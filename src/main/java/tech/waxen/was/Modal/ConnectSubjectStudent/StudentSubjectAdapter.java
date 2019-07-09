package tech.waxen.was.Modal.ConnectSubjectStudent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.waxen.was.R;

public class StudentSubjectAdapter extends RecyclerView.Adapter<StudentSubjectAdapter.ViewHolder> {
    List<ConnectSubjectStudent> subjectStudents;

    public StudentSubjectAdapter(List<ConnectSubjectStudent> subjectStudents) {
        this.subjectStudents = subjectStudents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_in_subject, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return subjectStudents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
