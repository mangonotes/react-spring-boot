package org.greenmango.service.membership.dao.enitty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TB_MEMBER")
public class MemberDO extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    protected Integer memberId;
    @Column(name = "FIRST_NAME")
    protected String firstName;
    @Column(name = "LAST_NAME")
    protected String lastName;
    @Column(name = "DOB")
    protected Date dataOfBirth;
    @Column(name = "POSTAL_CODE")
    protected String postalCode;

    @OneToOne(mappedBy="memberDO" ,cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    protected FileDO fileDO;

    public MemberDO(String firstName, String lastName, Date dataOfBirth, String postalCode, String createdBy, Date createdDate) {
        super(createdBy, createdDate);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dataOfBirth = dataOfBirth;
        this.postalCode = postalCode;

    }

    public MemberDO() {
        super(null, null);
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDataOfBirth() {
        return dataOfBirth;
    }

    public void setDataOfBirth(Date dataOfBirth) {
        this.dataOfBirth = dataOfBirth;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public FileDO getFileDO() {
        return fileDO;
    }

    public void setFileDO(FileDO fileDO) {
        this.fileDO = fileDO;
    }
}
