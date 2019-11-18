package com.udacity.course3.reviews.mongorepository;

import com.udacity.course3.reviews.model.MongoReview;
import com.udacity.course3.reviews.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoReviewRepository extends MongoRepository<MongoReview, Integer> {
    List<MongoReview> findByProduct(Product product);
    MongoReview findByReviewId(int reviewId);
    void deleteByReviewId(int reviewId);
}
