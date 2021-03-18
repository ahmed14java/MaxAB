package com.maxab.service;

import com.maxab.errors.BookNotFoundException;
import com.maxab.errors.InvalidBookInputException;
import com.maxab.errors.InvalidDateFormat;
import com.maxab.model.Book;
import com.maxab.repository.BookRepository;
import com.maxab.service.dto.BookDTO;
import com.maxab.service.impl.BookServiceImpl;
import com.maxab.util.UtilTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void createBook() {
        BookDTO bookDTO = UtilTest.createTestBookIncommingDTO();
        Book book = UtilTest.convertDtoToBook(bookDTO);
        when(bookRepository.save(any())).thenReturn(book);
        Book newBook = bookService.save(bookDTO);
        assertNotNull(newBook);
        assertEquals(bookDTO.getTitle(), newBook.getTitle());
        verify(bookRepository).save(any());
    }

    @Test
    void createBookWithInvalidFields(){
        BookDTO bookDTO = UtilTest.createTestBookIncommingDTO();
        bookDTO.setTitle(null);
        when(bookRepository.save(any())).thenThrow(InvalidBookInputException.class);
        assertThrows(InvalidBookInputException.class, () -> bookService.save(bookDTO));
    }

    @Test
    void deleteBook() throws BookNotFoundException {
        BookDTO bookDTO = UtilTest.createTestBookIncommingDTO();
        Book book = UtilTest.convertDtoToBook(bookDTO);
        when(bookRepository.findById(any())).thenReturn(Optional.ofNullable(book));
        doNothing().when(bookRepository).deleteById(anyLong());
        bookService.deleteBook(anyLong());
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void deleteBookWithInvalidId(){
        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(99999L));
    }


    @Test
    void updateBook() throws BookNotFoundException {
        BookDTO bookDTO = UtilTest.createTestBookIncommingDTO();
        Book book = UtilTest.convertDtoToBook(bookDTO);
        book.setId(1L);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.ofNullable(book));
        when(bookRepository.save(any())).thenReturn(book);
        Book bookUpdated = bookService.update(book);
        assertNotNull(bookUpdated);
        assertEquals(book.getTitle(), bookUpdated.getTitle());
        verify(bookRepository).save(any());
    }

    @Test
    void updateWithInvalidId(){
        BookDTO bookDTO = UtilTest.createTestBookIncommingDTO();
        Book book = UtilTest.convertDtoToBook(bookDTO);
        assertThrows(BookNotFoundException.class, () -> bookService.update(book));
    }

    @Test
    void search() throws InvalidDateFormat {
        List<Book> books = UtilTest.getTestBooks(3);
        Page<Book> page = new PageImpl<>(books);
        Pageable paging = PageRequest.of(0, 10);
        when(bookRepository.search("Spring",null,paging)).thenReturn(page);
        List<Book> result = bookService.search("Spring" ,null,0,10);
        assertEquals(books.size(), result.size());
    }

    @Test
    void searchWithInvalidDateFormat() throws InvalidDateFormat {
        List<Book> books = UtilTest.getTestBooks(3);
        Page<Book> page = new PageImpl<>(books);
        Pageable paging = PageRequest.of(0, 10);
        when(bookRepository.search("Spring",null,paging)).thenReturn(page);
        List<Book> result = bookService.search("Spring" ,null,0,10);
        assertEquals(books.size(), result.size());
        assertThrows(InvalidDateFormat.class, () -> bookService.search("Spring" ,"2020/03/12",0,10));
    }

    @Test
    void getAllBooks() {
        List<Book> books = UtilTest.getTestBooks(3);
        Page<Book> page = new PageImpl<>(books);
        Pageable paging = PageRequest.of(0, 10);
        when(bookRepository.findAll(paging)).thenReturn(page);
        List<Book> result = bookService.getAllBooks(0,10);
        assertEquals(books.size(), result.size());
    }

    @Test
    void getAllBooks_second_approach() {
        Pageable paging = PageRequest.of(0, 10);
        Page<Book> page = new PageImpl<>(new ArrayList<>());
        when(bookRepository.findAll(paging)).thenReturn(page);
        List<Book> result = bookService.getAllBooks(0,10);;
        assertEquals(0, result.size());
    }

}