package org.greenmango.service.membership.dao.enitty;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable {
    @Column(name="CREATED_BY")
    protected String createdBy;
    @Column(name="CREATED_DT")
    protected  Date createdDate;
    @Column(name="MODIFIED_BY")
    protected String modifiedBy;
    @Column(name="MODIFIED_DT")
    protected Date modifiedDate;


    public BaseEntity(String createdBy, Date createdDate){
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
