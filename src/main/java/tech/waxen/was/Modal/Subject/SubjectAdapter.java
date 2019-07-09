package tech.waxen.was.Modal.Subject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import es.dmoral.toasty.Toasty;
import tech.waxen.was.AttendanceStudent;
import tech.waxen.was.Modal.ConnectSubjectStudent.SubjectStudentHandler;
import tech.waxen.was.R;
import tech.waxen.was.UpdateStudentInSubjectActivity;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectHolder> {
    List<Subject> subjects;

    public SubjectAdapter(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @NonNull
    @Override
    public SubjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject, parent, false);
        return new SubjectHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final SubjectHolder holder, int position) {
        final Subject subject = subjects.get(position);

        holder.tvSubjectName.setText(subject.getsName());
        holder.tvSubjectDescription.setText(subject.getsDescription());
        holder.tvStudentCount.setText(new SubjectStudentHandler(holder.btnDiemDanhNgay.getContext()).getAll(subject.getsId()).size() + "");
        holder.tvSubjectLength.setText(subject.getsLength());
        holder.tvCreateAt.setText(subject.getsCreateAt());
        holder.tvEndAt.setText(subject.getsEndAt());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(holder.btnDiemDanhNgay.getContext(), "Hiện tại chưa hỗ trợ chức năng này", Toasty.LENGTH_SHORT, true).show();
            }
        });

        holder.btnUpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateSudentInSubject = new Intent(holder.btnUpdateStudent.getContext(), UpdateStudentInSubjectActivity.class);
                updateSudentInSubject.putExtra(Subject.TAG, subject);
                holder.btnDiemDanhNgay.getContext().startActivity(updateSudentInSubject);
            }
        });

        holder.btnDiemDanhNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent attendenceStudent = new Intent(holder.btnDiemDanhNgay.getContext(), AttendanceStudent.class);
                attendenceStudent.putExtra(Subject.TAG, subject);
                holder.btnDiemDanhNgay.getContext().startActivity(attendenceStudent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public static class SubjectHolder extends RecyclerView.ViewHolder {
        TextView tvSubjectName;
        TextView tvSubjectDescription;
        TextView tvStudentCount;
        TextView tvSubjectLength;
        TextView tvCreateAt;
        TextView tvEndAt;

        Button btnDiemDanhNgay;
        Button btnUpdateStudent;
        Button btnEdit;


        public SubjectHolder(@NonNull View itemView) {
            super(itemView);
            tvSubjectName = itemView.findViewById(R.id.subject_name);
            tvSubjectDescription = itemView.findViewById(R.id.subject_description);
            tvStudentCount = itemView.findViewById(R.id.subject_student_count);
            tvSubjectLength = itemView.findViewById(R.id.subject_length);
            tvCreateAt = itemView.findViewById(R.id.subject_start_time);
            tvEndAt = itemView.findViewById(R.id.subject_end_time);

            btnDiemDanhNgay = itemView.findViewById(R.id.diem_danh_ngay);
            btnUpdateStudent = itemView.findViewById(R.id.subject_update_student);
            btnEdit = itemView.findViewById(R.id.subject_edit);
        }
    }
}
