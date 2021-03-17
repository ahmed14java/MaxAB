package com.maxab.service.impl;

import com.maxab.errors.BookNotFoundException;
import com.maxab.errors.InvalidDateFormat;
import com.maxab.model.Book;
import com.maxab.repository.BookRepository;
import com.maxab.service.BookService;
import com.maxab.service.dto.BookDTO;
import com.maxab.util.BookUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String PATTER_ERROR_MESSAGE = "Date Must Match With 'yyyy-MM-dd' ";

    private final BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Book> page = bookRepository.findAll(paging);
        if(page.hasContent()) {
            return page.getContent();
        } else {
            return new ArrayList<>();
        }
    }


    @Override
    public Book save(BookDTO bookDTO) {
        Book request = BookUtil.convertToEntity(bookDTO);
        return bookRepository.save(request);
    }

    @Override
    public Book update(Book book) throws BookNotFoundException{
        if (book.getId() == null) {
            throw new BookNotFoundException("you must put the book ID");
        }
        Book bookUpdate = bookRepository.findById(book.getId()).orElseThrow(() -> new BookNotFoundException("Could not find book with id: " + book.getId()));
        bookUpdate.setTitle(book.getTitle());
        bookUpdate.setDescription(book.getDescription());
        bookUpdate.setReleaseDate(book.getReleaseDate());
        bookUpdate.setAuthorName(book.getAuthorName());
        return bookRepository.save(bookUpdate);
    }


    @Override
    public void deleteBook(Long id) throws BookNotFoundException {
       Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Could not find book with id: " + id));
       bookRepository.delete(book);
    }

    @Override
    public List<Book> search(String title, String releaseDate, Integer pageNo, Integer pageSize) throws InvalidDateFormat {
        LocalDate date = null;
        if(releaseDate != null){
            try {
                date = LocalDate.parse(releaseDate,DateTimeFormatter.ofPattern(DATE_FORMAT));
            } catch (Exception e) {
                throw new InvalidDateFormat(PATTER_ERROR_MESSAGE);
            }
        }
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Book> books = bookRepository.search(title, date, paging);
        if(books.hasContent()) {
            return books.getContent();
        } else {
            return new ArrayList<>();
        }
    }


}
