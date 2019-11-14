package com.udacity.course3.reviews;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//TODO: WHAT AM IDOING HERE?
@DataMongoTest
@ExtendWith(SpringExtension.class)
public class MongoDbSpringIntegrationTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void test() {
        // given
        DBObject objectToSave = BasicDBObjectBuilder.start()
                .add("key", "value")
                .get();



        if (objectToSave != null && mongoTemplate != null) {


            // when
            mongoTemplate.save(objectToSave, "collection");

            // then
            assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key")
                    .containsOnly("value");
        }
    }

}
