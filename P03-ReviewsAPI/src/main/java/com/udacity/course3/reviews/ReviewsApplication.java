package com.udacity.course3.reviews;

import com.mongodb.MongoClient;
import com.udacity.course3.reviews.mongorepository.MongoReviewRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = MongoReviewRepository.class)
public class ReviewsApplication {

	@Bean
	public MongoClient m() throws Exception { return new MongoClient("localhost"); }

	@Bean
	public MongoTemplate mongoTemplate() throws Exception { return new MongoTemplate(m(), "reviews"); }

	public static void main(String[] args) {
		SpringApplication.run(ReviewsApplication.class, args);
	}

}