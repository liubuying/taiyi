-- 部门表
CREATE TABLE `taiyi_wecom_department` (
    `id` bigint NOT NULL COMMENT '部门ID',
    `name` varchar(64) NOT NULL COMMENT '部门名称',
    `parent_id` bigint DEFAULT NULL COMMENT '父部门ID',
    `order_num` int DEFAULT '0' COMMENT '排序',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业微信部门表';

-- 员工表
CREATE TABLE `taiyi_wecom_employee` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `userid` varchar(64) NOT NULL COMMENT '企业微信员工ID',
    `name` varchar(64) NOT NULL COMMENT '员工姓名',
    `mobile` varchar(20) DEFAULT NULL COMMENT '手机号码',
    `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
    `gender` tinyint DEFAULT NULL COMMENT '性别：1-男，2-女',
    `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
    `thumb_avatar` varchar(255) DEFAULT NULL COMMENT '头像缩略图URL',
    `status` tinyint DEFAULT '1' COMMENT '状态：1-已激活，2-已禁用，4-未激活，5-退出企业',
    `enable` tinyint DEFAULT '1' COMMENT '是否启用：1-启用，0-禁用',
    `alias` varchar(64) DEFAULT NULL COMMENT '别名',
    `position` varchar(64) DEFAULT NULL COMMENT '职位信息',
    `address` varchar(255) DEFAULT NULL COMMENT '地址',
    `main_department` bigint DEFAULT NULL COMMENT '主部门ID',
    `is_leader` tinyint DEFAULT '0' COMMENT '是否为部门负责人：1-是，0-否',
    `extattr` text COMMENT '扩展属性(JSON)',
    `external_profile` text COMMENT '对外属性(JSON)',
    `external_position` varchar(64) DEFAULT NULL COMMENT '对外职务',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_userid` (`userid`),
    KEY `idx_mobile` (`mobile`),
    KEY `idx_email` (`email`),
    KEY `idx_main_department` (`main_department`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业微信员工表';

-- 员工部门关联表
CREATE TABLE `taiyi_wecom_employee_department` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `employee_id` bigint NOT NULL COMMENT '员工ID',
    `department_id` bigint NOT NULL COMMENT '部门ID',
    `is_leader` tinyint DEFAULT '0' COMMENT '是否为部门负责人：1-是，0-否',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_employee_department` (`employee_id`,`department_id`),
    KEY `idx_department_id` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工部门关联表';