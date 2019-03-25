package com.ldz.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 档案柜管理表
 */
@Table(name = "record_management")
public class RecordManagement implements Serializable {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 位置
     */
    private String position;

    /**
     * 容量
     */
    private Integer capacity;

    /**
     * 现有数量
     */
    private Integer num;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private String updateTime;

    /**
     * 修改人
     */
    private String updater;

    /**
     * 科目
     */
    private String sub;

    /**
     * 编码
     */
    @Column(name = "archives_code")
    private String archivesCode;
    /**
     * 创建人
     */
    @Column(name = "cjr")
    private String cjr;

    /**
     * 创建时间
     */
    @Column(name = "cjsj")
    private String cjsj;
    
    /**
     * 机构代码
     */
    @Column(name = "jgdm")
    private String jgdm;

    /**
     * 机构名称
     */
    @Column(name = "jgmc")
    private String jgmc;
    /**
     * 新增时，仓库的数量
     */
    @Transient
    private Integer archivesNum;
    /**
     * 仓库起始编码
     */
    @Transient
    private String archivesCodeStart;

    public String getArchivesCodeStart() {
        return archivesCodeStart;
    }

    public void setArchivesCodeStart(String archivesCodeStart) {
        this.archivesCodeStart = archivesCodeStart;
    }

    public Integer getArchivesNum() {
        return archivesNum;
    }

    public void setArchivesNum(Integer archivesNum) {
        this.archivesNum = archivesNum;
    }

    public String getJgdm() {
		return jgdm;
	}

	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}

	public String getJgmc() {
		return jgmc;
	}

	public void setJgmc(String jgmc) {
		this.jgmc = jgmc;
	}

	public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }
    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取位置
     *
     * @return position - 位置
     */
    public String getPosition() {
        return position;
    }

    /**
     * 设置位置
     *
     * @param position 位置
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * 获取容量
     *
     * @return capacity - 容量
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * 设置容量
     *
     * @param capacity 容量
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    /**
     * 获取现有数量
     *
     * @return num - 现有数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置现有数量
     *
     * @param num 现有数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取修改人
     *
     * @return updater - 修改人
     */
    public String getUpdater() {
        return updater;
    }

    /**
     * 设置修改人
     *
     * @param updater 修改人
     */
    public void setUpdater(String updater) {
        this.updater = updater;
    }

    /**
     * 获取科目
     *
     * @return sub - 科目
     */
    public String getSub() {
        return sub;
    }

    /**
     * 设置科目
     *
     * @param sub 科目
     */
    public void setSub(String sub) {
        this.sub = sub;
    }

    /**
     * 获取编码
     *
     * @return archives_code - 编码
     */
    public String getArchivesCode() {
        return archivesCode;
    }

    /**
     * 设置编码
     *
     * @param archivesCode 编码
     */
    public void setArchivesCode(String archivesCode) {
        this.archivesCode = archivesCode;
    }

    public enum InnerColumn {
        id("id"),
        position("position"),
        capacity("capacity"),
        num("num"),
        updateTime("update_time"),
        updater("updater"),
        sub("sub"),
        cjr("cjr"),
        cjsj("cjsj"),
        archivesCode("archives_code");

        private final String column;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        InnerColumn(String column) {
            this.column = column;
        }

        public String desc() {
            return this.column + " DESC";
        }

        public String asc() {
            return this.column + " ASC";
        }
    }
}