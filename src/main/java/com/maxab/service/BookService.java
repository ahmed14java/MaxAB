package com.maxab.service;

import com.maxab.errors.BookNotFoundException;
import com.maxab.errors.InvalidDateFormat;
import com.maxab.model.Book;
import com.maxab.service.dto.BookDTO;

import java.util.List;


public interface BookService {

    List<Book> getAllBooks(Integer pageNo, Integer pageSize);

    Book save(BookDTO bookDTO);

    Book update(Book book) throws BookNotFoundException;

    void deleteBook(Long id) throws BookNotFoundException;

    List<Book> search(String title, String releaseDate, Integer pageNo, Integer pageSize)throws InvalidDateFormat;
}
