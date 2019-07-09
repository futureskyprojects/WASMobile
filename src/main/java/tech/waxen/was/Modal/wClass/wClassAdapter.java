package tech.waxen.was.Modal.wClass;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.dmoral.toasty.Toasty;
import tech.waxen.was.Modal.Student.StudentHandler;
import tech.waxen.was.R;

public class wClassAdapter extends RecyclerView.Adapter<wClassAdapter.wClassViewHolder> {
    private List<wClass> wClasses;

    public wClassAdapter(List<wClass> wClasses) {
        this.wClasses = wClasses;
    }

    @NonNull
    @Override
    public wClassAdapter.wClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false);
        return new wClassViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final wClassAdapter.wClassViewHolder holder, int position) {
        final wClass wclass = wClasses.get(position);
        holder.tvStudentCount.setText(new StudentHandler(holder.ivBtnDelete.getContext()).getAll(wclass.getcId()).size() + "");
        holder.tvClassName.setText(wclass.cName);
        holder.tvSchoolYear.setText(wclass.cSchoolYear);
        holder.rlClassItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mStudentActivity = new Intent(holder.tvClassName.getContext(), tech.waxen.was.StudentActivity.class);
                mStudentActivity.putExtra(wClass.TAG, wclass);
                holder.tvClassName.getContext().startActivity(mStudentActivity);
            }
        });

        holder.ivBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.ivBtnDelete.getContext());
                builder.setTitle("XÓA?")
                        .setMessage("Xóa lớp [" + wclass.getcName() + "]?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (new wClassHandler(holder.ivBtnDelete.getContext()).delete(wclass.getcId()) > 0 && new StudentHandler(holder.ivBtnDelete.getContext()).deleteByClass(wclass.getcId()) > 0) {
                                    Toasty.success(holder.ivBtnDelete.getContext(), "Xóa lớp [" + wclass.getcName() + "] thành công.", Toasty.LENGTH_SHORT, true).show();
                                } else {
                                    Toasty.error(holder.ivBtnDelete.getContext(), "Xóa lớp [" + wclass.getcName() + "] không thành công.", Toasty.LENGTH_SHORT, true).show();
                                }
                            }
                        }).setNegativeButton("Hủy", null);
                builder.setCancelable(false);
                builder.create();
                builder.show();
            }
        });

        holder.ivBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(holder.ivBtnEdit.getContext(), "Hiện tại chưa hỗ trợ chức năng này", Toasty.LENGTH_SHORT, true).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return wClasses.size();
    }

    public static class wClassViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlClassItem;
        TextView tvStudentCount;
        TextView tvClassName;
        TextView tvSchoolYear;
        ImageView ivBtnDelete;
        ImageView ivBtnEdit;

        public wClassViewHolder(@NonNull View itemView) {
            super(itemView);
            rlClassItem = itemView.findViewById(R.id.class_item);
            tvStudentCount = itemView.findViewById(R.id.class_student_count);
            tvClassName = itemView.findViewById(R.id.class_name);
            tvSchoolYear = itemView.findViewById(R.id.class_schoolyear);
            ivBtnDelete = itemView.findViewById(R.id.class_delete);
            ivBtnEdit = itemView.findViewById(R.id.class_edit);
        }
    }

}
