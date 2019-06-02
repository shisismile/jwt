package com.smile.model.sys;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author smile
 */
@Data
@TableName("sys_perm")
public class SysPerm implements Serializable {
    /**
     * 角色ID
     */
    @TableId
    private Long id;

    /**
     * 角色名称
     */
    @NotBlank(message="角色名称不能为空")
    private String permName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建者ID
     */
    private Long createUserId;


    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
