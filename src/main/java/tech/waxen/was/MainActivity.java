package tech.waxen.was;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity{
    private SubjectFragment mSubjectFragment;
    private ClassFragment mClassFragment;

    public TextView tvToolbarTitle;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_class:
                    tvToolbarTitle.setText("Danh sách lớp");
                    replaceFragment(mClassFragment);
                    return true;
                case R.id.menu_subjects:
                    tvToolbarTitle.setText("Môn giảng dạy");
                    replaceFragment(mSubjectFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main_layout);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        tvToolbarTitle = findViewById(R.id.toolbar_title);
        initFragments();
        navView.setSelectedItemId(R.id.menu_subjects);

        findViewById(R.id.topbar_icon_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_software_info, null);
                builder.setView(view);
                builder.setCancelable(false);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                ///
                view.findViewById(R.id.close_close_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
            }
        });

        findViewById(R.id.topbar_icon_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(MainActivity.this, "Chức năng thiết lập mật khẩu chưa khả dụng. Hãy đợi phiên bản sau.", Toasty.LENGTH_SHORT, true).show();
            }
        });
    }

    private void initFragments() {
        mSubjectFragment = new SubjectFragment();
        mClassFragment = new ClassFragment();
    }

    public void replaceFragment(Fragment destFragment)
    {
        // First get FragmentManager object.
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the layout holder with the required Fragment object.
        fragmentTransaction.replace(R.id.main_frame, destFragment);

        // Commit the Fragment replace action.
        fragmentTransaction.commit();
    }
}
