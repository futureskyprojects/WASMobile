package tech.waxen.was.Modal.Student;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.waxen.was.Modal.wClass.wClass;
import tech.waxen.was.Modal.wClass.wClassHandler;
import tech.waxen.was.R;
import tech.waxen.was.Utils;

public class Student {
    private int cId;
    private int stId;
    private String stCode;
    private String stName;
    private String stEmail;

    public Student(int cId, int stId, String stCode, String stName, String stEmail) {
        this.cId = cId;
        this.stId = stId;
        this.stCode = stCode;
        this.stName = stName;
        this.stEmail = stEmail;
    }

    public Student(int cId, String stCode, String stName, String stEmail) {
        this.cId = cId;
        this.stCode = stCode;
        this.stName = stName;
        this.stEmail = stEmail;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public int getStId() {
        return stId;
    }

    public void setStId(int stId) {
        this.stId = stId;
    }

    public String getStCode() {
        return stCode;
    }

    public void setStCode(String stCode) {
        this.stCode = stCode;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getStEmail() {
        return stEmail;
    }

    public void setStEmail(String stEmail) {
        this.stEmail = stEmail;
    }

    public void ShowInfo(Context mContext) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_student_info, null);
        builder.setCancelable(false);
        builder.setView(v);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        // View init
        v.findViewById(R.id.dialog_student_info_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        //
        ImageView ivBackground = v.findViewById(R.id.dialog_student_info_backgronud);
        CircleImageView ciAvatar = v.findViewById(R.id.dialog_student_info_avatar);

        TextView tvStudentName = v.findViewById(R.id.dialog_student_info_student_name);
        TextView tvStudentCode = v.findViewById(R.id.dialog_student_info_student_code);
        TextView tvStudentEmail = v.findViewById(R.id.dialog_student_info_student_email);
        TextView tvStudentClass = v.findViewById(R.id.dialog_student_info_student_class);

        // Load image
        Glide.with(mContext).load("https://www.gravatar.com/avatar/" + Utils.md5(getStEmail())).into(ivBackground);
        Glide.with(mContext).load("https://www.gravatar.com/avatar/" + Utils.md5(getStEmail())).into(ciAvatar);
        // Other info
        tvStudentName.setText(getStName());
        tvStudentCode.setText(getStCode());
        tvStudentEmail.setText(getStEmail());
        // Get class name
        wClass wclass = new wClassHandler(mContext).get(getcId());
        if (wclass == null)
            tvStudentClass.setText("Không có lớp");
        else
            tvStudentClass.setText(wclass.getcName());
    }
}
