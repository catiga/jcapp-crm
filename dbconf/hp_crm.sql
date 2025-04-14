/*
 Navicat MySQL Data Transfer

 Source Server         : 10.3.66.160
 Source Server Type    : MySQL
 Source Server Version : 50643
 Source Host           : 10.3.66.160:3306
 Source Schema         : hp_crm

 Target Server Type    : MySQL
 Target Server Version : 50643
 File Encoding         : 65001

 Date: 28/04/2019 22:16:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account_info
-- ----------------------------
DROP TABLE IF EXISTS `account_info`;
CREATE TABLE `account_info` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `ap_id` bigint(20) DEFAULT NULL,
  `head` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `realname` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `sex` tinyint(4) NOT NULL DEFAULT '0',
  `info` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  `user_no` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `birthday` char(10) DEFAULT NULL,
  `weight` decimal(10,2) DEFAULT NULL,
  `height` decimal(10,2) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `postcode` varchar(255) DEFAULT NULL,
  `countrycode` varchar(255) DEFAULT NULL,
  `countryname` varchar(255) DEFAULT NULL,
  `ethnicitycode` varchar(255) DEFAULT NULL,
  `ethnicityname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for data_account_session
-- ----------------------------
DROP TABLE IF EXISTS `data_account_session`;
CREATE TABLE `data_account_session`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `proj_id` bigint(20) NULL DEFAULT NULL,
  `basic_id` bigint(20) NULL DEFAULT NULL,
  `ap_id` bigint(20) NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `rushed` bigint(20) NULL DEFAULT NULL,
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `lograns` int(11) NOT NULL DEFAULT 0,
  `logtype` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `token`(`token`(191)) USING BTREE,
  INDEX `a_time`(`a_time`) USING BTREE,
  INDEX `proj_id`(`proj_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for data_account_third
-- ----------------------------
DROP TABLE IF EXISTS `data_account_third`;
CREATE TABLE `data_account_third`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account_id` bigint(20) NULL DEFAULT NULL,
  `part_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `part_union_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `belong_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `pid` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for mm_account_project_mc
-- ----------------------------
DROP TABLE IF EXISTS `mm_account_project_mc`;
CREATE TABLE `mm_account_project_mc`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `basic_id` bigint(20) NULL DEFAULT NULL,
  `pid` bigint(20) NULL DEFAULT NULL,
  `sid` bigint(20) NULL DEFAULT NULL,
  `mc_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mc_pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mc_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mc_mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mc_level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `balance` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `point` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `period` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `discount` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `limit_discount_by_num` int(11) NULL DEFAULT 0,
  `min_recharge_money` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '允许最小充值金额',
  `a_time` datetime(0) NOT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `can_recharge` tinyint(11) NULL DEFAULT 1,
  `can_buy_with_online_pay` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0',
  `goods_discount` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mch_id` bigint(20) NULL DEFAULT NULL,
  `id_card` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' ',
  `amremark` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' ',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' ',
  `hardware_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '硬件编号',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '会员等级生效时间',
  `old_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for mm_account_project_mc_detail
-- ----------------------------
DROP TABLE IF EXISTS `mm_account_project_mc_detail`;
CREATE TABLE `mm_account_project_mc_detail`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NULL DEFAULT NULL,
  `acmid` bigint(20) NULL DEFAULT NULL,
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `o_c` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NOT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `remarks` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `amount` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0.00',
  `num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交易编号',
  `code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `d_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退款对应的原交易编号',
  `old_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退款对应的原交易编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for mm_data_account_basic
-- ----------------------------
DROP TABLE IF EXISTS `mm_data_account_basic`;
CREATE TABLE `mm_data_account_basic`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `mobile`(`mobile`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for mm_dic_common_config
-- ----------------------------
DROP TABLE IF EXISTS `mm_dic_common_config`;
CREATE TABLE `mm_dic_common_config`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cc_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cc_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cc_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `rollback` tinyint(4) NOT NULL DEFAULT 0,
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `jt` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `entry` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for mm_member_card_hierarchy
-- ----------------------------
DROP TABLE IF EXISTS `mm_member_card_hierarchy`;
CREATE TABLE `mm_member_card_hierarchy`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `mc_id` bigint(20) NULL DEFAULT NULL,
  `h_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `h_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `h_point` bigint(11) NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `hindex` int(11) NULL DEFAULT NULL,
  `htimes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mcr_period` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `init_recharge` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `supp_recharge` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否允许充值 1允许',
  `least_recharge` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `close_recharge` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否允许线上充值：0不允许',
  `cr_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cr_type_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cr_discount` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `day_buy_limit` int(11) NULL DEFAULT NULL,
  `show_buy_limit` int(11) NULL DEFAULT NULL,
  `utimekey` bigint(20) NULL DEFAULT NULL,
  `gift_recharge` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '赠送金额',
  `getmode` int(11) NOT NULL DEFAULT 1 COMMENT '0 直接获取，1直接升级',
  `getpay` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '支付金额',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `prefix` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `handle_fee` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `handle_fee_by` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `j_r` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bapa` tinyint(4) NULL DEFAULT NULL,
  `belop` tinyint(4) NULL DEFAULT NULL,
  `hodu` tinyint(4) NULL DEFAULT NULL,
  `bgc` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `validate_period` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时期值',
  `validate_type` varchar(225) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'FIXED 固定有效期，GETTIME 按领用日期',
  `wx_card_id` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `other_rule` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_htimes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `movie_hitmes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for mm_member_card_hierarchy_detail
-- ----------------------------
DROP TABLE IF EXISTS `mm_member_card_hierarchy_detail`;
CREATE TABLE `mm_member_card_hierarchy_detail`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `mch_id` bigint(20) NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for mm_member_card_hierarchy_subjoin
-- ----------------------------
DROP TABLE IF EXISTS `mm_member_card_hierarchy_subjoin`;
CREATE TABLE `mm_member_card_hierarchy_subjoin`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `mch_id` bigint(20) NOT NULL COMMENT '会员等级id',
  `set_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '// 0010 分组 0011 商品 0000 通用折扣',
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `set_id` bigint(20) NOT NULL COMMENT '折扣id',
  `set_discount` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '折扣 折扣 0.01 - 9.99',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for mm_member_card_rule
-- ----------------------------
DROP TABLE IF EXISTS `mm_member_card_rule`;
CREATE TABLE `mm_member_card_rule`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NULL DEFAULT NULL,
  `sid` bigint(20) NULL DEFAULT NULL,
  `mcr_type` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `mcr_status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '10',
  `mcflk` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1000',
  `mc_level` tinyint(11) NOT NULL DEFAULT 1,
  `is_outer` tinyint(4) NOT NULL DEFAULT 0,
  `outer_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `outer_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `outer_pc_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `outer_pc_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `prefix` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_mc_prefix` tinyint(4) NOT NULL DEFAULT 1,
  `a_mch_prefix` tinyint(4) NOT NULL DEFAULT 1,
  `start_cn` int(11) NULL DEFAULT NULL,
  `end_cn` int(11) NULL DEFAULT NULL,
  `ct_cn` int(11) NULL DEFAULT NULL,
  `supp_jf` tinyint(4) NOT NULL DEFAULT 1,
  `def_jf_add_ratio` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1/1',
  `def_jf_con_ratio` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '100/1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for mm_order_mc
-- ----------------------------
DROP TABLE IF EXISTS `mm_order_mc`;
CREATE TABLE `mm_order_mc`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `card_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `init_pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '888888',
  `mch_id` bigint(20) NULL DEFAULT NULL,
  `acmid` bigint(20) NULL DEFAULT NULL,
  `basic_id` bigint(20) NULL DEFAULT NULL,
  `order_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `total_amount` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_amount` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `pay_time` datetime(0) NULL DEFAULT NULL,
  `take_time` datetime(0) NULL DEFAULT NULL,
  `pay_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `order_status` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `o_c` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `com_order` bigint(20) NULL DEFAULT NULL,
  `ops` tinyint(4) NOT NULL DEFAULT 0,
  `drawback_time` datetime(0) NULL DEFAULT NULL,
  `refund_time` datetime(0) NULL DEFAULT NULL,
  `gift_amount` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` bigint(11) NOT NULL,
  `puid` bigint(11) NULL DEFAULT NULL COMMENT '发卡id',
  `puname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发卡名称',
  `s_id` bigint(20) NULL DEFAULT NULL,
  `s_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `store_id` bigint(20) NULL DEFAULT NULL,
  `store_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `id_card` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pre_item_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for mm_order_recharge_mc
-- ----------------------------
DROP TABLE IF EXISTS `mm_order_recharge_mc`;
CREATE TABLE `mm_order_recharge_mc`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `basic_id` bigint(20) NULL DEFAULT NULL,
  `order_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `total_amount` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_amount` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mch_id` bigint(20) NULL DEFAULT NULL COMMENT '区分充值渠道',
  `a_time` datetime(0) NOT NULL,
  `pay_time` datetime(0) NULL DEFAULT NULL,
  `pay_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `order_status` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `o_c` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '8001',
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` bigint(20) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `com_order` bigint(20) NULL DEFAULT NULL,
  `store_id` bigint(20) NULL DEFAULT NULL,
  `card_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `taked_time` datetime(0) NULL DEFAULT NULL,
  `acmid` bigint(20) NULL DEFAULT NULL,
  `ops` tinyint(20) NULL DEFAULT NULL COMMENT '区分充值渠道',
  `puid` bigint(20) NULL DEFAULT NULL COMMENT '区分充值渠道',
  `puname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区分充值渠道',
  `store_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `refund_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for mm_pre_mc_order_info
-- ----------------------------
DROP TABLE IF EXISTS `mm_pre_mc_order_info`;
CREATE TABLE `mm_pre_mc_order_info`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `pid` bigint(20) NULL DEFAULT NULL,
  `order_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ss` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '00',
  `a_time` datetime(0) NULL DEFAULT NULL,
  `total_num` int(11) NULL DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `mcr_id` bigint(20) NULL DEFAULT NULL,
  `mcr_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mch_id` bigint(20) NULL DEFAULT NULL,
  `mch_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for mm_pre_mc_order_item
-- ----------------------------
DROP TABLE IF EXISTS `mm_pre_mc_order_item`;
CREATE TABLE `mm_pre_mc_order_item`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NULL DEFAULT NULL,
  `card_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pub_ss` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发卡状态',
  `pub_order_mc` bigint(20) NULL DEFAULT NULL COMMENT '关联的发卡订单id',
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `cards_identifier` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '卡编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE `data_account_address` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `basic_id` bigint(20) DEFAULT NULL,
  `ap_id` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `country` varchar(255) NOT NULL DEFAULT '0000',
  `province` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `zone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `province_code` varchar(255) DEFAULT NULL,
  `city_code` varchar(255) DEFAULT NULL,
  `zone_code` varchar(255) DEFAULT NULL,
  `is_def` tinyint(1) NOT NULL DEFAULT '0',
  `zipcode` varchar(16) DEFAULT NULL,
  `proj_id` bigint(20) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1192 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;

