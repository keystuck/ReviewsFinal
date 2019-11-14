package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.model.MongoReview;
import com.udacity.course3.reviews.model.Product;
import com.udacity.course3.reviews.model.Review;
import com.udacity.course3.reviews.mongorepository.MongoReviewRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MongoReviewRepository mongoReviewRepository;

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewForProduct(@Valid @PathVariable("productId") Integer productId, @RequestBody Review review) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()){
            review.setProduct(optionalProduct.get());
            reviewRepository.save(review);
            MongoReview mongoReview = new MongoReview(review);
            mongoReviewRepository.save(mongoReview);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsForProduct(@Valid @PathVariable("productId") Integer productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        List<MongoReview> mongoReviews = new ArrayList<>();
        if (optionalProduct.isPresent()){
            List<Review> sqlReviewsList = reviewRepository.findByProduct(optionalProduct.get());

            for (Review sqlReview : sqlReviewsList){
                MongoReview mongoReview = mongoReviewRepository.findByReviewId(sqlReview.getReviewId());
                if (mongoReview != null){
                    mongoReviews.add(mongoReview);
                }
            }

            return ResponseEntity.accepted().body(mongoReviews);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}