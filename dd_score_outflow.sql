/*
 Navicat Premium Data Transfer

 Source Server         : workhub
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : cloud2

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 28/09/2018 14:48:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dd_score_outflow
-- ----------------------------
DROP TABLE IF EXISTS `dd_score_outflow`;
CREATE TABLE `dd_score_outflow`  (
  `id` bigint(20) NOT NULL,
  `uid` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `source_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `expend_score` bigint(20) NULL DEFAULT NULL COMMENT '消耗积分',
  `expend_detail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消耗原因',
  `udp_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
