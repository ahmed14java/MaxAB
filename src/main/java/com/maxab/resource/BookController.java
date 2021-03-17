package com.maxab.resource;

import com.maxab.errors.BookNotFoundException;
import com.maxab.errors.InvalidDateFormat;
import com.maxab.model.Book;
import com.maxab.service.BookService;
import com.maxab.service.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    /**
     * {@code POST } : Create a new book.
     * @param bookDTO the book to create.
     */
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody @Valid BookDTO bookDTO) {
        Book result = bookService.save(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code GET  /books} : get all the books.
     *
     * @param pageNo the pagination information.
     *
     * @param pageSize the pagination information.
     */
    @GetMapping
    public ResponseEntity<List<Book>> getBooks(@RequestParam(defaultValue = "0") Integer pageNo,
                                               @RequestParam(defaultValue = "10") Integer pageSize){
        List<Book> books = bookService.getAllBooks(pageNo, pageSize);
        return new ResponseEntity<>(books, OK);
    }

    /**
     * {@code GET  /books/search} : search the books.
     *
     * @param title search with title.
     *
     * @param date search with date within two dates ( release date and current date ).
     */
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = false) String title,
                                                  @RequestParam(required = false, defaultValue = "2021-03-17") String date,
                                                  @RequestParam(defaultValue = "0") Integer pageNo,
                                                  @RequestParam(defaultValue = "10") Integer pageSize) throws InvalidDateFormat {
        List<Book> books = bookService.search(title, date, pageNo, pageSize);
        return ResponseEntity.status(OK).body(books);
    }

    /**
     * {@code DELETE  /:id} : delete the "id" book.
     * @param id the id of the book to delete.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) throws BookNotFoundException {
        bookService.deleteBook(id);
        return ResponseEntity.status(OK).build();
    }

    /**
     * {@code PUT } : Update a book.
     * @param book the book to update.
     */
    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody @Valid Book book) throws BookNotFoundException{
        Book result = bookService.update(book);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
