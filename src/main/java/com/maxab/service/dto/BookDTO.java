package com.maxab.service.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Data
public class BookDTO {

    @NotNull(message = "title is required")
    @NotEmpty
    private String title;

    @NotNull(message = "author name is required")
    @NotEmpty
    private String authorName;

    @DateTimeFormat(iso = DATE)
    @PastOrPresent
    private LocalDate releaseDate;

    private String description;
}
