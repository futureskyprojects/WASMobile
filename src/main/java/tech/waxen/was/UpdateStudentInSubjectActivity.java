package tech.waxen.was;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import tech.waxen.was.Modal.ConnectSubjectStudent.ConnectSubjectStudent;
import tech.waxen.was.Modal.ConnectSubjectStudent.DialogStudentSubjectAdapter;
import tech.waxen.was.Modal.ConnectSubjectStudent.SubjectStudentHandler;
import tech.waxen.was.Modal.Student.Student;
import tech.waxen.was.Modal.Student.StudentAdapter;
import tech.waxen.was.Modal.Student.StudentHandler;
import tech.waxen.was.Modal.Subject.Subject;
import tech.waxen.was.Modal.wClass.wClass;
import tech.waxen.was.Modal.wClass.wClassHandler;

public class UpdateStudentInSubjectActivity extends AppCompatActivity {
    Subject subject;
    StudentAdapter mAdapter;

    AlertDialog dialog;

    List<Student> students;
    List<ConnectSubjectStudent> subjectStudents;
    private TextView tvTitle;

    void loadData() {
        if (subjectStudents == null)
            subjectStudents = new ArrayList<>();
        if (students == null)
            students = new ArrayList<>();
        subjectStudents.clear();
        students.clear();

        subjectStudents.addAll(new SubjectStudentHandler(this).getAll(subject.getsId()));
        for (ConnectSubjectStudent subjectStudent : subjectStudents) {
            Student student = new StudentHandler(this).get(subjectStudent.getStId());
            if (student != null)
                students.add(student);
        }
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_update_student_in_subject_layout);
        subject = (Subject) getIntent().getSerializableExtra(Subject.TAG);
        if (subject == null) {
            Toasty.error(this, "Môn không hợp lệ.", Toasty.LENGTH_SHORT, true).show();
            finish();
        } else {
            initViews();
            tvTitle.setText(subject.getsName());
        }
    }

    private void initViews() {
        tvTitle = findViewById(R.id.subject_student_subject_name);
        findViewById(R.id.subject_student_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // For grid
        RecyclerView mListStudentInSubject = findViewById(R.id.student_in_subject_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mListStudentInSubject.setHasFixedSize(true);
        mListStudentInSubject.setLayoutManager(layoutManager);

        // InitStudent
        subjectStudents = new ArrayList<>();
        students = new ArrayList<>();
        mAdapter = new StudentAdapter(students);

        mListStudentInSubject.setAdapter(mAdapter);
        loadData();
        //
        findViewById(R.id.subject_student_add_new_student).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStudentInSubjectActivity.this);
                View view = LayoutInflater.from(UpdateStudentInSubjectActivity.this)
                        .inflate(R.layout.dialog_add_student_in_subject, null);
                builder.setView(view);
                builder.setCancelable(false);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                // Show view
                final EditText edtStudentCode = view.findViewById(R.id.student_in_subject_inp_code);
                view.findViewById(R.id.student_in_subject_add_dialog_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
                view.findViewById(R.id.student_in_subject_btn_add_new_green).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        String sCode = edtStudentCode.getText().toString();
                        if (sCode.isEmpty()) {
                            Toasty.warning(v.getContext(), "Mã sinh viên không thể trống", Toasty.LENGTH_SHORT, true).show();
                        } else {
                            final Student student = new StudentHandler(v.getContext()).get(sCode);
                            if (student != null) {
                                for (Student st : students) {
                                    if (st.getStId() == student.getStId()) {
                                        Toasty.warning(v.getContext(), "Sinh viên này đã tồn tại", Toasty.LENGTH_SHORT, true).show();
                                        return;
                                    }
                                }
                                AlertDialog.Builder bui = new AlertDialog.Builder(v.getContext())
                                        .setTitle("XÁC NHẬN THÊM")
                                        .setMessage("Đã tim thấy sinh viên tên [" + student.getStName() + "], thực sự muốn thêm sinh viên?")
                                        .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ConnectSubjectStudent subjectStudent = new ConnectSubjectStudent(subject.getsId(), student.getStId(), 0);
                                                if (new SubjectStudentHandler(v.getContext()).add(subjectStudent) > 0) {
                                                    Toasty.success(v.getContext(), "Thêm sinh viên [" + student.getStName() + "] vào lớp [" + subject.getsName() + "] thành công!", Toasty.LENGTH_SHORT, true).show();
                                                    dialog.cancel();
                                                    alertDialog.cancel();
                                                    loadData();
                                                } else {
                                                    Toasty.error(v.getContext(), "Thêm sinh viên [" + student.getStName() + "] vào lớp [" + subject.getsName() + "] chưa thành công!", Toasty.LENGTH_SHORT, true).show();
                                                }
                                            }
                                        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                                alertDialog.cancel();
                                            }
                                        });
                                bui.setCancelable(false);
                                bui.create();
                                bui.show();
                            } else {
                                Toasty.warning(v.getContext(), "Không thể tìm thấy sinh viên này.", Toasty.LENGTH_SHORT, true).show();
                            }
                        }
                    }
                });


            }
        });
        //
        findViewById(R.id.subject_student_add_new_class).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStudentInSubjectActivity.this);
                View view = LayoutInflater.from(UpdateStudentInSubjectActivity.this).inflate(R.layout.dialog_class_select_in_subject_student, null);
                builder.setView(view);
                builder.setCancelable(false);

                dialog = builder.create();
                dialog.show();
                ///
                view.findViewById(R.id.dialog_class_select_in_subject_student_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                ////
                RecyclerView rvListClassInDialog = view.findViewById(R.id.dialog_class_select_in_subject_student_list_class);
                rvListClassInDialog.setHasFixedSize(true);
                rvListClassInDialog.setLayoutManager(new LinearLayoutManager(UpdateStudentInSubjectActivity.this));

                List<wClass> classes = new wClassHandler(UpdateStudentInSubjectActivity.this).getAllClass();
                DialogStudentSubjectAdapter smallAdapter = new DialogStudentSubjectAdapter(classes);

                rvListClassInDialog.setAdapter(smallAdapter);
            }
        });


    }

    public void AddStudentByClass(final wClass wclass) {
        if (dialog != null && dialog.isShowing())
            dialog.cancel();
        if (wclass != null) {
            final List<Student> _students = new StudentHandler(UpdateStudentInSubjectActivity.this).getAll(wclass.getcId());
            // Xóa bỏ các sinh viên đã tồn tại trước đó
            for (int i = 0; i < _students.size(); i++) {
                for (int j = 0; j < students.size(); j++) {
                    if (_students.get(i).getStId() == students.get(j).getStId()) {
                        _students.remove(i);
                        break;
                    }
                }
            }
            if (_students.size() <= 0) {
                Toasty.warning(UpdateStudentInSubjectActivity.this, "Tất cả sinh viên của lớp [" + wclass.getcName() + "] đã học môn này rồi!", Toasty.LENGTH_SHORT, true).show();
                return;
            }
            // Xong thì hỏi
            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStudentInSubjectActivity.this);
            builder.setTitle("THÊM TẤT CẢ SINH VIÊN")
                    .setMessage("Thực sự thêm tất cả " + _students.size() + " sinh viên của lớp [" + wclass.getcName() + "] vào môn học [" + subject.getsName() + "] ?")
                    .setPositiveButton("Thêm hết", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int success = 0;
                            for (Student st : _students) {
                                ConnectSubjectStudent subjectStudent = new ConnectSubjectStudent(subject.getsId(), st.getStId(), 0);
                                if (new SubjectStudentHandler(UpdateStudentInSubjectActivity.this).add(subjectStudent) > 0) {
                                    success++;
                                }
                            }
                            Toasty.success(UpdateStudentInSubjectActivity.this, "Đã thêm thành công " + success + " sinh viên của lớp [" + wclass.getcName() + "] vào môn học [" + subject.getsName() + "]", Toasty.LENGTH_SHORT, true).show();
                            dialog.cancel();
                            if (success > 0) {
                                loadData();
                            }
                        }
                    })
                    .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builder.setCancelable(false);
            builder.show();
        } else {
            Toasty.warning(UpdateStudentInSubjectActivity.this, "Lỗi khi chọn lớp này", Toasty.LENGTH_SHORT, true).show();
        }
    }
}
