DROP TABLE IF EXISTS training.shiritori;

CREATE TABLE `shiritori` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `entry` varchar(30) NOT NULL,
  `word` varchar(100) NOT NULL,
  `raw_word` varchar(100) NOT NULL,  -- Add the rawWord column
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;