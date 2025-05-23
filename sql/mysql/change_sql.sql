ALTER TABLE taiyi_db.taiyi_company ADD creator_id BIGINT NOT NULL COMMENT '创建人信息';
ALTER TABLE taiyi_db.taiyi_company ADD operator_id BIGINT NULL COMMENT '操作人';



ALTER TABLE taiyi_db.taiyi_company ADD tenant_id BIGINT NOT NULL COMMENT '租户入驻信息';



ALTER TABLE taiyi_db.taiyi_company ADD deleted INT DEFAULT 0 NULL COMMENT '删除';



ALTER TABLE taiyi_db.taiyi_legal_person ADD deleted INT DEFAULT 0 NULL COMMENT '是否删除';




ALTER TABLE taiyi_db.taiyi_wechat_login_record ADD deleted INT DEFAULT 0 NULL COMMENT '删除 0 未删除 1删除';


ALTER TABLE taiyi_db.taiyi_wechat_login_record ADD creator_id BIGINT NOT NULL COMMENT '创建人';
ALTER TABLE taiyi_db.taiyi_wechat_login_record ADD operator_id BIGINT NULL COMMENT '操作人ID';


CREATE TABLE taiyi_db.taiyi_wechat_login_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键ID',
    employee_user_id BIGINT COMMENT '员工ID',
    wx_union_id VARCHAR(128) COMMENT '微信UnionId',
    wx_no VARCHAR(64) NOT NULL COMMENT '微信No',
    nickname VARCHAR(64) COMMENT '昵称',
    login_qr_code VARCHAR(255) COMMENT '登录二维码',
    port INT COMMENT '端口',
    pid INT COMMENT '进程ID',
    ip VARCHAR(64) COMMENT 'IP',
    login_time DATETIME COMMENT '登录时间',
    gmt_create    DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    is_offline TINYINT(1) DEFAULT 0 COMMENT '是否下线（0-在线，1-下线）'
) COMMENT='微信用户登录记录表';


ALTER TABLE taiyi_db.taiyi_wx_account_pool ADD status INT DEFAULT 0 NOT NULL COMMENT '账号状态 0 未分配 1 已分配';


ALTER TABLE taiyi_db.taiyi_domain_wx_account_relation ADD deleted INT DEFAULT 0 NOT NULL COMMENT '是否删除';


ALTER TABLE taiyi_db.taiyi_domain_name DROP KEY domain_name;


ALTER TABLE taiyi_db.taiyi_domain_name MODIFY COLUMN operator_id bigint unsigned NULL COMMENT '操作人ID';


ALTER TABLE taiyi_db.taiyi_domain_name MODIFY COLUMN company_id bigint unsigned NULL COMMENT '所属公司ID';


ALTER TABLE taiyi_db.taiyi_domain_name CHANGE creatorId creator_id bigint NOT NULL COMMENT '创建人';

ALTER TABLE taiyi_db.taiyi_domain_wx_account_relation ADD creator_id BIGINT NOT NULL COMMENT '创建人';




ALTER TABLE taiyi_db.taiyi_domain_name CHANGE `desc` domain_desc text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '域名备注';

ALTER TABLE taiyi_db.taiyi_domain_name ADD `domain` varchar(64) NOT NULL COMMENT '域名IP或者域名';
ALTER TABLE taiyi_db.taiyi_domain_name ADD `desc` TEXT NULL COMMENT '域名备注';


ALTER TABLE taiyi_db.taiyi_domain_name ADD deleted INT DEFAULT 0 NULL COMMENT '是否删除 0未删除 1已删除';
ALTER TABLE taiyi_db.taiyi_domain_name ADD creatorId BIGINT NOT NULL COMMENT '创建人';




ALTER TABLE taiyi_db.taiyi_wx_account_pool ADD creator_nick_name varchar(100) NOT NULL COMMENT '创建人昵称';

ALTER TABLE taiyi_db.taiyi_wx_account_pool ADD operator_nick_name varchar(100) NULL COMMENT '修改人昵称';

