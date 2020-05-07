package org.greenmango.service.membership.dao.enitty;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TB_FILE")
public class FileDO extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "FILE_ID")
    protected Integer memberId;

    @Lob
    @Column(name = "FILE")
    protected byte[] file;

    @Column(name = "FILE_TYPE")
    protected String fileType;
    @Column(name = "FILE_NAME")
    protected String fileName;
    @OneToOne(fetch =FetchType.LAZY)
    @MapsId
    private MemberDO memberDO;

    public FileDO(byte[] file, String fileName, String fileType, String createdBy, Date createdDate) {
        super(createdBy, createdDate);
        this.file = file;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public FileDO() {
        super(null, null);
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public MemberDO getMemberDO() {
        return memberDO;
    }

    public void setMemberDO(MemberDO memberDO) {
        this.memberDO = memberDO;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
