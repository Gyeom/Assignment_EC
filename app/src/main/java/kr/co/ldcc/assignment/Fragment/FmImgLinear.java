package kr.co.ldcc.assignment.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.co.ldcc.assignment.Adapter.ImageAdapter;
import kr.co.ldcc.assignment.Vo.ImageVo;
import kr.co.ldcc.assignment.R;

import static kr.co.ldcc.assignment.Activity.MainActivity.imageAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FmImgLinear extends Fragment {

    public static FmImgLinear newInstance()
    {
        return new FmImgLinear();
    }
    public FmImgLinear() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fm_img_linear,container,false);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView =(RecyclerView) v.findViewById(R.id.recycler_img_linear) ;
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(mLayoutManager) ;

        // 리사이클러뷰에 VideoAdapter 객체 지정.
        if(imageAdapter==null){
            imageAdapter= new ImageAdapter(new ArrayList<ImageVo>()) ;
        }
        recyclerView.setAdapter(imageAdapter) ;
        return v;
    }

}
