/**
 * 问卷pack
 */
CREATE TABLE `data_tp_quespack` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL,
  `pack_no` varchar(255) DEFAULT NULL,
  `pack_name` varchar(255) DEFAULT NULL,
  `pack_info` varchar(2048) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  `useflag` tinyint(4) NOT NULL DEFAULT '1',
  `sgutimes` int(11) NOT NULL DEFAULT '0',
  `tplname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4;

/**
 * 问卷明细
 */
CREATE TABLE `data_tp_quesitem` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `pack` bigint(20) DEFAULT NULL,
  `itemno` varchar(255) DEFAULT NULL,
  `question` varchar(2048) DEFAULT NULL,
  `subhead` varchar(2048) DEFAULT NULL,
  `qt` varchar(16) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1059 DEFAULT CHARSET=utf8mb4;

/**
 * 问卷明细选项
 */
CREATE TABLE `data_tp_queschoise` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `item_id` bigint(20) DEFAULT NULL,
  `awno` varchar(255) DEFAULT NULL,
  `awname` varchar(255) DEFAULT NULL,
  `awvise` varchar(255) DEFAULT NULL,
  `input_falg` tinyint(4) NOT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5339 DEFAULT CHARSET=utf8mb4;


/**
 * 问卷答案表
 */
CREATE TABLE `data_tp_result` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `user_key` varchar(255) DEFAULT NULL,
  `pack_id` bigint(20) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `choise_id` bigint(20) DEFAULT NULL,
  `choise_value` varchar(255) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `ip_addr` varchar(128) DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/**
 * 读取记录
 */
CREATE TABLE `data_doc_reader` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `ap_id` bigint(20) DEFAULT NULL,
  `user_key` varchar(255) DEFAULT NULL,
  `doc_id` bigint(20) DEFAULT NULL,
  `ip_addr` varchar(128) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  `app_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 用户信息
 */
CREATE TABLE `account_info` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `ap_id` bigint(20) DEFAULT NULL,
  `head` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `realname` varchar(255) DEFAULT NULL,
  `sex` tinyint(4) NOT NULL DEFAULT '0',
  `info` varchar(255) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/** 20190924 增加宠物表 **/
CREATE TABLE `mm_binddata_pet` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `ap_id` bigint(20) DEFAULT NULL,
  `basic_id` bigint(20) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `headpic` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `catcode` varchar(255) DEFAULT NULL,
  `catname` varchar(255) DEFAULT NULL,
  `gender` tinyint(4) DEFAULT NULL,
  `birthday` char(10) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `stature` varchar(16) DEFAULT NULL,
  `sterilisation` varchar(16) DEFAULT NULL,
  `give_birth` varchar(16) DEFAULT NULL,
  `hair_color` varchar(16) DEFAULT NULL,
  `disease_history` varchar(255) DEFAULT NULL,
  `hair_care_brand` varchar(255) DEFAULT NULL,
  `food_brand` varchar(255) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  `nature` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;





