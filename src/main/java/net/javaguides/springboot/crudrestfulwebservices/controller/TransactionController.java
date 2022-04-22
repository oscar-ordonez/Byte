package net.javaguides.springboot.crudrestfulwebservices.controller;

import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import net.javaguides.springboot.crudrestfulwebservices.model.Sale;
import net.javaguides.springboot.crudrestfulwebservices.repository.LikeRepository;
import net.javaguides.springboot.crudrestfulwebservices.repository.SaleRepository;

public class TransactionController {

  @Autowired
  private SaleRepository saleRepository;

  // get all books in the repository
  @GetMapping("/transactions/{id}")
  public ResponseEntity<Map<String, Object>> getAllBooksPage(@PathVariable(value = "id") long bookId, @RequestParam(name="from", defaultValue="") String from, @RequestParam(name="to", defaultValue="") String to) throws Exception {
    if(from.isEmpty() || to.isEmpty()){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } 
    Date fromDate = new SimpleDateFormat("dd/MM/yyyy").parse(from); 
    Date toDate = new SimpleDateFormat("dd/MM/yyyy").parse(to); 
    List<Sale> sales = saleRepository.findAllBySaleDateBetween(fromDate , toDate);
    
    ArrayList<Date> dates = new ArrayList<Date>();
    ArrayList<String> customers = new ArrayList<String>();
    float totalRevenue = 0;
    for(int i = 0; i < sales.size(); i++){
      if(sales.get(i).getBookId() == bookId){
        dates.add(sales.get(i).getSaleDate());
        customers.add(sales.get(i).getCustomerEmail());
        totalRevenue = totalRevenue + sales.get(i).getPrice();
      }
    }
    if(totalRevenue == 0){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }else{
      Map<String, Object> response = new HashMap<String, Object>();
      response.put("bookId", bookId);
      response.put("sales", dates);
      response.put("totalRevenue", totalRevenue);
      response.put("customers", customers);
      return ResponseEntity.ok().body(response);
    }

  } 
}
