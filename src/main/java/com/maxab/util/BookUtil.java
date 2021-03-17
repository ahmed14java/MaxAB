package com.maxab.util;

import com.maxab.model.Book;
import com.maxab.service.dto.BookDTO;
import org.modelmapper.ModelMapper;

public class BookUtil {

    public static Book convertToEntity(BookDTO bookDTO){
        ModelMapper mapper = new ModelMapper();
        Book book = mapper.map(bookDTO, Book.class);
        return book;
    }
}
