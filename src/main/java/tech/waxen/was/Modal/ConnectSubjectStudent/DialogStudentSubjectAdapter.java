package tech.waxen.was.Modal.ConnectSubjectStudent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.waxen.was.Modal.wClass.wClass;
import tech.waxen.was.R;
import tech.waxen.was.UpdateStudentInSubjectActivity;

public class DialogStudentSubjectAdapter extends RecyclerView.Adapter<DialogStudentSubjectAdapter.ViewHolder> {
    List<wClass> classes;

    public DialogStudentSubjectAdapter(List<wClass> classes) {
        this.classes = classes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_class_select_in_subject_student, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final wClass wclass = classes.get(position);
        holder.tvClassName.setText(wclass.getcName());
        holder.tvClassName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.tvClassName.getContext() instanceof UpdateStudentInSubjectActivity) {
                    ((UpdateStudentInSubjectActivity)holder.tvClassName.getContext()).AddStudentByClass(wclass);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvClassName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClassName = itemView.findViewById(R.id.dialog_class_select_in_subject_student_class_name);
        }
    }
}
