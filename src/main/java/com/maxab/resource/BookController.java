package com.maxab.resource;

import com.maxab.errors.BookNotFoundException;
import com.maxab.errors.InvalidDateFormat;
import com.maxab.model.Book;
import com.maxab.service.BookService;
import com.maxab.service.dto.BookDTO;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
     * @param token to authenticate the user.
     */
    @Operation(summary = "JSON Web Token (mandatory)",security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody @Valid BookDTO bookDTO, @Parameter(hidden = true) @RequestHeader(name = "Authorization") String token) {
        Book result = bookService.save(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code GET  /books} : get all the books.
     *
     * @param pageNo the pagination information.
     *
     * @param pageSize the pagination information.
     * @param token to authenticate the user.
     */
    @Operation(summary = "JSON Web Token (mandatory)",security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping
    public ResponseEntity<List<Book>> getBooks(@RequestParam(defaultValue = "0") Integer pageNo,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @Parameter(hidden = true) @RequestHeader(name = "Authorization") String token){
        List<Book> books = bookService.getAllBooks(pageNo, pageSize);
        return new ResponseEntity<>(books, OK);
    }

    /**
     * {@code GET  /books/search} : search the books.
     *
     * @param title search with title.
     *
     * @param date search with date within two dates ( release date and current date ).
     * @param token to authenticate the user.
     */
    @Operation(summary = "JSON Web Token (mandatory)",security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = false) String title,
                                                  @RequestParam(required = false, defaultValue = "2021-03-17") String date,
                                                  @RequestParam(defaultValue = "0") Integer pageNo,
                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                  @Parameter(hidden = true) @RequestHeader(name = "Authorization") String token) throws InvalidDateFormat {
        List<Book> books = bookService.search(title, date, pageNo, pageSize);
        return ResponseEntity.status(OK).body(books);
    }

    /**
     * {@code DELETE  /:id} : delete the "id" book.
     * @param id the id of the book to delete.
     * @param token to authenticate the user.
     */
    @Operation(summary = "JSON Web Token (mandatory)",security = { @SecurityRequirement(name = "bearer-key") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id, @Parameter(hidden = true) @RequestHeader(name = "Authorization") String token) throws BookNotFoundException {
        bookService.deleteBook(id);
        return ResponseEntity.status(OK).build();
    }

    /**
     * {@code PUT } : Update a book.
     * @param book the book to update.
     * @param token to authenticate the user.
     */
    @Operation(summary = "JSON Web Token (mandatory)",security = { @SecurityRequirement(name = "bearer-key") })
    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody @Valid Book book, @Parameter(hidden = true) @RequestHeader(name = "Authorization") String token) throws BookNotFoundException{
        Book result = bookService.update(book);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
