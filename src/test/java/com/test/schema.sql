#脚本信息
CREATE TABLE `script`
(
    `id`          int(9) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `name`        varchar(255)  NOT NULL,
    `content`     varchar(1000) NOT NULL,
    `directory`   varchar(50)   DEFAULT NULL,
    `description` varchar(500)  DEFAULT NULL,
    `create_user` varchar(30)   DEFAULT NULL,
    `update_user` varchar(30)   DEFAULT NULL,
    `create_time` datetime      DEFAULT NULL,
    `update_time` datetime      DEFAULT NULL
);

#脚本的扩展属性
CREATE TABLE `script_expand`
(
    `id`          int(9) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `script_id`   varchar(9)    NOT NULL,
    `name`        varchar(255)  NOT NULL,
    `type`        int(1)        NOT NULL,
    `weight`      int(9)        NOT NULL,
    `content`     varchar(1000) NOT NULL,
    `description` varchar(500)  DEFAULT NULL,
    `base`        varchar(500)  DEFAULT NULL,
    `create_user` varchar(30)   DEFAULT NULL,
    `update_user` varchar(30)   DEFAULT NULL,
    `create_time` datetime      DEFAULT NULL,
    `update_time` datetime      DEFAULT NULL
);

#执行记录信息
CREATE TABLE `exec_record`
(
    `id`          int(9) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `script_id`   varchar(9)    NOT NULL,
    `name`        varchar(50)   NOT NULL,
    `script_name` varchar(50)   NOT NULL,
    `content`     varchar(1000) NOT NULL,
    `status`          int(1)        NOT NULL,
    `base`        varchar(500)  DEFAULT NULL,
    `create_user` varchar(30)   DEFAULT NULL,
    `update_user` varchar(30)   DEFAULT NULL,
    `create_time` datetime      DEFAULT NULL,
    `update_time` datetime      DEFAULT NULL
);

#生成的文件信息
CREATE TABLE `record_file`
(
    `id`          int(9) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `record_id`   varchar(9)    NOT NULL,
    `name`        varchar(255)  NOT NULL,
    `description` varchar(500)  DEFAULT NULL,
    `address`     varchar(500)  DEFAULT NULL,
    `base`        varchar(500)  DEFAULT NULL,
    `create_user` varchar(30)   DEFAULT NULL,
    `update_user` varchar(30)   DEFAULT NULL,
    `create_time` datetime      DEFAULT NULL,
    `update_time` datetime      DEFAULT NULL
)