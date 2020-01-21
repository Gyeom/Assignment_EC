package kr.co.ldcc.assignment.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.co.ldcc.assignment.R;
import kr.co.ldcc.assignment.Adapter.VideoAdapter;
import kr.co.ldcc.assignment.Vo.VideoVo;

import static kr.co.ldcc.assignment.Activity.MainActivity.videoAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FmVdLinear extends Fragment {

    public static FmVdLinear newInstance()
    {
        return new FmVdLinear();
    }
    public FmVdLinear() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fm_vd_linear,container,false);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView =(RecyclerView) v.findViewById(R.id.recycler_vd_linear) ;
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(mLayoutManager) ;

        // 리사이클러뷰에 VideoAdapter 객체 지정.
        if(videoAdapter==null){
            videoAdapter= new VideoAdapter(new ArrayList<VideoVo>()) ;
        }
        recyclerView.setAdapter(videoAdapter) ;
        return v;
    }

}
