package com.udacity.course3.reviews;

import com.udacity.course3.reviews.model.Comment;
import com.udacity.course3.reviews.model.Product;
import com.udacity.course3.reviews.model.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {
    @Autowired
    private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private EntityManager entityManager;
    @Autowired private TestEntityManager testEntityManager;
    @Autowired private ProductRepository productRepository;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private CommentRepository commentRepository;

    private String testProductName = "testProductName";
    private String testReviewText = "testReviewText";
    private String testCommentText = "testCommentText";

    @Test
    public void injectedComponentsAreNotNull(){
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(testEntityManager).isNotNull();
        assertThat(productRepository).isNotNull();
        assertThat(reviewRepository).isNotNull();
        assertThat(commentRepository).isNotNull();
    }

    @Test
    public void findByReview(){
        Product testProduct = new Product();
        testProduct.setProductName(testProductName);
        productRepository.save(testProduct);

        Review testReview = new Review();
        testReview.setProduct(testProduct);
        testReview.setReviewerName("Name");
        testReview.setReviewText(testReviewText);
        reviewRepository.save(testReview);

        Comment testComment = new Comment();
        testComment.setReview(testReview);
        testComment.setCommenterName("testCommenterName");
        testComment.setCommentText(testCommentText);
        commentRepository.save(testComment);

        List<Comment> commentList = commentRepository.findByReview(testReview);
        assertThat(commentList).isNotNull();
        assertThat(commentList.get(0)).isNotNull().extracting("commentText").contains(testCommentText);

    }


}
