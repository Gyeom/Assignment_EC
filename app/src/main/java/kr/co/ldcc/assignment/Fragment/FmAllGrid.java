package kr.co.ldcc.assignment.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.co.ldcc.assignment.Adapter.AllDataAdapter;
import kr.co.ldcc.assignment.R;

import static kr.co.ldcc.assignment.Activity.MainActivity.allDataAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FmAllGrid extends Fragment {

    public static FmAllGrid newInstance() {
        return new FmAllGrid();
    }
    public FmAllGrid() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fm_recycler_grid,container,false);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView =(RecyclerView) v.findViewById(R.id.rv_grid);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(mLayoutManager);

        // 리사이클러뷰에 VideoAdapter 객체 지정.
        if(allDataAdapter==null){
            allDataAdapter= new AllDataAdapter(new ArrayList<Object>());
        }
        recyclerView.setAdapter(allDataAdapter) ;
        return v;
    }
}
