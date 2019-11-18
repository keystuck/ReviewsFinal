package com.udacity.course3.reviews;

import com.udacity.course3.reviews.model.MongoReview;
import com.udacity.course3.reviews.model.Product;
import com.udacity.course3.reviews.mongorepository.MongoReviewRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class MongoReviewRepositoryTests {



    private Product product;
    private String testReviewerName = "testReviwerName";
    private int testReviewId = 800;

    @Autowired
    private MongoReviewRepository mongoReviewRepository;

    @Autowired
    private MongoTemplate mongoTemplate;



    @Before
    public void setUp(){
        product = new Product();
        product.setProductId(700);
        product.setProductName("TestProduct");

        MongoReview mongoReview = new MongoReview();
        mongoReview.setReviewId(testReviewId);
        mongoReview.setProduct(product);
        mongoReview.setReviewerName(testReviewerName);
        mongoReview.setReviewText("TestReviewText");
        mongoReviewRepository.save(mongoReview);
    }


    @Test
    public void findByProduct(){
        List<MongoReview> mongoReviewList = mongoReviewRepository.findByProduct(product);
        assertThat(mongoReviewList).hasSize(1).extracting("reviewerName").contains(testReviewerName);
    }

    @Test
    public void findByReviewId(){
        MongoReview mongoReview = mongoReviewRepository.findByReviewId(testReviewId);
        assertThat(mongoReview).isNotNull().extracting("reviewerName").contains(testReviewerName);
    }

    @Test
    public void deleteByReviewId(){
        mongoReviewRepository.deleteByReviewId(testReviewId);
        MongoReview mongoReview = mongoReviewRepository.findByReviewId(testReviewId);
        assertThat(mongoReview).isNull();
    }

    @After
    public void cleanUp(){
        mongoReviewRepository.deleteByReviewId(testReviewId);
    }

}
