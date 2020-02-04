package kr.co.ldcc.assignment.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.co.ldcc.assignment.Activity.MainActivity;
import kr.co.ldcc.assignment.Adapter.BmarkAdapter;
import kr.co.ldcc.assignment.DB.AppDatabase;
import kr.co.ldcc.assignment.Dao.BmarkDao;
import kr.co.ldcc.assignment.R;
import kr.co.ldcc.assignment.Vo.BmarkVo;


public class BmarkFragment extends Fragment {

    private AppDatabase db=null;
    private RecyclerView recyclerView;
    private ArrayList<BmarkVo> bmarkList;
    private BmarkAdapter bmarkAdapter;
    public static BmarkFragment newInstance() {
        return new BmarkFragment();
    }
    public BmarkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_recycler_grid,container,false);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView =(RecyclerView) v.findViewById(R.id.rv_grid);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(mLayoutManager);
        //디비생성
        db = AppDatabase.getInstance(getContext());
        new SelectAllBmark(db.bmarkDao()).execute();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        new SelectAllBmark(db.bmarkDao()).execute();
    }

    public class SelectAllBmark extends  AsyncTask<Void, Void, List<BmarkVo>> {
        private BmarkDao bmarkDao;

        public SelectAllBmark(BmarkDao bmarkDao){ this.bmarkDao = bmarkDao; }

        @Override
        protected List<BmarkVo> doInBackground(Void... voids) {
            return bmarkDao.getAll(((MainActivity)getActivity()).getUser());
        }

        @Override
        protected void onPostExecute(List<BmarkVo> results) {
            // 리사이클러뷰에 VideoAdapter 객체 지정.
            bmarkList = new ArrayList<BmarkVo>(results);
            bmarkAdapter= new BmarkAdapter(bmarkList,((MainActivity)getActivity()).getUser(),((MainActivity)getActivity()).getProfile());
            recyclerView.setAdapter(bmarkAdapter);
        }
    }
}
