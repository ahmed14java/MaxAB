package com.maxab.resource;

import com.maxab.model.Book;
import com.maxab.service.BookService;
import com.maxab.service.dto.BookDTO;
import com.maxab.util.BookUtil;
import com.maxab.util.UtilTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@WithMockUser
class BookControllerTest {

    @MockBean
    BookService bookingService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void createBook() throws Exception {
        BookDTO bookDTO = UtilTest.createTestBookIncommingDTO();
        Book book = UtilTest.convertDtoToBook(bookDTO);
        when(bookingService.save(bookDTO)).thenReturn(book);
        MvcResult result = mockMvc.perform(post("/api/books")
                                  .accept(APPLICATION_JSON)
                                  .content(UtilTest.objectToJson(bookDTO))
                                  .contentType(APPLICATION_JSON))
                                  .andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.CREATED.value(), status);
    }

    @Test
    void getBooks() {
    }

    @Test
    void searchBooks() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void updateBook() {
    }
}