-- 系统用户
CREATE TABLE `sys_user`
(
    `id`             bigint      NOT NULL,
    `username`       varchar(50) NOT NULL COMMENT '用户名',
    `password`       varchar(100) COMMENT '密码',
    `email`          varchar(100) COMMENT '邮箱',
    `mobile`         varchar(100) COMMENT '手机号',
    `status`         tinyint COMMENT '状态  0：禁用   1：正常',
    `create_user_id` bigint(20) COMMENT '创建者ID',
    `create_time`    datetime COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='系统用户';
-- 角色
CREATE TABLE `sys_role`
(
    `id`             bigint NOT NULL,
    `role_name`      varchar(100) COMMENT '角色名称',
    `remark`         varchar(100) COMMENT '备注',
    `create_user_id` bigint(20) COMMENT '创建者ID',
    `create_time`    datetime COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='角色';

-- 用户与角色对应关系
CREATE TABLE `sys_user_role`
(
    `id`      bigint NOT NULL,
    `user_id` bigint COMMENT '用户ID',
    `role_id` bigint COMMENT '角色ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户与角色对应关系';

-- 权限
CREATE TABLE `sys_perm`
(
    `id`             bigint NOT NULL,
    `perm_name`      varchar(100) COMMENT '权限名称',
    `remark`         varchar(100) COMMENT '备注',
    `create_user_id` bigint(20) COMMENT '创建者ID',
    `create_time`    datetime COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='权限';

-- 用户与角色对应关系
CREATE TABLE `sys_user_role`
(
    `id`      bigint NOT NULL,
    `perm_id` bigint COMMENT '权限ID',
    `role_id` bigint COMMENT '角色ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='权限与角色对应关系';

-- 菜单
CREATE TABLE `sys_menu`
(
    `id`        bigint NOT NULL,
    `parent_id` bigint COMMENT '父菜单ID，一级菜单为0',
    `name`      varchar(50) COMMENT '菜单名称',
    `url`       varchar(200) COMMENT '菜单URL',
    `type`      int COMMENT '类型   0：目录   1：菜单   2：按钮',
    `icon`      varchar(50) COMMENT '菜单图标',
    `order_num` int COMMENT '排序',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='菜单管理';
-- 角色与菜单对应关系
CREATE TABLE `sys_role_menu`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `role_id` bigint COMMENT '角色ID',
    `menu_id` bigint COMMENT '菜单ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='角色与菜单对应关系';

-- 系统配置信息
CREATE TABLE `sys_config`
(
    `id`          bigint NOT NULL,
    `param_key`   varchar(50) COMMENT 'key',
    `param_value` varchar(2000) COMMENT 'value',
    `status`      tinyint DEFAULT 1 COMMENT '状态   0：隐藏   1：显示',
    `remark`      varchar(500) COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE INDEX (`param_key`)
) ENGINE = `InnoDB`
  DEFAULT CHARACTER SET utf8 COMMENT ='系统配置信息表 字典表';
