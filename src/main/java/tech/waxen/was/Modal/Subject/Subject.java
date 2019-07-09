package tech.waxen.was.Modal.Subject;

import java.io.Serializable;

public class Subject implements Serializable {
    public final static String TAG = Subject.class.getSimpleName();

    private int sId;
    private String sName;
    private String sLength;
    private String sDescription;
    private String sCreateAt;
    private String sEndAt;

    public Subject(int sId, String sName, String sLength, String sDescription, String sCreateAt, String sEndAt) {
        this.sId = sId;
        this.sName = sName;
        this.sLength = sLength;
        this.sDescription = sDescription;
        this.sCreateAt = sCreateAt;
        this.sEndAt = sEndAt;
    }

    public Subject(String sName, String sLength, String sDescription, String sCreateAt, String sEndAt) {
        this.sName = sName;
        this.sLength = sLength;
        this.sDescription = sDescription;
        this.sCreateAt = sCreateAt;
        this.sEndAt = sEndAt;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsLength() {
        return sLength;
    }

    public void setsLength(String sLength) {
        this.sLength = sLength;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }

    public String getsCreateAt() {
        return sCreateAt;
    }

    public void setsCreateAt(String sCreateAt) {
        this.sCreateAt = sCreateAt;
    }

    public String getsEndAt() {
        return sEndAt;
    }

    public void setsEndAt(String sEndAt) {
        this.sEndAt = sEndAt;
    }
}
