package tech.waxen.was;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import tech.waxen.was.Modal.wClass.wClass;
import tech.waxen.was.Modal.wClass.wClassAdapter;
import tech.waxen.was.Modal.wClass.wClassHandler;


public class ClassFragment extends Fragment {
    private wClassAdapter mAdapter;
    private List<wClass> wClasses = new ArrayList<>();

    public ClassFragment() {
    }

    private void loadClass(Context mContext) {
        if (wClasses == null)
            wClasses = new ArrayList<>();
        wClasses.clear();
        wClasses.addAll(new wClassHandler(mContext).getAllClass());
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_class_layout, container, false);

        //
        RecyclerView rvClassList = v.findViewById(R.id.list_class);
        rvClassList.setHasFixedSize(true);
        rvClassList.setLayoutManager(new LinearLayoutManager(v.getContext()));
        mAdapter = new wClassAdapter(wClasses);
        rvClassList.setAdapter(mAdapter);
        loadClass(v.getContext());

        FloatingActionButton mFloatingActionButton = v.findViewById(R.id.add_new_class);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddClassDialog(v.getContext());
            }
        });
        return v;
    }

    private void showAddClassDialog(Context mContext) {
        // Create and show
        AlertDialog.Builder mAddClass = new AlertDialog.Builder(mContext);
        @SuppressLint("InflateParams") View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_class, null);
        mAddClass.setView(v);

        final AlertDialog dialog = mAddClass.create();
        dialog.setCancelable(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
        // Set up event
        v.findViewById(R.id.add_class_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        final EditText edtClassName = v.findViewById(R.id.inp_class_name);
        final EditText edtShoolYear = v.findViewById(R.id.inp_school_year);
        v.findViewById(R.id.btn_add_class).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtClassName.getText().length() <= 0 || edtShoolYear.getText().length() <= 0) {
                    Toasty.warning(v.getContext(), "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT, true).show();
                } else if (!edtShoolYear.getText().toString().matches("20\\d{2}-20\\d{2}")) {
                    Toasty.warning(v.getContext(), "Niên khóa phải là 20xx-20xx", Toast.LENGTH_SHORT, true).show();
                } else {
                    try {
                        String[] temp = edtShoolYear.getText().toString().split("-");
                        if (Integer.parseInt(temp[1]) < Integer.parseInt(temp[0])) {
                            Toasty.warning(v.getContext(), "Năm kết thúc không thể bé hơn năm bắt đầu.", Toasty.LENGTH_SHORT, true).show();
                        } else {
                            // Xử lý thêm lớp ở đây
                            wClass wclass = new wClass(edtClassName.getText().toString(), edtShoolYear.getText().toString());
                            try {
                                if (new wClassHandler(v.getContext()).addClass(wclass) > 0) { // Thêm class mới
                                    Toasty.success(v.getContext(), "Thêm lớp [" + edtClassName.getText() + "] thành công!", Toasty.LENGTH_SHORT, true).show();
                                    dialog.cancel();
                                    // Shau đó tiến hành update recycle view luôn
                                    loadClass(v.getContext());
                                }
                            } catch (Exception ex) {
                                Toasty.error(v.getContext(), "Thêm lớp [" + edtClassName.getText() + "] không thành công!", Toasty.LENGTH_SHORT, true).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toasty.warning(v.getContext(), "Niên khóa không hợp lệ, vui lòng nhập lại.", Toasty.LENGTH_SHORT, true).show();
                    }
                }
            }
        });
    }
}
