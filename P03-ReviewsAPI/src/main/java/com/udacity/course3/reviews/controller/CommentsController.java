package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.model.Comment;
import com.udacity.course3.reviews.model.MongoReview;
import com.udacity.course3.reviews.model.Review;
import com.udacity.course3.reviews.mongorepository.MongoReviewRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    @Qualifier("reviewRepository")
    private ReviewRepository reviewRepository;


    @Autowired
            @Qualifier("mongoReviewRepository")
    MongoReviewRepository mongoReviewRepository;

    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<?> createCommentForReview(@Valid @PathVariable("reviewId") Integer reviewId, @RequestBody Comment comment) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isPresent()){
            comment.setReview(optionalReview.get());

            MongoReview mongoReview = mongoReviewRepository.findByReviewId(reviewId);
            if (mongoReview !=  null){
                mongoReviewRepository.deleteByReviewId(reviewId);
                mongoReview.addComments(comment);
                mongoReviewRepository.save(mongoReview);
            }

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public List<?> listCommentsForReview(@Valid @PathVariable("reviewId") Integer reviewId) {
        List<Comment> commentList = new ArrayList<>();
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isPresent()){
            MongoReview mongoReview = mongoReviewRepository.findByReviewId(reviewId);
            if (mongoReview != null){
                commentList.addAll(mongoReview.getComments());
            }
        }
        return commentList;
    }
}