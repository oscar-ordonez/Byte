package net.javaguides.springboot.crudrestfulwebservices.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sale")
public class Sale {

  @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NonNull
	@Column(name = "bookId")
  private long bookId;

  @NonNull
	@Column(name = "customerEmail")
  private String customerEmail;

  @NonNull
	@Column(name = "price")
  private float price;

  @Temporal(TemporalType.DATE)
	Date saleDate;
}
