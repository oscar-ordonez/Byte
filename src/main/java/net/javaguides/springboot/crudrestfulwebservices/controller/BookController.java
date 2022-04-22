package net.javaguides.springboot.crudrestfulwebservices.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.crudrestfulwebservices.exception.ResourceNotFoundException;
import net.javaguides.springboot.crudrestfulwebservices.model.Book;
import net.javaguides.springboot.crudrestfulwebservices.repository.BookRepository;

@RestController
@RequestMapping("")
public class BookController {

  @Autowired
  private BookRepository bookRepository;

  // get all books in the repository
  @GetMapping("/allBooks")
  public ResponseEntity<List<Book>> getAllBooks(){
    try{
      List<Book> books = bookRepository.findByAvailable(true);
      //List<Book> books = bookRepository.findAll();
      return ResponseEntity.ok().body(books);
    }catch(Exception exc){
      exc.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  // get all books in the repository
  @GetMapping("/books")
  public Map<String, Object> getAllBooksPage(@RequestParam(name="page", defaultValue="1") int pageNo, @RequestParam(name="size", defaultValue="12") int pageSize, @RequestParam(name="sort", defaultValue="title") String sortBy, @RequestParam(name="title", defaultValue="") String title, @RequestParam(name="unavailable", defaultValue="false") boolean unavailable){
    Map<String, Object> response = new HashMap<String, Object>();
    Sort sort = Sort.by(sortBy);
    Pageable page = PageRequest.of(pageNo - 1 , pageSize, sort); // se resta porque el indice empieza en 0
    Page<Book> bookPage;
    if(unavailable){
      bookPage = bookRepository.findAll(page);
    }else{
      // here would go filtered by available = true
      bookPage = bookRepository.findAll(page);
    }
    response.put("content", bookPage.getContent());
    response.put("size", bookPage.getSize());
    response.put("numberOfElements", bookPage.getNumberOfElements());
    response.put("totalElements", bookPage.getTotalElements());
    response.put("totalPages", bookPage.getTotalPages());
    response.put("number", bookPage.getNumber() + 1);
    return response;
  } 

  // for title filter but not knowing how to apply it
  public List<Book> getBooksByExample(Book book){
    ExampleMatcher matcher = ExampleMatcher.matchingAny().withMatcher("title", GenericPropertyMatcher.of(StringMatcher.CONTAINING));
    Example<Book> e = Example.of(book, matcher);
    return bookRepository.findAll(e);
  }

  // create a new book
  @PostMapping("/books")
  public ResponseEntity<Book> createBook(@Validated @RequestBody Book book){
    try{
      book.setAvailable(true);
      bookRepository.save(book);
      return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }catch(Exception exc){
      exc.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  // get book by id
  @GetMapping("/books/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable(value = "id") long bookId) throws ResourceNotFoundException{
    try{
      Book book = bookRepository.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("Book not found for this id:: " + bookId));
      return ResponseEntity.ok().body(book);
    }catch(Exception exc){
      exc.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  // update book
  @PutMapping("/books/{id}")
  public ResponseEntity<Book> updateBook(@PathVariable(value = "id") long bookId, @RequestBody Book newBook) throws ResourceNotFoundException{
    try{
      Book book = bookRepository.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("Book not found for this id:: " + bookId));
      book.setAvailable(true);
      book.setDescription(newBook.getDescription());
      book.setSalePrice(newBook.getSalePrice());
      book.setStock(newBook.getStock());
      book.setTitle(newBook.getTitle());
      bookRepository.save(book);
      return ResponseEntity.ok().body(book);
    }catch(Exception exc){
      exc.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  // patch book
  @PatchMapping("/books/{id}")
  public ResponseEntity<Book> patchBook(@PathVariable(value = "id") long bookId, @RequestBody Book newBook) throws ResourceNotFoundException{
    try{
      Book book = bookRepository.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("Book not found for this id:: " + bookId));
      if(newBook.getDescription() != null){
        book.setDescription(newBook.getDescription());
      }
      if(newBook.getSalePrice() > 0){
        book.setSalePrice(newBook.getSalePrice());
      }
      if(newBook.getStock() > 0){
        book.setStock(newBook.getStock());
      }
      if(newBook.getTitle() != null){
        book.setTitle(newBook.getTitle());
      }
      bookRepository.save(book);
      return ResponseEntity.ok().body(book);
    }catch(Exception exc){
      exc.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  // delete book
  @DeleteMapping("/books/{id}")
  public ResponseEntity<?> deleteBook(@PathVariable(value = "id") long bookId) throws ResourceNotFoundException{
    try{
      bookRepository.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("Book not found for this id:: " + bookId));
      bookRepository.deleteById(bookId);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }catch(Exception exc){
      exc.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
