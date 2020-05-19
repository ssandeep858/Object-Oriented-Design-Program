CREATE DATABASE Store;


USE Store;


CREATE TABLE product (
  name VARCHAR(45) NOT NULL PRIMARY KEY,
  quantity INT NOT NULL,
  type VARCHAR(45) NOT NULL,
  id INT NOT NULL,
  price INT NOT NULL );

INSERT INTO  product(name,quantity,type,id,price)  VALUES ('books',700,'Study',1,70);
INSERT INTO  product(name,quantity,type,id,price)  VALUES ('Laptop',610,'Electronic',2,6100);
INSERT INTO  product(name,quantity,type,id,price)  VALUES ('Shirts',520,'CLothes',3,77);
INSERT INTO  product(name,quantity,type,id,price)  VALUES ('Glasses',430,'Essentials',4,61);
INSERT INTO  product(name,quantity,type,id,price)  VALUES ('Nike',340,'Shoes',5,100);

CREATE TABLE user (
  name VARCHAR(45) NOT NULL ,
  email VARCHAR(45) NOT NULL,
  password VARCHAR(45) NOT NULL,
  isAdmin TINYINT NOT NULL );

INSERT INTO  user(name,email,password,isAdmin)  VALUES ('sandeep','singh96','sandeep123','1');
INSERT INTO  user(name,email,password,isAdmin)  VALUES ('akshay','armali','akshay123','1');
INSERT INTO  user(name,email,password,isAdmin)  VALUES ('blake','bltroutm','blake123','1');





