-- mysql启动关闭外键的方法
SET FOREIGN_KEY_CHECKS=0;

-- -------------------------------
-- 创建用户表
-- -------------------------------
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `userid` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户ID',
  `username` varchar(11) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '用户密码',
  `salt` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '盐',
  `nickname` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `sex` tinyint(1) COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `birthday` DATETIME COLLATE utf8_bin DEFAULT NULL COMMENT '生日',
  `card` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号',
  `address` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `headpicture` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '头像地址',
  `email` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `truename` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `paypass` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '支付密码',
  `paysalt` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '支付盐',
  `worklist` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '帐套列表',
  `sysidlist` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '系统列表',
  `shopid` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '商户码',
  `utype` varchar(8) COLLATE utf8_bin DEFAULT NULL COMMENT '用户类型',
  `starttime` DATETIME COLLATE utf8_bin DEFAULT NULL COMMENT '有效开始时间',
  `endtime` DATETIME COLLATE utf8_bin DEFAULT NULL COMMENT '有效结束时间',
  `locked` tinyint(1) COLLATE utf8_bin DEFAULT '0' COMMENT '是否被锁定',
  PRIMARY KEY (`userid`)
)ENGINE=InnoDB CHARACTER SET utf8 DEFAULT CHARSET=utf8 COLLATE utf8_bin;

-- -------------------------------
-- 创建角色表
-- -------------------------------
DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `roleid` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '角色ID',
  `rotype` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色类型',
  `rolename` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名',
  `description` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `availabe` tinyint(1) COLLATE utf8_bin DEFAULT '1' COMMENT '是否启用',
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB CHARACTER SET utf8 DEFAULT CHARSET=utf8 COLLATE utf8_bin;
-- -------------------------------
-- 创建角色用户表
-- -------------------------------
DROP TABLE IF EXISTS `role_users`;

CREATE TABLE `role_users` (
  `roleid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用户ID',
  `userid` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`userid`, `roleid`)
)ENGINE=InnoDB CHARACTER SET utf8 DEFAULT CHARSET=utf8 COLLATE utf8_bin;


-- -------------------------------
-- 创建菜单表
-- -------------------------------
DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `sysid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '系统ID',
  `menuid` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '菜单ID',
  `menuname` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '菜单名',
  `pmenuid` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '父菜单ID',
  `level` int(4) COLLATE utf8_bin NOT NULL COMMENT '级次',
  `uri` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '菜单名',
  `availabe` tinyint(1) COLLATE utf8_bin DEFAULT '1' COMMENT '是否启用',
  PRIMARY KEY (`menuid`)
) ENGINE=InnoDB CHARACTER SET utf8 DEFAULT CHARSET=utf8 COLLATE utf8_bin;


-- -------------------------------
-- 创建角色菜单表
-- -------------------------------
DROP TABLE IF EXISTS `role_menu`;

CREATE TABLE `role_menu` (
  `roleid` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '角色ID',
  `menuid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`roleid`, `menuid`)
)ENGINE=InnoDB CHARACTER SET utf8 DEFAULT CHARSET=utf8 COLLATE utf8_bin;

-- -------------------------------
-- 创建资源表
-- -------------------------------
DROP TABLE IF EXISTS `resource`;

CREATE TABLE `resource` (
  `resourceid` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '资源ID',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '资源名',
  `retype` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '资源类型',
  `reindex` int(4) COLLATE utf8_bin DEFAULT NULL COMMENT '排序',
  `parentids` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '父编号列表',
  `availabe` tinyint(1) COLLATE utf8_bin DEFAULT '1' COMMENT '是否启用',
  PRIMARY KEY (`resourceid`)
) ENGINE=InnoDB CHARACTER SET utf8 DEFAULT CHARSET=utf8 COLLATE utf8_bin;

-- -------------------------------
-- 创建角色资源表
-- -------------------------------
DROP TABLE IF EXISTS `role_resource`;

CREATE TABLE `role_resource` (
  `roreid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '系统ID',
  `sysid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '系统ID',
  `roleid` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '角色ID',
  `resourceid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '资源ID',
  `operaid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '操作ID',
  PRIMARY KEY (`roreid`)
)ENGINE=InnoDB CHARACTER SET utf8 DEFAULT CHARSET=utf8 COLLATE utf8_bin;


-- -------------------------------
-- 创建菜单表
-- -------------------------------
DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `sysid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '系统ID',
  `menuid` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '菜单ID',
  `menuname` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '菜单名',
  `pmenuid` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '父菜单ID',
  `level` int(4) COLLATE utf8_bin NOT NULL COMMENT '级次',
  `uri` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '菜单名',
  `availabe` tinyint(1) COLLATE utf8_bin DEFAULT '1' COMMENT '是否启用',
  PRIMARY KEY (`menuid`)
) ENGINE=InnoDB CHARACTER SET utf8 DEFAULT CHARSET=utf8 COLLATE utf8_bin;


-- -------------------------------
-- 创建角色菜单表
-- -------------------------------
DROP TABLE IF EXISTS `role_menu`;

CREATE TABLE `role_menu` (
  `roleid` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '角色ID',
  `menuid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`roleid`, `menuid`)
)ENGINE=InnoDB CHARACTER SET utf8 DEFAULT CHARSET=utf8 COLLATE utf8_bin;


-- -------------------------------
-- 创建操作表
-- -------------------------------
DROP TABLE IF EXISTS `operation`;

CREATE TABLE `operation` (
  `operaid` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '操作ID',
  `operaname` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '操作名',
  `operatype` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '操类型',
  `operation` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '操作动作',
  `availabe` tinyint(1) COLLATE utf8_bin DEFAULT '1' COMMENT '是否启用',
  PRIMARY KEY (`operaid`)
) ENGINE=InnoDB CHARACTER SET utf8 DEFAULT CHARSET=utf8 COLLATE utf8_bin;


-- -------------------------------
-- 创建安全信息表
-- -------------------------------
DROP TABLE IF EXISTS `sysconfig`;

CREATE TABLE `sysconfig` (
	`id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '主码',
	`createdate` datetime COLLATE utf8_bin NOT NULL COMMENT '创建时间',
	`modifydate` datetime COLLATE utf8_bin NOT NULL COMMENT '修改时间',
	`conno` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '编号',
	`ename` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '英文名',
	`name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '中文名',
	`url` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '接口url',
	`method` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '方法名',
	`userid` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '使用id',
	`appid` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '软件id',
	`payment_type` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '支付类型',
	`seller_id` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '卖家账号',
	`seller_password` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '卖家密码',
	`privatekey` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '卖家私钥',
	`status` varchar(8) COLLATE utf8_bin DEFAULT NULL COMMENT '状态',
	`timeout` int(32) COLLATE utf8_bin DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARACTER SET utf8 DEFAULT CHARSET=utf8 COLLATE utf8_bin;


-- -------------------------------
-- 创建IP过滤表
-- -------------------------------
DROP TABLE IF EXISTS `sysipfilter`;

CREATE TABLE `sysipfilter` (
	`id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '主码',
	`createdate` datetime COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
	`modifydate` datetime COLLATE utf8_bin DEFAULT NULL COMMENT '修改时间',
	`ip` varchar(15) COLLATE utf8_bin NOT NULL COMMENT 'IP',
	`name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
	`port` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '端口',
	`remark` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
	`ispassd` tinyint(1) COLLATE utf8_bin DEFAULT '1' COMMENT '是否允许/false(0)为禁止访问/true(1)为允许访问',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARACTER SET utf8 DEFAULT CHARSET=utf8 COLLATE utf8_bin;
