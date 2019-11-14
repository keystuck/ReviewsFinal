CREATE TABLE products (
    product_id INT NOT NULL AUTO_INCREMENT,
    product_name VARCHAR(500) NOT NULL,
    primary key (product_id)
    );

CREATE TABLE reviews (
    review_id INT NOT NULL AUTO_INCREMENT,
    product_id INT NOT NULL,
    reviewer_name VARCHAR(100) NOT NULL,
    review_text VARCHAR(10000) NOT NULL,
    primary key (review_id),
    foreign key (product_id) references products (product_id)
    );

CREATE TABLE comments (
    comment_id INT NOT NULL AUTO_INCREMENT,
    review_id INT NOT NULL,
    commenter_name VARCHAR(100) NOT NULL,
    comment_text VARCHAR(10000) NOT NULL,
    primary key (comment_id),
    foreign key (review_id) references reviews (review_id)
    );