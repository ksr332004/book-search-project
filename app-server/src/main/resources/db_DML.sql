INSERT INTO `user` (`user_id`, `email`, `name`, `password`, `role`, `registration_date`) VALUES
	(1, 'test@test.com', 'test', '123', 'ROLE_USER', NOW()),
	(2, 'admin@test.com', 'admin', '123', 'ROLE_USER', NOW()),
	(3, 'seran@test.com', 'seran', '123', 'ROLE_USER', NOW());
	
INSERT INTO `history` (`history_id`, `user_id`, `search`, `new_date`) VALUES
	(1, 1, '음악', NOW()),
	(2, 1, '노래', NOW()),
	(3, 1, '게임', NOW());

INSERT INTO `bookmark` (`bookmark_id`, `user_id`, `authors`, `category`, `contents`, `create_date`, `isbn`, `key_barcode`, `new_date`, `price`, `publisher`, `thumbnail`, `title`, `translators`, `url`) VALUES
	(1, 1, 'authors1', 'category1', 'contents1', NOW(), '123444', '123455', NOW(), 10000, 'publisher', 'thumbnail', 'title1', 'translators', 'url'),
	(2, 1, 'authors2', 'category2', 'contents2', NOW(), '123442', '123425', NOW(), 12000, 'publisher', 'thumbnail', 'title2', 'translators', 'url'),
	(3, 2, 'authors3', 'category3', 'contents3', NOW(), '123442 111111', '123495', NOW(), 13000, 'publisher', 'thumbnail', 'title3', 'translators', 'url');