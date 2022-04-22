package net.javaguides.springboot.crudrestfulwebservices.model;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
  
  private long bookId;
  
  private int likes;
  
  private ArrayList<String> customers;
}