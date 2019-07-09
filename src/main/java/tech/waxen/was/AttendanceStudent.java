package tech.waxen.was;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import tech.waxen.was.Modal.ConnectSubjectStudent.AttendanceAdapter;
import tech.waxen.was.Modal.ConnectSubjectStudent.ConnectSubjectStudent;
import tech.waxen.was.Modal.ConnectSubjectStudent.SubjectStudentHandler;
import tech.waxen.was.Modal.Student.Student;
import tech.waxen.was.Modal.Student.StudentHandler;
import tech.waxen.was.Modal.Subject.Subject;

public class AttendanceStudent extends AppCompatActivity {
    public final static int REQUEST_QR_SCANNER_CODE = 1403;
    public final static String DATA_STUDENT_CODE = "STUDENT_CODE";

    List<ConnectSubjectStudent> subjectStudents;
    Subject subject;
    AttendanceAdapter mAdapter;

    void loadDatabase() {
        if (subjectStudents == null)
            subjectStudents = new ArrayList<>();
        subjectStudents.clear();
        subjectStudents.addAll(new SubjectStudentHandler(AttendanceStudent.this).getAll(subject.getsId()));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_student);

        subject = (Subject) getIntent().getSerializableExtra(Subject.TAG);
        if (subject == null) {
            Toasty.warning(this, "Có lỗi khi lấy thông tin các sinh viên học môn này.", Toasty.LENGTH_SHORT, true).show();
            finish();
        } else {
            initViews();
        }
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText("ĐIỂM DANH LẦN " + (new SubjectStudentHandler(AttendanceStudent.this).getMaxAttendanceCount(subject.getsId()) + 1));
    }

    private void initViews() {
        findViewById(R.id.topbar_icon_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RecyclerView rvStudentList = findViewById(R.id.attendance_student_list);
        rvStudentList.setHasFixedSize(true);
        rvStudentList.setLayoutManager(new LinearLayoutManager(this));
        subjectStudents = new ArrayList<>();
        mAdapter = new AttendanceAdapter(subject, subjectStudents);
        rvStudentList.setAdapter(mAdapter);
        loadDatabase();

        findViewById(R.id.attendance_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent capture = new Intent(AttendanceStudent.this, CaptureActivity.class);
                startActivityForResult(capture, REQUEST_QR_SCANNER_CODE);
            }
        });

        findViewById(R.id.attendance_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update have
                for (int i = 0; i < subjectStudents.size(); i++) {
                    if (subjectStudents.get(i).isAttendance())
                        subjectStudents.get(i).setSsHave(subjectStudents.get(i).getSsHave() + 1);
                    if (new SubjectStudentHandler(AttendanceStudent.this).update(subjectStudents.get(i)) > 0) {
                        subjectStudents.remove(subjectStudents.get(i));
                    }
                }
                mAdapter.notifyDataSetChanged();
                if (subjectStudents.size() <= 0) {
                    Toasty.success(AttendanceStudent.this, "Lưu điểm danh lần này thành công.", Toasty.LENGTH_SHORT, true).show();
                    onBackPressed();
                } else {
                    Toasty.warning(AttendanceStudent.this, "Lưu điểm danh chưa hoàn tất. Vui lòng kiểm tra lại.", Toasty.LENGTH_SHORT, true).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_QR_SCANNER_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String StudentCode = "";
                if (data != null) {
                    StudentCode = data.getStringExtra(AttendanceStudent.DATA_STUDENT_CODE);
                    Student student = new StudentHandler(AttendanceStudent.this).get(StudentCode);
                    student.ShowInfo(AttendanceStudent.this);
                    for (int i = 0; i < subjectStudents.size(); i++) {
                        if (subjectStudents.get(i).getStId() == student.getStId()) {
                            subjectStudents.get(i).setAttendance(true);
                            mAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            }
        }
    }
}
