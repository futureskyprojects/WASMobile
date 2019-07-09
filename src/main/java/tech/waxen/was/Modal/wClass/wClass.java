package tech.waxen.was.Modal.wClass;

import java.io.Serializable;

public class wClass implements Serializable {
    public final static String TAG = wClass.class.getSimpleName();
    int cId;
    String cName;
    String cSchoolYear;

    public wClass(int cId, String cName, String cSchoolYear) {
        this.cId = cId;
        this.cName = cName;
        this.cSchoolYear = cSchoolYear;
    }

    public wClass(String cName, String cSchoolYear) {
        this.cName = cName;
        this.cSchoolYear = cSchoolYear;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcSchoolYear() {
        return cSchoolYear;
    }

    public void setcSchoolYear(String cSchoolYear) {
        this.cSchoolYear = cSchoolYear;
    }
}
