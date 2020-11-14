DROP DATABASE IF EXISTS acg;
CREATE DATABASE acg
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_general_ci;
USE acg;

DROP TABLE IF EXISTS `tbl_item_list`;
CREATE TABLE `tbl_item_list` (
    id              INT   AUTO_INCREMENT PRIMARY KEY,
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    status          VARCHAR(20) DEFAULT ''                                         NOT NULL COMMENT '0:列表插入，1：已爬完',
    category        VARCHAR(20)  NOT NULL COMMENT '类别',
    title           VARCHAR(200) NOT NULL COMMENT '标题名称',
    url             VARCHAR(255) NOT NULL DEFAULT '' COMMENT '详情页url',
    page_number     INT          NOT NULL COMMENT '列表页页码',
    icon_image_url  VARCHAR(255) NULL,
    producer        VARCHAR(100) NOT NULL DEFAULT '' COMMENT '厂商',
    series          VARCHAR(100) NOT NULL DEFAULT '' COMMENT '系列',
    level           VARCHAR(100) NOT NULL DEFAULT '' COMMENT '级别或分类',
    name            VARCHAR(100) NOT NULL DEFAULT '' COMMENT '名称',
    version         VARCHAR(100) NOT NULL DEFAULT '' COMMENT '版本',
    material_type   VARCHAR(100) NOT NULL DEFAULT '' COMMENT '材料种类',
    toy_class       VARCHAR(100) NOT NULL DEFAULT '' COMMENT '玩具分类',
    participator    VARCHAR(100) NOT NULL DEFAULT '' COMMENT '参与创建',

    release_date    VARCHAR(100) NOT NULL DEFAULT '' COMMENT '发售时间',
    rate            VARCHAR(100) NOT NULL DEFAULT '' COMMENT '比例',
    spec            VARCHAR(200) NOT NULL DEFAULT '' COMMENT '规格',
    price           VARCHAR(100) NOT NULL DEFAULT '' COMMENT '价格',
    person_name     VARCHAR(300) NOT NULL DEFAULT '' COMMENT '人物名',
    proto_person    VARCHAR(100) NOT NULL DEFAULT '' COMMENT '原型师',
    number          VARCHAR(100) NOT NULL DEFAULT '' COMMENT '编号',
    banner_image_url  VARCHAR(255) NULL COMMENT '首图地址'
) ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    ROW_FORMAT = DYNAMIC;

ALTER TABLE `tbl_item_list` ADD COLUMN  `release_date` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '发售时间';
ALTER TABLE `tbl_item_list` ADD COLUMN  `rate` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '发售时间';
ALTER TABLE `tbl_item_list` ADD COLUMN  `spec` VARCHAR(200) NOT NULL DEFAULT '' COMMENT '发售时间';
ALTER TABLE `tbl_item_list` ADD COLUMN  `price` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '发售时间';
ALTER TABLE `tbl_item_list` ADD COLUMN  `person_name` VARCHAR(300) NOT NULL DEFAULT '' COMMENT '发售时间';
ALTER TABLE `tbl_item_list` ADD COLUMN  `proto_person` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '发售时间';
ALTER TABLE `tbl_item_list` ADD COLUMN  `number` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '发售时间';
ALTER TABLE `tbl_item_list` ADD COLUMN  `banner_image_url` VARCHAR(255) NULL COMMENT '首图地址';

DROP TABLE IF EXISTS `tbl_factory_list`;
CREATE TABLE `tbl_factory_list` (
    id                INT   AUTO_INCREMENT PRIMARY KEY,
    create_time       DATETIME DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    update_time       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    status            VARCHAR(20) DEFAULT ''                                         NOT NULL COMMENT '0:列表插入，1：已爬完',
    title             VARCHAR(100) NOT NULL COMMENT '标题名称',
    url               VARCHAR(255) NOT NULL DEFAULT '' COMMENT '详情页url',
    introduce         VARCHAR(500) NOT NULL DEFAULT '' COMMENT '简介',
    type              VARCHAR(200) NOT NULL DEFAULT '' COMMENT '类型',
    creator           VARCHAR(200) NOT NULL DEFAULT '' COMMENT '参与创建',
    series            VARCHAR(1000) NOT NULL DEFAULT '' COMMENT '玩具系列',
    banner_image_url  VARCHAR(255) NULL COMMENT '首图地址'
) ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    ROW_FORMAT = DYNAMIC;
