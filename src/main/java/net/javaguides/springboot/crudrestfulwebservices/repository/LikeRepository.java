package net.javaguides.springboot.crudrestfulwebservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.crudrestfulwebservices.model.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>{
  
}