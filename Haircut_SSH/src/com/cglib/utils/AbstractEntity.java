package com.cglib.utils;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * The Class AbstractEntity.
 */
@MappedSuperclass
public class AbstractEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 90101711163849670L;

    /** 乐观锁版本. */
    private Integer cversion;

    /** 创建者(登录账户). */
    private String creator;

    /** 创建时间. */
    private Date create_date;

    /** 最后更新者(登录帐号). */
    private String modifier;

    /** 最后更新时间. */
    private Date modify_date;
    
    /** 删除标志 */
    private Short del_flg;

    @Override
    public String toString() {
        return "AbstractEntity{" +
                ", cversion=" + cversion +
                ", creator='" + creator + '\'' +
                ", create_date=" + create_date +
                ", modifier='" + modifier + '\'' +
                ", modify_date=" + modify_date +
                ", del_flg=" + del_flg +
                '}';
    }


    /**
     * Gets the version.
     * 
     * @return the version
     */
    @Column(name = "cversion")
    public Integer getCversion() {
        return cversion;
    }

    /**
     * Sets the version.
     * 
     * @param version
     *            the new version
     */
    public void setCversion(Integer cversion) {
        this.cversion = cversion;
    }

    @Column(name = "creator")
    public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "create_date")
	public Date getCreate_date() {
		return create_date;
	}


	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	@Column(name = "modifier")
	public String getModifier() {
		return modifier;
	}


	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name = "modify_date")
	public Date getModify_date() {
		return modify_date;
	}


	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}

	@Column(name = "del_flg")
	public Short getDel_flg() {
		return del_flg;
	}


	public void setDel_flg(Short del_flg) {
		this.del_flg = del_flg;
	}

	

}
