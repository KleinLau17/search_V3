package com.shangxun.search.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName channel_entity
 */
@TableName(value ="channel_entity")
@Data
public class ChannelEntity implements Serializable {
    /**
     * 数据库编号
     */
    @TableId
    private String id;

    /**
     * 操作机构ID
     */
    private String companyId;

    /**
     * 群主体
     */
    private String groupEntityCode;

    /**
     * 子主体
     */
    private String entityCode;

    /**
     * 子主体名称
     */
    private String codeName;

    /**
     * 主体类型
     */
    private String entityType;

    /**
     * 主体类型名称
     */
    private String typeName;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModify;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ChannelEntity other = (ChannelEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCompanyId() == null ? other.getCompanyId() == null : this.getCompanyId().equals(other.getCompanyId()))
            && (this.getGroupEntityCode() == null ? other.getGroupEntityCode() == null : this.getGroupEntityCode().equals(other.getGroupEntityCode()))
            && (this.getEntityCode() == null ? other.getEntityCode() == null : this.getEntityCode().equals(other.getEntityCode()))
            && (this.getCodeName() == null ? other.getCodeName() == null : this.getCodeName().equals(other.getCodeName()))
            && (this.getEntityType() == null ? other.getEntityType() == null : this.getEntityType().equals(other.getEntityType()))
            && (this.getTypeName() == null ? other.getTypeName() == null : this.getTypeName().equals(other.getTypeName()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModify() == null ? other.getGmtModify() == null : this.getGmtModify().equals(other.getGmtModify()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCompanyId() == null) ? 0 : getCompanyId().hashCode());
        result = prime * result + ((getGroupEntityCode() == null) ? 0 : getGroupEntityCode().hashCode());
        result = prime * result + ((getEntityCode() == null) ? 0 : getEntityCode().hashCode());
        result = prime * result + ((getCodeName() == null) ? 0 : getCodeName().hashCode());
        result = prime * result + ((getEntityType() == null) ? 0 : getEntityType().hashCode());
        result = prime * result + ((getTypeName() == null) ? 0 : getTypeName().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModify() == null) ? 0 : getGmtModify().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", companyId=").append(companyId);
        sb.append(", groupEntityCode=").append(groupEntityCode);
        sb.append(", entityCode=").append(entityCode);
        sb.append(", codeName=").append(codeName);
        sb.append(", entityType=").append(entityType);
        sb.append(", typeName=").append(typeName);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModify=").append(gmtModify);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
