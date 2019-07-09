package tech.waxen.was.Modal.Student;

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
import es.dmoral.toasty.Toasty;
import tech.waxen.was.R;
import tech.waxen.was.Utils;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private List<Student> students;

    public StudentAdapter(List<Student> students) {
        this.students = students;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentViewHolder holder, int position) {
        final Student student = students.get(position);
        holder.stName.setText(student.getStName());
        holder.stEmail.setText(student.getStEmail());
        Glide.with(holder.stAvatar.getContext()).load("https://www.gravatar.com/avatar/" + Utils.md5(student.getStEmail())).into(holder.stAvatar);
        holder.stItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student.ShowInfo(v.getContext());
            }
        });
        holder.stItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toasty.info(holder.stAvatar.getContext(), "Hiện tại chưa hỗ trợ chức năng này", Toasty.LENGTH_SHORT, true).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout stItem;
        CircleImageView stAvatar;
        TextView stName;
        TextView stEmail;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            stItem = itemView.findViewById(R.id.student_item);
            stAvatar = itemView.findViewById(R.id.student_avatar);
            stName = itemView.findViewById(R.id.student_name);
            stEmail = itemView.findViewById(R.id.student_email);
        }
    }
}
