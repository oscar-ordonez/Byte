package net.javaguides.springboot.crudrestfulwebservices.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.crudrestfulwebservices.model.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>{
  List<Sale> findAllBySaleDateBetween(Date saleDateStart, Date saleDateEnd);
}