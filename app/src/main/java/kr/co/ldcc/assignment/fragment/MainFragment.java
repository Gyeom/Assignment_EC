package kr.co.ldcc.assignment.fragment;

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

    private Button buttonVideoGrid, buttonVideoLinear;
    private Button buttonImageGrid, buttonImageLinear;
    private Button buttonAllDataGrid, buttonAllDataLinear;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main,container,false);
        buttonVideoGrid = (Button)v.findViewById(R.id.buttonVideoGrid);
        buttonVideoLinear = (Button)v.findViewById(R.id.buttonVideoLinear);
        buttonImageGrid = (Button)v.findViewById(R.id.buttonImageGrid);
        buttonImageLinear = (Button)v.findViewById(R.id.buttonImageLinear);
        buttonAllDataGrid = (Button)v.findViewById(R.id.buttonAllDataGrid);
        buttonAllDataLinear = (Button)v.findViewById(R.id.buttonAllDataLinear);
        showLayout(buttonVideoLinear);
        showLayout(buttonImageLinear);
        showLayout(buttonAllDataLinear);

        //Button Listener
        buttonVideoLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLayout(v);
            }
        });
        buttonVideoGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLayout(v);
            }
        });
        buttonImageLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLayout(v);
            }
        });
        buttonImageGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLayout(v);
            }
        });
        buttonAllDataLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLayout(v);
            }
        });
        buttonAllDataGrid.setOnClickListener(new View.OnClickListener() {
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
            case R.id.buttonVideoGrid:
                tran.replace(R.id.containerVideo, SubFragment.newInstance(),"buttonVideoGrid");
                break;
            case R.id.buttonVideoLinear:
                tran.replace(R.id.containerVideo, SubFragment.newInstance(),"buttonVideoLinear");
                break;
            case R.id.buttonImageLinear:
                tran.replace(R.id.containerImage, SubFragment.newInstance(),"buttonImageLinear");
                break;
            case R.id.buttonImageGrid:
                tran.replace(R.id.containerImage, SubFragment.newInstance(),"buttonImageGrid");
                break;
            case R.id.buttonAllDataLinear:
                tran.replace(R.id.containerAllData, SubFragment.newInstance(),"buttonAllDataLinear");
                break;
            case R.id.buttonAllDataGrid:
                tran.replace(R.id.containerAllData, SubFragment.newInstance(),"buttonAllDataGrid");
                break;
        }
        tran.commit();
    }
}