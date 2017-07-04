CREATE DATABASE  IF NOT EXISTS `webstore`;
USE `webstore`;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;

CREATE TABLE `products` (
  `id` varchar(45) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL, 
  `unit_price` DECIMAL DEFAULT NULL,
  `manufacturer` varchar(50) DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL,
  `product_condition` varchar(50) DEFAULT NULL,
  `units_in_stock` BIGINT DEFAULT NULL,
  `units_in_order` BIGINT DEFAULT NULL,
  `discontinued` BOOLEAN DEFAULT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `products` VALUES('P1234','iPhone 6s', 'Apple iPhone 6s', 500, 'Apple', 'Smartphone','New',450,0,FALSE);
INSERT INTO products VALUES('P1235','Dell Inspiron', 'Dell Inspiron 14 inch laptop', '700', 'Dell','Laptop','New',1000,0,false);
INSERT INTO `products` VALUES('P1236','Nexus 7', 'Google Nexus 7', '300', 'Google', 'Tablet','New',1000,0,false);


--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;

CREATE TABLE `customers` (
  `id` varchar(45) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `address` varchar(250) DEFAULT NULL, 
  `units_ordered` BIGINT DEFAULT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `customers` VALUES('1','Bill Smith', '10 Stone Court Drive', '3');


