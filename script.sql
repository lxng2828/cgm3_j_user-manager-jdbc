DROP DATABASE IF EXISTS demo;
CREATE DATABASE demo;
USE demo;

CREATE TABLE users (
    id INT(3) NOT NULL AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(220) NOT NULL,
    country VARCHAR(120),
    PRIMARY KEY (id)
);

INSERT INTO users (name, email, country) VALUES
('Minh', 'minh@codegym.vn', 'Viet Nam'),
('Kante', 'kante@che.uk', 'Kenia'),
('Hieu', 'hieu@codegym.vn', 'Viet Nam'),
('Lan', 'lan@gmail.com', 'Viet Nam'),
('Tuan', 'tuan@yahoo.com', 'Viet Nam'),
('Aisha', 'aisha@africa.org', 'Nigeria'),
('Sofia', 'sofia@euro.eu', 'Spain'),
('Hiroshi', 'hiroshi@japan.co', 'Japan'),
('Mei', 'mei@china.cn', 'China'),
('Alex', 'alex@usa.com', 'USA'),
('Emma', 'emma@uk.org', 'United Kingdom'),
('Ravi', 'ravi@india.in', 'India'),
('Lucas', 'lucas@france.fr', 'France'),
('Yara', 'yara@brazil.br', 'Brazil'),
('Omar', 'omar@egypt.eg', 'Egypt'),
('Anya', 'anya@russia.ru', 'Russia'),
('Chen', 'chen@taiwan.tw', 'Taiwan');

DELIMITER $$
CREATE PROCEDURE get_user_by_id(IN user_id INT)
BEGIN
SELECT users.name, users.email, users.country
FROM users
where users.id = user_id;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE insert_user(IN user_name varchar(50),IN user_email varchar(50),IN user_country varchar(50))
BEGIN
INSERT INTO users(name, email, country) VALUES(user_name, user_email, user_country);
END$$
DELIMITER ;

create table Employee ( 
            id int(3) NOT NULL AUTO_INCREMENT, 
            name varchar(120) NOT NULL, 
            salary int(220) NOT NULL, 
            created_Date datetime, 
            PRIMARY KEY (id) 
);
