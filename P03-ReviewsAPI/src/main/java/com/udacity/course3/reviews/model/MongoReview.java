package com.udacity.course3.reviews.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Document("reviews")
public class MongoReview {
    @Id
    private int reviewId;

    @NotNull
    @Column(name = "reviewer_name")
    private String reviewerName;

    @NotNull
    @Column(name = "review_text")
    private String reviewText;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public MongoReview(){
        this.comments = new ArrayList<>();
    }

    public MongoReview(MongoReview mongoReview){
        this.product = mongoReview.product;
        this.reviewText = mongoReview.reviewText;
        this.reviewerName = mongoReview.reviewerName;
        this.reviewId = mongoReview.reviewId;
        this.comments = mongoReview.comments;
    }

    public MongoReview(Review review){
        this.reviewId = review.getReviewId();
        this.reviewerName = review.getReviewerName();
        this.reviewText = review.getReviewText();
        this.product = review.getProduct();
        this.comments = new ArrayList<>();
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComments(Comment comment){
        this.comments.add(comment);
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


}
