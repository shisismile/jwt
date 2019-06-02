/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.smile.model.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户
 *
 * @author smile
 */
@Data
@TableName("sys_user")
public class SysUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户ID
	 */
	@TableId
	private Long id;

	/**
	 * 用户名
	 */
	@NotBlank(message="用户名不能为空")
	private String username;

	/**
	 * 密码
	 */
	@NotBlank(message="密码不能为空")
	private String password;

	/**
	 * 邮箱
	 */
	@NotBlank(message="邮箱不能为空")
	@Email(message="邮箱格式不正确")
	private String email;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 状态  0：禁用   1：正常
	 */
	private Integer status;

	/**
	 * 角色ID列表
	 */
	@TableField(exist=false)
	private List<SysRole> roleList;

	/**
	 * 创建者ID
	 */
	private Long createUserId;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 用户ip
	 */
	@TableField(exist=false)
	private String ip;

	/**
	 * 用户请求uri
	 */
	@TableField(exist=false)
	private String uri;

	/**
	 * 登录终端
	 */
	@TableField(exist=false)
	private String from;

	/**
	 * jti 唯一代码
	 */
	@TableField(exist=false)
	private String jti;

}