ALTER TABLE taiyi_db.taiyi_wx_account_pool ADD creator_id BIGINT NULL COMMENT '创建人';

ALTER TABLE taiyi_db.taiyi_wx_account_pool ADD tenant_id BIGINT DEFAULT 1 NULL COMMENT '租户字段';

ALTER TABLE taiyi_db.taiyi_wx_account_pool ADD deleted TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除';


CREATE TABLE taiyi_legal_person (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(100) NOT NULL COMMENT '法人姓名',
    cert_no VARCHAR(20) UNIQUE COMMENT '证件号码',
    cert_type VARCHAR(20) UNIQUE COMMENT '证件类型',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '电子邮箱',
    company_id BIGINT UNSIGNED UNIQUE COMMENT '关联公司ID',
    gmt_create DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (company_id) REFERENCES taiyi_company(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='法人信息表';


CREATE TABLE taiyi_company (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(255) NOT NULL COMMENT '公司名称',
    unified_social_credit_code VARCHAR(64) UNIQUE NOT NULL COMMENT '统一社会信用代码',
    liaison VARCHAR(100) COMMENT '联系人',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '邮箱',
    industry VARCHAR(100) COMMENT '所属行业',
    registered_address VARCHAR(255) COMMENT '注册地址',
    office_address VARCHAR(255) COMMENT '入驻办公地址',
    entry_date DATE COMMENT '入驻日期',
    exit_date DATE COMMENT '退出日期',
    status TINYINT DEFAULT 1 COMMENT '公司状态：0=退出，1=在营，2=审核中',
    business_license_url VARCHAR(255) COMMENT '营业执照链接',
    gmt_create DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='入驻公司信息表';

-- 域名管理表
CREATE TABLE taiyi_domain_name (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    domain_name VARCHAR(255) NOT NULL UNIQUE COMMENT '绑定域名',
    status TINYINT DEFAULT 1 COMMENT '状态：1=启用，0=禁用',
    company_id BIGINT UNSIGNED NOT NULL COMMENT '所属公司ID',
    operator_id BIGINT UNSIGNED NOT NULL COMMENT '操作人ID',
    gmt_create DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='域名管理表';


-- 公司与员工微绑定关系
CREATE TABLE taiyi_company_user_relation (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    company_id BIGINT UNSIGNED NOT NULL COMMENT '公司ID',
    user_id BIGINT UNSIGNED NOT NULL COMMENT '员工ID',
    user_name VARCHAR(128)   COMMENT '员工名称',
    operator_id BIGINT UNSIGNED NOT NULL COMMENT '操作人ID',
    gmt_create DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='入驻公司信息表';


-- 客服账号
CREATE TABLE taiyi_wx_account_pool  (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '主键 ID',
    company_id BIGINT UNSIGNED NOT NULL COMMENT '公司 ID',
    account_type VARCHAR(32) NOT NULL COMMENT '账号类型，如 wechat/work_wechat',
    account_id VARCHAR(100) NOT NULL COMMENT '账号唯一 ID，如 openid/userid',
    union_id VARCHAR(100) COMMENT '微信平台统一标识',
    device VARCHAR(64) COMMENT '使用设备',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(64) COMMENT '邮箱',
    nick_name VARCHAR(20) COMMENT '备注',
    avatar VARCHAR(255) COMMENT '头像',
    expire_time DATETIME COMMENT '过期时间',
    operator_id BIGINT UNSIGNED NOT NULL COMMENT '操作人ID',
    is_expired TINYINT DEFAULT 0 COMMENT '是否过期 0 未过期 1 过期',
    gmt_create DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信池账号';

-- 微信账号与域名绑定关系
CREATE TABLE taiyi_domain_wx_account_relation (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '主键 ID',
    union_id VARCHAR(32) NOT NULL COMMENT '账号unionId',
    domain_id BIGINT UNSIGNED NOT NULL COMMENT  '域名管理 ID',
    status TINYINT DEFAULT 1 COMMENT '状态：0=禁用，1=启用',
    operator_id BIGINT UNSIGNED COMMENT '修改人',
    gmt_create DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='域名与微信账号关系表';

-- 员工与微信账号的绑定关系
CREATE TABLE taiyi_users_wx_account_relation (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '主键 ID',
    employee_id BIGINT UNSIGNED NOT NULL COMMENT '员工 ID',
    union_id VARCHAR(32) NOT NULL COMMENT '账号union ID',
    status TINYINT DEFAULT 1 COMMENT '状态：0=禁用，1=启用',
    operator_id BIGINT UNSIGNED COMMENT '修改人',
    gmt_create DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工与微信账号关系表';





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


-- 企业微信应用表（包含企业应用的相关信息）
-- 不同企业可能存在相同的 agent_id，因此使用 (corpid, agent_id) 的组合来确保应用的唯一性。
CREATE TABLE `wechat_app` (
                              `app_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键，自增ID',
                              `enterprise_id` INT UNSIGNED NOT NULL COMMENT '关联的企业ID',
                              `agent_id` INT UNSIGNED NOT NULL COMMENT '应用AgentId',
                              `app_secret` VARCHAR(128) NOT NULL COMMENT '应用Secret',
                              `name` VARCHAR(100) NOT NULL COMMENT '应用名称',
                              `square_logo_url` VARCHAR(255) DEFAULT NULL COMMENT '方形Logo地址',
                              `description` TEXT DEFAULT NULL COMMENT '应用描述',
                              `is_closed` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否关闭：0=启用，1=关闭',
                              `redirect_domain` VARCHAR(255) DEFAULT NULL COMMENT '授权回调域名',
                              `report_location_flag` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否上报地理位置：0=否，1=是',
                              `is_report_enter` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否进入会话上报：0=否，1=是',
                              `home_url` VARCHAR(255) NOT NULL COMMENT '应用主页URL',
                              `customized_publish_status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '发布状态：0=未发布，1=已发布',
                              `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              PRIMARY KEY (`app_id`),
                              UNIQUE KEY `uk_enterprise_agent` (`enterprise_id`, `agent_id`),
                              KEY `idx_name` (`name`),
                              KEY `idx_redirect_domain` (`redirect_domain`),
                              CONSTRAINT `fk_wechat_app_enterprise` FOREIGN KEY (`enterprise_id`) REFERENCES `Enterprise_Information`(`enterprise_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业微信应用配置表';

-- 插入应用信息：
INSERT INTO `wechat_app` (`enterprise_id`, `agent_id`, `app_secret`, `name`, `square_logo_url`, `description`, `home_url`)
VALUES (1, 1000002, 'fU3zd_ULx5HOw5ETKBQuXnfP35QeoPOH5ZriBHf3CFE', 'HR助手', 'https://p.qlogo.cn/bizmail/FicwmI50icF8GH9ib7rUAYR5kicLTgP265naVFQKnleqSlRhiaBx7QA9u7Q/0', 'HR服务与员工自助平台', 'https://open.work.weixin.qq.com');


-- 企业信息应用授权表
CREATE TABLE `Enterprise_Information` (
                                          `enterprise_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键，自增ID',
                                          `corpid` VARCHAR(64) NOT NULL COMMENT '企业ID（CorpID）',
                                          `name` VARCHAR(100) NOT NULL COMMENT '企业简称',
                                          `square_logo_url` VARCHAR(255) DEFAULT NULL COMMENT '联系电话',
                                          `description` TEXT DEFAULT NULL COMMENT '企业域名',
                                          `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                          PRIMARY KEY (`enterprise_id`),
                                          UNIQUE KEY `uk_corpid` (`corpid`),
                                          KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业基本信息表';

-- 插入企业信息：
INSERT INTO `Enterprise_Information` (`corpid`, `name`, `square_logo_url`, `description`)
VALUES ('ww7d23c84b96863bc3', '示例企业', 'https://example.com/logo.png', '示例企业描述');



-- 微信账号基础表（包含客服和普通用户）
CREATE TABLE wx_accounts (
                             wx_id VARCHAR(32) PRIMARY KEY COMMENT '微信ID（openid）',
                             account_type ENUM('客服', '普通用户') NOT NULL DEFAULT '普通用户',
                             nickname VARCHAR(64) NOT NULL COMMENT '微信昵称',
                             avatar VARCHAR(255) COMMENT '头像URL',
                             gender TINYINT COMMENT '0-未知 1-男 2-女',
                             region VARCHAR(127) COMMENT '地区',
                             created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                             last_active DATETIME COMMENT '最后活跃时间',
                             INDEX idx_account_type (account_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- 客服管理关系表
CREATE TABLE service_management (
                                    appid varchar(64) NOT NULL  comment '设备码',
                                    manager_id INT NOT NULL COMMENT '平台用户ID',
                                    service_wx_id VARCHAR(32) NOT NULL COMMENT '客服微信ID',
                                    bind_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                                    status ENUM('正常', '已解绑') DEFAULT '正常',
                                    PRIMARY KEY (manager_id, service_wx_id),
    -- FOREIGN KEY (manager_id) REFERENCES 用户表(user_id) ON DELETE CASCADE,
                                    FOREIGN KEY (service_wx_id) REFERENCES wx_accounts(wx_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 微信好友关系表
CREATE TABLE wx_friendships (
                                friendship_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                service_wx_id VARCHAR(32) NOT NULL COMMENT '客服微信ID',
                                customer_wx_id VARCHAR(32) NOT NULL COMMENT '客户微信ID',
                                add_source ENUM('扫码', '搜索', '名片分享') DEFAULT '扫码',
                                add_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                                friend_status ENUM('正常', '已删除', '被拉黑') DEFAULT '正常',
                                last_interaction DATETIME COMMENT '最后沟通时间',
                                UNIQUE KEY uniq_relationship (service_wx_id, customer_wx_id),
                                FOREIGN KEY (service_wx_id) REFERENCES wx_accounts(wx_id) ON DELETE CASCADE,
                                FOREIGN KEY (customer_wx_id) REFERENCES wx_accounts(wx_id) ON DELETE CASCADE,
                                INDEX idx_friend_status (friend_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 好友标签管理表
CREATE TABLE friend_tags (
                             tag_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                             friendship_id BIGINT NOT NULL COMMENT '好友关系ID',
                             tag_name VARCHAR(50) NOT NULL COMMENT '标签名称',
                             tag_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                             FOREIGN KEY (friendship_id) REFERENCES wx_friendships(friendship_id) ON DELETE CASCADE,
                             INDEX idx_tag_name (tag_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 好友备注管理表
CREATE TABLE friend_remarks (
                                remark_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                friendship_id BIGINT NOT NULL COMMENT '好友关系ID',
                                remark_content TEXT NOT NULL COMMENT '备注内容',
                                update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                version INT DEFAULT 1 COMMENT '版本号',
                                FOREIGN KEY (friendship_id) REFERENCES wx_friendships(friendship_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- 查询客服（wx_id=KF123）管理的所有客户
SELECT c.wx_id, c.nickname, f.add_time,
       GROUP_CONCAT(t.tag_name) AS tags,
       r.remark_content
FROM wx_friendships f
         JOIN wx_accounts c ON f.customer_wx_id = c.wx_id
         LEFT JOIN friend_tags t ON f.friendship_id = t.friendship_id
         LEFT JOIN friend_remarks r ON f.friendship_id = r.friendship_id
WHERE f.service_wx_id = 'KF123'
  AND f.friend_status = '正常'
GROUP BY c.wx_id;

-- 查询平台用户（user_id=100）管理的所有客服
SELECT s.service_wx_id, a.nickname,
       COUNT(f.customer_wx_id) AS customer_count
FROM service_management s
         JOIN wx_accounts a ON s.service_wx_id = a.wx_id
         LEFT JOIN wx_friendships f ON s.service_wx_id = f.service_wx_id
WHERE s.manager_id = 100
  AND s.status = '正常'
GROUP BY s.service_wx_id;