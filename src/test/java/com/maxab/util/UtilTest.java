package com.maxab.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maxab.model.Book;
import com.maxab.service.dto.BookDTO;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UtilTest<T> {
    /*

    public static <T> T jsonToObject(String json, Class<T> clazz) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(json, clazz);
    }


    public static <T> String objectToJson(T object) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.writeValueAsString(object);
    }
    */
    public static List<Book> getTestBooks(Integer count) {
        List<Book> books = new ArrayList<>();
        for (int i = 1; i <= count; i++)
        {
            books.add(new Book(1L + i, "Java " + i , "John" , LocalDate.now(), "new Book"));
        }
        return books;
    }

    public static BookDTO createTestBookIncommingDTO(){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthorName("Michel");
        bookDTO.setTitle("Spring microservice");
        bookDTO.setReleaseDate(LocalDate.now());
        bookDTO.setDescription("spring microservice description here");
        return bookDTO;
    }

    public static Book convertDtoToBook(BookDTO bookDTO){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(bookDTO, Book.class);
    }
}
