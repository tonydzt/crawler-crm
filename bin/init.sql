-- auto-generated definition
CREATE TABLE assignment
(
  id              BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  org_id          BIGINT                             NULL
  COMMENT '公司ID',
  applicant       VARCHAR(160)                       NULL
  COMMENT '申请人',
  contact         VARCHAR(160)                       NULL
  COMMENT '联系方式',
  address         VARCHAR(160)                       NULL
  COMMENT '地址',
  trademark       TINYTEXT                           NULL
  COMMENT '商标',
  registration_no TINYTEXT                           NULL
  COMMENT '注册号',
  date            DATE                               NULL,
  category        INT                                NULL,
  assignment_name VARCHAR(20)                        NULL,
  legal_person    VARCHAR(20)                        NULL,
  is_again        TINYINT(2)                         NULL,
  has_tel         TINYINT                            NULL
  COMMENT '是否有手机',
  insert_time     DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  update_time     DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  mobile_num      INT                                NULL
  COMMENT '座机数',
  tel_num         INT                                NULL
  COMMENT '手机数',
  CONSTRAINT assignment_org_id_date_pk
  UNIQUE (org_id, date)
)
  COMMENT '分配表'
  ENGINE = InnoDB;

-- auto-generated definition
CREATE TABLE crawler_config
(
  id          BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  `key`       VARCHAR(50)                        NULL
  COMMENT '配置项名称',
  value       VARCHAR(100)                       NULL
  COMMENT '配置项值',
  insert_time DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT crawler_config_key_uindex
  UNIQUE (`key`)
)
  ENGINE = InnoDB;

-- auto-generated definition
CREATE TABLE email
(
  id          BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  org_id      BIGINT                             NULL
  COMMENT '公司ID',
  email       VARCHAR(50)                        NULL
  COMMENT '邮箱',
  insert_time DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  CONSTRAINT email_email_uindex
  UNIQUE (email)
)
  COMMENT '邮箱'
  ENGINE = InnoDB;

-- auto-generated definition
CREATE TABLE employee
(
  id          BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  name        VARCHAR(50)                        NULL
  COMMENT '名字',
  insert_time DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
)
  COMMENT '员工表'
  ENGINE = InnoDB;

-- auto-generated definition
CREATE TABLE excel_status
(
  id            BIGINT AUTO_INCREMENT
  COMMENT '主键'
    PRIMARY KEY,
  path          VARCHAR(50)                        NULL
  COMMENT '路径',
  date          DATE                               NULL
  COMMENT '解析日期',
  file_name     VARCHAR(50)                        NULL
  COMMENT '文件名',
  status        TINYINT DEFAULT '0'                NOT NULL
  COMMENT '状态：0-未解析，1-解析中，2-已解析',
  duplicate_num INT                                NULL
  COMMENT '注册号重复数量',
  insert_time   DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  update_time   DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  valid_num     INT                                NULL
  COMMENT '校验有效数据',
  total_num     INT                                NULL
  COMMENT '总数量'
)
  COMMENT 'excel解析状态表'
  ENGINE = InnoDB;

-- auto-generated definition
CREATE TABLE mobile
(
  id          BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  org_id      BIGINT                             NOT NULL
  COMMENT '公司ID',
  mobile_no   VARCHAR(11)                        NULL
  COMMENT '手机号',
  insert_time DATETIME DEFAULT CURRENT_TIMESTAMP NULL
  COMMENT '插入时间',
  CONSTRAINT mobile_mobile_no_uindex
  UNIQUE (mobile_no)
)
  COMMENT '手机'
  ENGINE = InnoDB;

-- auto-generated definition
CREATE TABLE org
(
  id                 BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  employee_id        BIGINT                             NULL
  COMMENT '归属业务员ID',
  name               VARCHAR(100)                       NULL
  COMMENT '公司名',
  legal_person       VARCHAR(20)                        NULL
  COMMENT '法人',
  registered_capital VARCHAR(30)                        NULL
  COMMENT '注册资本',
  insert_time        DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  has_contact        TINYINT DEFAULT '0'                NULL
  COMMENT '是否有联系方式 0-没有 1-有',
  CONSTRAINT org_name_pk
  UNIQUE (name)
)
  COMMENT '公司'
  ENGINE = InnoDB;

-- auto-generated definition
CREATE TABLE tel
(
  id          BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  org_id      BIGINT                             NOT NULL
  COMMENT '公司ID',
  tel_no      VARCHAR(20)                        NULL
  COMMENT '座机号',
  insert_time DATETIME DEFAULT CURRENT_TIMESTAMP NULL
  COMMENT ' 插入时间',
  CONSTRAINT tel_tel_no_uindex
  UNIQUE (tel_no)
)
  COMMENT '固定电话'
  ENGINE = InnoDB;

-- auto-generated definition
CREATE TABLE trademark
(
  id              BIGINT AUTO_INCREMENT
    PRIMARY KEY,
  excel_status_id BIGINT                             NULL
  COMMENT '解析批次号',
  category        INT                                NULL
  COMMENT '类别',
  registration_no INT                                NULL
  COMMENT '注册号',
  trademark       VARCHAR(200)                       NULL
  COMMENT '商标',
  date            DATE                               NULL
  COMMENT '申请日期',
  applicant       VARCHAR(160)                       NULL
  COMMENT '申请人',
  address         VARCHAR(160)                       NULL
  COMMENT '地址',
  agent           VARCHAR(160)                       NULL
  COMMENT '代理机构',
  service         VARCHAR(500)                       NULL
  COMMENT '服务',
  status          TINYINT                            NULL
  COMMENT '法律状态',
  tel             VARCHAR(20)                        NULL
  COMMENT '座机',
  insert_time     DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  update_time     DATETIME DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
  mobile          VARCHAR(11)                        NULL
  COMMENT '手机',
  craw_status     TINYINT DEFAULT '0'                NULL
  COMMENT '爬取状态 0-未爬取，1-正在爬取，2-已爬取，3-重复，跳过爬取',
  org_id          BIGINT                             NULL
  COMMENT '公司ID',
  is_again        TINYINT                            NULL
  COMMENT '是否是老客户新机会',
  employee_id     BIGINT                             NULL
  COMMENT '分配员工ID',
  CONSTRAINT trademark_registrationNo_uindex
  UNIQUE (registration_no)
)
  ENGINE = InnoDB;