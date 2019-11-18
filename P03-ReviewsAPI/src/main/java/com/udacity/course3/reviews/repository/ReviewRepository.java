package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.model.Product;
import com.udacity.course3.reviews.model.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ReviewRepository extends CrudRepository<Review, Integer> {
    List<Review> findByProduct(Product product);
}
