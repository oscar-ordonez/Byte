package net.javaguides.springboot.crudrestfulwebservices.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "likes")
public class Like {
  @Id
  private long bookId;
  @Column(name = "likes")
  private int likes;
  @Column(name = "customers")
  private ArrayList<String> customers;
  private String customerEmail;
}