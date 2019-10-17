
/**
 * 修改客户信息表
 */
ALTER TABLE `account_info` ADD COLUMN `pid` BIGINT(20);

ALTER TABLE `account_info` ADD COLUMN `birthday` CHAR(10);

ALTER TABLE `account_info` ADD COLUMN `weight` DECIMAL(10,2);
ALTER TABLE `account_info` ADD COLUMN `height` DECIMAL(10,2);


ALTER TABLE `account_info` ADD COLUMN `email` VARCHAR(255);

ALTER TABLE `account_info` ADD COLUMN `mobile` VARCHAR(255);

ALTER TABLE `account_info` ADD COLUMN `postcode` VARCHAR(255);

ALTER TABLE `account_info` ADD COLUMN `countrycode` VARCHAR(255);

ALTER TABLE `account_info` ADD COLUMN `countryname` VARCHAR(255);

ALTER TABLE `account_info` ADD COLUMN `ethnicitycode` VARCHAR(255);

ALTER TABLE `account_info` ADD COLUMN `ethnicityname` VARCHAR(255);
