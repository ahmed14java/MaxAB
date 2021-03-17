package com.maxab.repository;

import com.maxab.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    Page<Book> findAll(Pageable pageable);

    Optional<Book> findById(Long aLong);

    @Query("select b from Book b where (:title IS NULL OR b.title LIKE CONCAT('%',:title,'%')) and (:releaseDate IS NULL OR b.releaseDate between :releaseDate and current_date)")
    Page<Book> search(@Param("title") String title, @Param("releaseDate") LocalDate releaseDate, Pageable pageable);

}
