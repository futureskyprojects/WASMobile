package tech.waxen.was.Modal.ConnectSubjectStudent;

import java.io.Serializable;

public class ConnectSubjectStudent implements Serializable {
    public final static String TAG = ConnectSubjectStudent.class.getSimpleName();

    int ssId;
    int sId;
    int stId;
    int ssHave;
    boolean isAttendance = false;

    public boolean isAttendance() {
        return isAttendance;
    }

    public void setAttendance(boolean attendance) {
        isAttendance = attendance;
    }

    public ConnectSubjectStudent(int ssId, int sId, int stId, int have) {
        this.ssId = ssId;
        this.sId = sId;
        this.stId = stId;
        this.ssHave = have;
    }

    public ConnectSubjectStudent(int sId, int stId, int have) {
        this.sId = sId;
        this.stId = stId;
        this.ssHave = have;
    }

    public int getSsId() {
        return ssId;
    }

    public void setSsId(int ssId) {
        this.ssId = ssId;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public int getStId() {
        return stId;
    }

    public void setStId(int stId) {
        this.stId = stId;
    }

    public int getSsHave() {
        return ssHave;
    }

    public void setSsHave(int ssHave) {
        this.ssHave = ssHave;
    }
}
