package kr.co.ldcc.assignment.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.co.ldcc.assignment.activity.MainActivity;
import kr.co.ldcc.assignment.adapter.AllDataAdapter;
import kr.co.ldcc.assignment.adapter.ImageAdapter;
import kr.co.ldcc.assignment.adapter.VideoAdapter;
import kr.co.ldcc.assignment.R;
import kr.co.ldcc.assignment.vo.ImageVo;
import kr.co.ldcc.assignment.vo.VideoVo;

/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 */
public class SubFragment extends androidx.fragment.app.Fragment {
    private VideoAdapter videoAdapter;
    private ImageAdapter imageAdapter;
    private AllDataAdapter allDataAdapter;
    public static SubFragment newInstance()
    {
        return new SubFragment();
    }
    public SubFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = null;
        RecyclerView recyclerView;
        LinearLayoutManager linearLayoutManager;
        GridLayoutManager gridLayoutManager;
        ArrayList<Object> allDataVos;

        switch (getTag()) {
            case "buttonVideoGrid":
                // Inflate the layout for this fragment
                v =inflater.inflate(R.layout.fragment_recycler_grid,container,false);
                Log.d("test",getTag());
                // 리사이클러뷰에 LinearLayoutManager 객체 지정.
                recyclerView =(RecyclerView) v.findViewById(R.id.recyclerViewGrid);
                gridLayoutManager = new GridLayoutManager(getActivity(),3);
                recyclerView.setLayoutManager(gridLayoutManager);
                ArrayList<VideoVo> videoVos = ((MainActivity)getActivity()).getVideoVos();
                videoAdapter = new VideoAdapter(videoVos,((MainActivity)getActivity()).getUser(),((MainActivity)getActivity()).getProfile());
                // 리사이클러뷰에 VideoAdapter 객체 지정.
                recyclerView.setAdapter(videoAdapter);
                break;
            case "buttonVideoLinear":
                v =inflater.inflate(R.layout.fragment_recycler_linear,container,false);

                // 리사이클러뷰에 LinearLayoutManager 객체 지정.
                recyclerView =(RecyclerView) v.findViewById(R.id.recyclerViewLinear) ;
                linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

                recyclerView.setLayoutManager(linearLayoutManager);
                videoVos = ((MainActivity)getActivity()).getVideoVos();
                videoAdapter = new VideoAdapter(videoVos,((MainActivity)getActivity()).getUser(),((MainActivity)getActivity()).getProfile());
                // 리사이클러뷰에 VideoAdapter 객체 지정.
                recyclerView.setAdapter(videoAdapter) ;
                break;
            case "buttonImageGrid":
                // Inflate the layout for this fragment
                v =inflater.inflate(R.layout.fragment_recycler_grid,container,false);

                // 리사이클러뷰에 LinearLayoutManager 객체 지정.
                recyclerView =(RecyclerView) v.findViewById(R.id.recyclerViewGrid);
                gridLayoutManager = new GridLayoutManager(getActivity(),3);
                recyclerView.setLayoutManager(gridLayoutManager);

                // 리사이클러뷰에 VideoAdapter 객체 지정.
                ArrayList<ImageVo> imageVos = ((MainActivity)getActivity()).getImageVos();
                imageAdapter = new ImageAdapter(imageVos,((MainActivity)getActivity()).getUser(),((MainActivity)getActivity()).getProfile());

                recyclerView.setAdapter(imageAdapter) ;
                break;
            case "buttonImageLinear":
                v =inflater.inflate(R.layout.fragment_recycler_linear,container,false);

                // 리사이클러뷰에 LinearLayoutManager 객체 지정.
                recyclerView =(RecyclerView) v.findViewById(R.id.recyclerViewLinear) ;
                linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager) ;

                imageVos = ((MainActivity)getActivity()).getImageVos();
                imageAdapter = new ImageAdapter(imageVos,((MainActivity)getActivity()).getUser(),((MainActivity)getActivity()).getProfile());
                // 리사이클러뷰에 mageAdapter 객체 지정.
                recyclerView.setAdapter(imageAdapter) ;
                break;
            case "buttonAllDataGrid":
                v =inflater.inflate(R.layout.fragment_recycler_grid,container,false);

                // 리사이클러뷰에 LinearLayoutManager 객체 지정.
                recyclerView =(RecyclerView) v.findViewById(R.id.recyclerViewGrid);
                gridLayoutManager = new GridLayoutManager(getActivity(),3);
                recyclerView.setLayoutManager(gridLayoutManager);

                // 리사이클러뷰에 VideoAdapter 객체 지정.
                allDataVos = ((MainActivity)getActivity()).getAllDataVos();
                allDataAdapter = new AllDataAdapter(allDataVos,((MainActivity)getActivity()).getUser(),((MainActivity)getActivity()).getProfile());
                recyclerView.setAdapter(allDataAdapter);
                break;
            case "buttonAllDataLinear":
                v =inflater.inflate(R.layout.fragment_recycler_linear,container,false);

                // 리사이클러뷰에 LinearLayoutManager 객체 지정.
                recyclerView =(RecyclerView) v.findViewById(R.id.recyclerViewLinear) ;
                linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

                recyclerView.setLayoutManager(linearLayoutManager) ;

                // 리사이클러뷰에 AllAdapter 객체 지정.
                allDataVos = ((MainActivity)getActivity()).getAllDataVos();
                allDataAdapter = new AllDataAdapter(allDataVos,((MainActivity)getActivity()).getUser(),((MainActivity)getActivity()).getProfile());
                recyclerView.setAdapter(allDataAdapter) ;
                break;
        }
        return v;
    }
}
