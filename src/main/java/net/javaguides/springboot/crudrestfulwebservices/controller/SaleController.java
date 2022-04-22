package net.javaguides.springboot.crudrestfulwebservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.crudrestfulwebservices.exception.ResourceNotFoundException;
import net.javaguides.springboot.crudrestfulwebservices.model.Book;
import net.javaguides.springboot.crudrestfulwebservices.model.Sale;
import net.javaguides.springboot.crudrestfulwebservices.repository.BookRepository;
import net.javaguides.springboot.crudrestfulwebservices.repository.SaleRepository;

@RestController
@RequestMapping("")
public class SaleController {

  @Autowired
  private SaleRepository saleRepository;
  @Autowired
  private BookRepository bookRepository;

  // get all sales in the repository
  @GetMapping("/sales")
  public ResponseEntity<List<Sale>> getAllSales(){
    try{
      List<Sale> sales = saleRepository.findAll();
      return ResponseEntity.ok().body(sales);
    }catch(Exception exc){
      exc.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  // create a new sale
  @PostMapping("/sales")
  public ResponseEntity<Sale> createBook(@Validated @RequestBody Sale sale) throws ResourceNotFoundException{
    try{
      long bookId = sale.getBookId();
      Book book = bookRepository.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("Book not found"));
      if(book.getStock() > 0){
        sale.setPrice(book.getSalePrice());
        saleRepository.save(sale);
        book.setStock(book.getStock() - 1);
        bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(sale);
      }else{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
    }catch(Exception exc){
      exc.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
