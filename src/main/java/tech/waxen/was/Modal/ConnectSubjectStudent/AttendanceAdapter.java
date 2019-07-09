package tech.waxen.was.Modal.ConnectSubjectStudent;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.waxen.was.Modal.Student.Student;
import tech.waxen.was.Modal.Student.StudentHandler;
import tech.waxen.was.Modal.Subject.Subject;
import tech.waxen.was.R;
import tech.waxen.was.Utils;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
    private Subject subject;
    private List<ConnectSubjectStudent> subjectStudents;

    public AttendanceAdapter(Subject subject, List<ConnectSubjectStudent> subjectStudents) {
        this.subject = subject;
        this.subjectStudents = subjectStudents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_in_attendance, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ConnectSubjectStudent subjectStudent = subjectStudents.get(position);
        final Student student = new StudentHandler(holder.tvStatusOfStudent.getContext()).get(subjectStudent.getStId());
        Glide.with(holder.ciStudentAvatar.getContext()).load("https://www.gravatar.com/avatar/" + Utils.md5(student.getStEmail())).into(holder.ciStudentAvatar);
        holder.tvStudentName.setText(student.getStName());
        float unHaveRate = ((float)subjectStudent.ssHave/Float.parseFloat(subject.getsLength()));
        holder.tvStatusOfStudent.setText(String.format("Tỉ lệ hoàn thành %.2f %% (Học %d/%s tiết)", unHaveRate, subjectStudent.ssHave, subject.getsLength()));

        if (subjectStudent.isAttendance)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.rlAttendanceItem.setBackground(holder.rlAttendanceItem.getContext().getDrawable(R.drawable.green_bottom_line));
            } else {
                holder.rlAttendanceItem.setBackgroundResource(R.drawable.green_bottom_line);
            }
        }
        holder.rlAttendanceItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                student.ShowInfo(v.getContext());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectStudents.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlAttendanceItem;
        CircleImageView ciStudentAvatar;
        TextView tvStudentName;
        TextView tvStatusOfStudent;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlAttendanceItem = itemView.findViewById(R.id.attendance_item);
            ciStudentAvatar = itemView.findViewById(R.id.student_in_attendance_avatar);
            tvStatusOfStudent = itemView.findViewById(R.id.attendance_stauts);
            tvStudentName = itemView.findViewById(R.id.student_in_attendance_name);
        }
    }
}
