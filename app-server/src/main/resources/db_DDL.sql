CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `available` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `registration_date` datetime DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `history` (
  `history_id` int(11) NOT NULL,
  `new_date` datetime DEFAULT NULL,
  `search` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`history_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `bookmark` (
  `bookmark_id` int(11) NOT NULL,
  `authors` varchar(255) DEFAULT NULL,
  `barcode` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `contents` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `ebook_barcode` varchar(255) DEFAULT NULL,
  `isbn` varchar(255) DEFAULT NULL,
  `key_barcode` varchar(255) DEFAULT NULL,
  `new_date` datetime DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `sale_price` int(11) DEFAULT NULL,
  `sale_yn` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `translators` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`bookmark_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci