package net.javaguides.springboot.crudrestfulwebservices.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "book")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long bookId;

	@NonNull
	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;
	
	@NonNull
	@Column(name = "stock")
	private int stock;

	@NonNull
	@Column(name = "salePrice")
	private float salePrice;

	@Column(name = "available")
	private boolean available;

	public boolean getAvailable(){
		return this.available;
	}
}
