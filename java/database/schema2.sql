BEGIN TRANSACTION;

DROP TABLE IF EXISTS user_location;
DROP SEQUENCE IF EXISTS seq_location_id;

CREATE SEQUENCE seq_location_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;


CREATE TABLE user_location (
	location_id int DEFAULT nextval('seq_location_id'::regclass) NOT NULL,
	user_id int NOT NULL,
	user_city varchar(50) NOT NULL,
	user_state varchar(2) NOT NULL,
    user_zip int,
    city_id int NOT NULL,
	CONSTRAINT PK_location PRIMARY KEY (location_id),
    CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

DROP TABLE IF EXISTS favorites;
DROP SEQUENCE IF EXISTS seq_choice_id;

CREATE SEQUENCE seq_choice_id
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

CREATE TABLE favorites (
    choice_id int DEFAULT nextval('seq_choice_id'::regclass) NOT NULL,
	user_id int NOT NULL,
	cuisine_name varchar(20) NOT NULL,
	cuisine_id int NOT NULL,
	CONSTRAINT PK_choice PRIMARY KEY (choice_id),
    CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

DROP TABLE IF EXISTS liked_restaurants;
DROP SEQUENCE IF EXISTS seq_liked_id;

CREATE SEQUENCE seq_liked_id
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

CREATE TABLE liked_restaurants (
    liked_id int DEFAULT nextval('seq_liked_id'::regclass) NOT NULL,
	restaurant_id int NOT NULL,
	name varchar(50) NOT NULL,
	rating decimal NOT NULL,
    cost int,
    location varchar(50),
    url varchar(200),
    photo_url varchar(200),
    liked boolean,
    user_id int NOT NULL,
    cuisine_type varchar(50),
	CONSTRAINT PK_liked PRIMARY KEY (liked_id),
    CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

COMMIT TRANSACTION;