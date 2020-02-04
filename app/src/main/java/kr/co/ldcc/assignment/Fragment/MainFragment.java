package kr.co.ldcc.assignment.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import kr.co.ldcc.assignment.R;

public class MainFragment extends Fragment {

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private Button btn_vd_grid, btn_vd_linear;
    private Button btn_img_grid, btn_img_linear;
    private Button btn_all_grid, btn_all_linear;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fm_main,container,false);
        btn_vd_grid = (Button)v.findViewById(R.id.btn_vd_grid);
        btn_vd_linear = (Button)v.findViewById(R.id.btn_vd_linear);
        btn_img_grid = (Button)v.findViewById(R.id.btn_img_grid);
        btn_img_linear = (Button)v.findViewById(R.id.btn_img_linear);
        btn_all_grid = (Button)v.findViewById(R.id.btn_all_grid);
        btn_all_linear = (Button)v.findViewById(R.id.btn_all_linear);
        showLayout(btn_vd_linear);
        showLayout(btn_img_linear);
        showLayout(btn_all_linear);

        //Button Listener
        btn_vd_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLayout(v);
            }
        });
        btn_vd_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLayout(v);
            }
        });
        btn_img_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLayout(v);
            }
        });
        btn_img_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLayout(v);
            }
        });
        btn_all_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLayout(v);
            }
        });
        btn_all_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLayout(v);
            }
        });
        return v;
    }

    public void showLayout(View view){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction tran =  fragmentManager.beginTransaction();

        switch (view.getId()) {
            case R.id.btn_vd_grid:
                tran.replace(R.id.container_video, SubFragment.newInstance(),"btn_vd_grid");
                break;
            case R.id.btn_vd_linear:
                tran.replace(R.id.container_video, SubFragment.newInstance(),"btn_vd_linear");
                break;
            case R.id.btn_img_linear:
                tran.replace(R.id.container_image, SubFragment.newInstance(),"btn_img_linear");
                break;
            case R.id.btn_img_grid:
                tran.replace(R.id.container_image, SubFragment.newInstance(),"btn_img_grid");
                break;
            case R.id.btn_all_linear:
                tran.replace(R.id.container_alldata, SubFragment.newInstance(),"btn_all_linear");
                break;
            case R.id.btn_all_grid:
                tran.replace(R.id.container_alldata, SubFragment.newInstance(),"btn_all_grid");
                break;
        }
        tran.commit();
    }
}