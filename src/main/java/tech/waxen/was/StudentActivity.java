package tech.waxen.was;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import tech.waxen.was.Modal.Student.Student;
import tech.waxen.was.Modal.Student.StudentAdapter;
import tech.waxen.was.Modal.Student.StudentHandler;
import tech.waxen.was.Modal.wClass.wClass;

public class StudentActivity extends AppCompatActivity {
    List<Student> students;
    private wClass wclass;
    private TextView tvClassName;
    private ImageView ivBtnBack;
    private ImageView ivBtnAddStudent;
    private RecyclerView rvStudentList;
    private StudentAdapter mAdapter;

    void loadAllStudent() {
        if (students == null)
            students = new ArrayList<>();
        students.clear();
        students.addAll(new StudentHandler(this).getAll(wclass.getcId()));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_student_layout);
        wclass = (wClass) getIntent().getSerializableExtra(wClass.TAG);
        if (wclass == null) {
            Toasty.error(this, "Không lấy được thông tin lớp.", Toasty.LENGTH_SHORT, true).show();
            finish();
        }

        initViews();
        initEvents();
        tvClassName.setText(wclass.getcName());
        initRecycleviews();

    }

    private void initRecycleviews() {
        rvStudentList.setHasFixedSize(true);
        rvStudentList.setLayoutManager(new LinearLayoutManager(this));
        students = new ArrayList<>();
        mAdapter = new StudentAdapter(students);
        rvStudentList.setAdapter(mAdapter);
        loadAllStudent();
    }

    private void initEvents() {
        ivBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ivBtnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder addStudent = new AlertDialog.Builder(StudentActivity.this);
                addStudent.setCancelable(false);
                View mView = LayoutInflater.from(StudentActivity.this).inflate(R.layout.dialog_add_student, null);
                addStudent.setView(mView);

                final AlertDialog dialog = addStudent.create();
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
                // Thêm các sự kiện xử lý ở đây
                mView.findViewById(R.id.student_add_dialog_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                final EditText stCode = mView.findViewById(R.id.student_inp_code);
                final EditText stName = mView.findViewById(R.id.student_inp_name);
                final EditText stEmail = mView.findViewById(R.id.student_inp_email);

                mView.findViewById(R.id.student_btn_add_new_green).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String sStudentCode = stCode.getText().toString().trim();
                        String sStudentEmail = stEmail.getText().toString().trim();
                        String sStudentName = stName.getText().toString().trim();

                        if (sStudentCode.isEmpty() || sStudentName.isEmpty()) {
                            Toasty.warning(StudentActivity.this, "Vui lòng nhập đầy đủ thông tin", Toasty.LENGTH_SHORT, true).show();
                        } else if (sStudentEmail.length() > 0 && !Utils.validate(sStudentEmail)) {
                            Toasty.warning(StudentActivity.this, "Địa chỉ email không hợp lệ.", Toasty.LENGTH_SHORT, true).show();
                        } else if (sStudentName.split(" ").length < 2) {
                            Toasty.warning(StudentActivity.this, "Tên phải có cả họ và tên.", Toasty.LENGTH_SHORT, true).show();
                        } else {
                            Student student = new Student(wclass.getcId(), sStudentCode, sStudentName, sStudentEmail);
                            if (new StudentHandler(StudentActivity.this).add(student) > 0) {
                                Toasty.success(StudentActivity.this, "Thêm sinh viên [" + student.getStName() + "] thành công.", Toasty.LENGTH_SHORT, true).show();
                                dialog.cancel();
                                loadAllStudent();
                            } else {
                                Toasty.error(StudentActivity.this, "Thêm sinh viên [" + student.getStName() + "] không thành công.", Toasty.LENGTH_SHORT, true).show();
                            }
                        }
                    }
                });
            }
        });
    }

    private void initViews() {
        tvClassName = findViewById(R.id.student_class_name);
        ivBtnAddStudent = findViewById(R.id.student_add_new);
        ivBtnBack = findViewById(R.id.student_back);
        rvStudentList = findViewById(R.id.student_list);
    }
}
