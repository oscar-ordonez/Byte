package net.javaguides.springboot.crudrestfulwebservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.crudrestfulwebservices.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
  List<Book> findByAvailable(boolean flag);
}