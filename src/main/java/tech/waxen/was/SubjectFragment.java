package tech.waxen.was;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import tech.waxen.was.Modal.Subject.Subject;
import tech.waxen.was.Modal.Subject.SubjectAdapter;
import tech.waxen.was.Modal.Subject.SubjectHandler;

public class SubjectFragment extends Fragment {
    private List<Subject> subjects;
    private SubjectAdapter mAdapter;


    private void loadAllSubject(Context mContext) {
        if (subjects == null)
            subjects = new ArrayList<>();
        subjects.clear();
        subjects.addAll(new SubjectHandler(mContext).getAll());
//        Toast.makeText(mContext, "Có " + subjects.size(), Toast.LENGTH_SHORT).show();
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }
    public SubjectFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_subject_layout, container, false);
        // for button add
        v.findViewById(R.id.add_new_subject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khởi tạo và hiển thị hộp thoại thêm
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View vz = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_add_subject, null);
                builder.setView(vz);
                builder.setCancelable(false);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                // Xử lý các view và sự kiện
                final EditText edtSubjectName = vz.findViewById(R.id.subject_inp_name);
                final EditText edtSubjectLength = vz.findViewById(R.id.subject_inp_length);
                final EditText edtSubjectStartTime = vz.findViewById(R.id.subject_inp_start_time);
                final EditText edtSubjectEndTime = vz.findViewById(R.id.subject_inp_end_time);
                final EditText edtSubjectDescription = vz.findViewById(R.id.subject_inp_description);

                vz.findViewById(R.id.subject_add_dialog_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });

                vz.findViewById(R.id.subject_btn_add_new).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String sName = edtSubjectName.getText().toString().trim();
                        String sLength = edtSubjectLength.getText().toString().trim();
                        String sStartTime = edtSubjectStartTime.getText().toString().trim();
                        String sEndTime = edtSubjectEndTime.getText().toString().trim();
                        String sDescription = edtSubjectDescription.getText().toString().trim();

                        if (sName.isEmpty() || sLength.isEmpty() || sStartTime.isEmpty() || sEndTime.isEmpty()) {
                            Toasty.warning(v.getContext(), "Vui lòng nhập đầy đủ thông tin.", Toasty.LENGTH_SHORT, true).show();
                        }
                        else if (!Utils.validateDDMMYYYY(sStartTime)) {
                            Toasty.warning(v.getContext(), "Thời gian bắt đầu phải có định dạng dd/mm/20yy", Toasty.LENGTH_SHORT, true).show();
                        }else if (!Utils.validateDDMMYYYY(sEndTime)) {
                            Toasty.warning(v.getContext(), "Thời gian kết thúc phải có định dạng dd/mm/20yy", Toasty.LENGTH_SHORT, true).show();
                        } else if (Utils.CompareDDMMYYYY(sEndTime, sStartTime) <= 0) {
                            Toasty.warning(v.getContext(), "Thời gian kết thúc không hợp lý", Toasty.LENGTH_SHORT, true).show();
                        } else {
                            try {
                                int subjectLength = Integer.parseInt(sLength);
                                if (subjectLength <= 0)
                                {
                                    Toasty.warning(v.getContext(),"Thời lượng tiết của môn không thể nhỏ hơn hoặc bằng 0.", Toasty.LENGTH_SHORT, true).show();
                                } else {
                                    Subject subject = new Subject(sName, sLength, sDescription, sStartTime, sEndTime);
                                    if (new SubjectHandler(v.getContext()).add(subject) > 0) {
                                        Toasty.success(v.getContext(), "Thêm môn [" + sName + "] thành công.", Toasty.LENGTH_SHORT, true).show();
                                        alertDialog.cancel();
                                        loadAllSubject(v.getContext());
                                    }
                                }
                            }catch (Exception e) {
                                Toasty.warning(v.getContext(),"Thời lượng tiết không hợp lệ.", Toasty.LENGTH_SHORT, true).show();
                            }
                            //
                        }
                    }
                });

            }
        });
        // For list
        RecyclerView mListSubject = v.findViewById(R.id.list_subject);
        mListSubject.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mListSubject.setLayoutManager(layoutManager);

        subjects = new ArrayList<>();
        mAdapter = new SubjectAdapter(subjects);
        mListSubject.setAdapter(mAdapter);

        loadAllSubject(v.getContext());

        return v;
    }

}
