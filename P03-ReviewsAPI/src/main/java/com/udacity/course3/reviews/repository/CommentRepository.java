package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.model.Comment;
import com.udacity.course3.reviews.model.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CommentRepository extends CrudRepository<Comment, Integer> {
    List<Comment> findByReview(Review review);
}
