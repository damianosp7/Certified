package com.example.certified.model;

import javax.persistence.*;

@Entity
@Table(name = "docs")
public class Docs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user")
    private String user;

    private String docName;

    private String docType;

    @Column(name = "timestamp")
    private String timestamp;

    @Lob
    private byte[] data;

    public Docs(String user, String docName, String docType, String timestamp, byte[] data) {
        this.user = user;
        this.docName = docName;
        this.docType = docType;
        this.timestamp = timestamp;
        this.data = data;
    }

    public Docs() {

    }

    @JoinTable(
            name = "docs",
            joinColumns = @JoinColumn(
                    name = "user", referencedColumnName = "email"))

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
