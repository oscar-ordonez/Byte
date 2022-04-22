package net.javaguides.springboot.crudrestfulwebservices.controller;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

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
import net.javaguides.springboot.crudrestfulwebservices.model.Like;
import net.javaguides.springboot.crudrestfulwebservices.repository.LikeRepository;
import net.javaguides.springboot.crudrestfulwebservices.repository.BookRepository;

@RestController
@RequestMapping("")
public class LikeController {

  @Autowired
  private LikeRepository likeRepository;
  @Autowired
  private BookRepository bookRepository;
  
  // get all likes in the repository
  @GetMapping("/likes")
  public ResponseEntity<List<Like>> getAllLikes(){
    try{
      List<Like> likes = likeRepository.findAll();
      return ResponseEntity.ok().body(likes);
    }catch(Exception exc){
      exc.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  // create a new like
  @PostMapping("/likes")
  public ResponseEntity<Map<String, Object>> createLike(@Validated @RequestBody Like like) throws ResourceNotFoundException{
    try{
      Map<String, Object> response = new HashMap<String, Object>();
      Book book = bookRepository.findById(like.getBookId()).orElseThrow(()-> new ResourceNotFoundException("Book not found"));
      // save only if book is available
      if(book.getAvailable()){
        // see if the book already exist
        if(likeRepository.findById(like.getBookId()).isEmpty()){
          like.setLikes(1);
          ArrayList<String> customers = new ArrayList<String>();
          customers.add(like.getCustomerEmail());
          like.setCustomers(customers);
          likeRepository.save(like);
          response.put("bookId", like.getBookId());
          response.put("likes", like.getLikes());
          response.put("customers", like.getCustomers());
          return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }else{
          Like oldLike = likeRepository.findById(like.getBookId()).orElseThrow(()-> new ResourceNotFoundException("Like not found"));
          ArrayList<String> customers = oldLike.getCustomers();
          boolean custSaved = false;
          for(int i = 0; i < customers.size(); i++){
            if(customers.get(i).contentEquals(like.getCustomerEmail())){
              custSaved = true;
            }
          }
          if(!custSaved){
            System.out.println(like.getLikes());
            oldLike.setLikes(oldLike.getLikes() + 1);
            // convert customers to arraylist
            customers.add(like.getCustomerEmail());
            oldLike.setCustomers(customers);
            likeRepository.save(oldLike);
            response.put("bookId", oldLike.getBookId());
            response.put("likes", oldLike.getLikes());
            response.put("customers", oldLike.getCustomers());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
          }else{
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
          }
        }
      }else{
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
      }
    }catch(Exception exc){
      exc.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
