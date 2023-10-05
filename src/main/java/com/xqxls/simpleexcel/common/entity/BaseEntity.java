package com.xqxls.simpleexcel.common.entity;

import com.xqxls.simpleexcel.common.domain.UserDto;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Id;
import lombok.Data;

/**
 * @Description:
 * @Author: huzhuo
 * @Date: Created in 2023/9/24 23:19
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 0L;

    @ApiModelProperty(value = "", required = false)
    @Id
    @Column(name = "id")
    private Long id;//

    @ApiModelProperty(value = "租户id", required = false)
    @Column(name = "tenement_id")
    private Long tenementId;//租户id

    @ApiModelProperty(value = "创建时间", required = false)
    @Column(name = "create_time")
    private LocalDateTime createTime;//创建时间

    @ApiModelProperty(value = "更新时间", required = false)
    @Column(name = "update_time")
    private LocalDateTime updateTime;//更新时间

    @ApiModelProperty(value = "创建人员id", required = false)
    @Column(name = "create_id")
    private Long createId;//创建人员id

    @ApiModelProperty(value = "更新人员id", required = false)
    @Column(name = "update_id")
    private Long updateId;//更新人员id

    @ApiModelProperty(value = "创建人员名字", required = false)
    @Column(name = "create_name")
    private String createName;//创建人员名字

    @ApiModelProperty(value = "更新人员名字", required = false)
    @Column(name = "update_name")
    private String updateName;//更新人员名字

    @ApiModelProperty(value = "是否已删除", required = false)
    @Column(name = "is_del")
    private Integer isDel;//是否已删除

    @ApiModelProperty(value = "是否启用", required = false)
    @Column(name = "is_enable")
    private Integer isEnable;//是否启用

    @ApiModelProperty(value = "版本", required = false)
    @Column(name = "version")
    private Integer version;//版本


    /**
     * 设置数据库实体的默认值 自动生成id
     *
     * @param userDto 用户信息
     */
    @ApiModelProperty(hidden = true)
    public void setDefaultVal(UserDto userDto) {
        setDefaultVal(userDto.getTenementId(), userDto.getId(), userDto.getUsername());
    }

    /**
     * 设置数据库实体的默认值(插入新数据时用)
     *
     * @param tenementId 租户id
     * @param createId   创建用户/首次更新用户id
     * @param createName 创建用户/首次更新用户名字
     */
    @ApiModelProperty(hidden = true)
    public void setDefaultVal(Long tenementId, Long createId, String createName) {
        this.tenementId = tenementId;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.createId = createId;
        this.updateId = createId;
        this.createName = createName;
        this.updateName = createName;
        this.isDel = 0;
        // 是否启用 由前端确定
        if (isEnable == null) {
            this.isEnable = 0;
        }
        this.version = 1;

    }

    /**
     * 更新时设置默认值
     *
     * @param userDto 用户信息
     */
    @ApiModelProperty(hidden = true)
    public void setUpdateVal(UserDto userDto) {
        setUpdateVal(userDto.getId(), userDto.getUsername());
    }

    /**
     * 更新时设置默认值
     *
     * @param updateId   操作用户id
     * @param updateName 操作用户名字
     */
    @ApiModelProperty(hidden = true)
    public void setUpdateVal(Long updateId, String updateName) {
        this.updateTime = LocalDateTime.now();
        this.updateId = updateId;
        this.updateName = updateName;
    }

}

