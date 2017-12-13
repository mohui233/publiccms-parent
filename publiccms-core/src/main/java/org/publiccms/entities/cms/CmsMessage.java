package org.publiccms.entities.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

import com.publiccms.common.generator.annotation.GeneratorColumn;

@Entity
@Table(name = "cms_message")
public class CmsMessage implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;
    @GeneratorColumn(title = "ID")
    private Integer id;
    @GeneratorColumn(title = "问题一", condition = true, like = true)
    private String q1;
    @GeneratorColumn(title = "问题二", condition = true, like = true)
    private String q2;
    @GeneratorColumn(title = "问题三", condition = true, like = true)
    private String q3;
    @GeneratorColumn(title = "站点")
    private String station;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @GeneratorColumn(title = "创建日期")
    private Date createDate;
    
    public CmsMessage() {
    }
    
	public CmsMessage(Integer id, String q1, String q2, String q3, String station, Date createDate) {
		this.id = id;
		this.q1 = q1;
		this.q2 = q2;
		this.q3 = q3;
		this.station = station;
		this.createDate = createDate;
	}
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "q1", nullable = false, length = 50)
    public String getQ1() {
        return q1;
    }

    public void setQ1(String q1) {
        this.q1 = q1 == null ? null : q1.trim();
    }

    @Column(name = "q2", nullable = false, length = 50)
    public String getQ2() {
        return q2;
    }

    public void setQ2(String q2) {
        this.q2 = q2 == null ? null : q2.trim();
    }

    @Column(name = "q3", nullable = false, length = 50)
    public String getQ3() {
        return q3;
    }

    public void setQ3(String q3) {
        this.q3 = q3 == null ? null : q3.trim();
    }


    @Column(name = "station", nullable = false, length = 50)
	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, length = 19)
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}