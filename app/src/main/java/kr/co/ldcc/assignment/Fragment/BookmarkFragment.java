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
import kr.co.ldcc.assignment.Adapter.BookmarkAdapter;
import kr.co.ldcc.assignment.DB.AppDatabase;
import kr.co.ldcc.assignment.Dao.BookmarkDao;
import kr.co.ldcc.assignment.R;
import kr.co.ldcc.assignment.Vo.BookmarkVo;


public class BookmarkFragment extends Fragment {

    private AppDatabase db=null;
    private RecyclerView recyclerView;
    private ArrayList<BookmarkVo> bookmarkList;
    private BookmarkAdapter bookMarkAdapter;
    public static BookmarkFragment newInstance() {
        return new BookmarkFragment();
    }
    public BookmarkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_recycler_grid,container,false);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView =(RecyclerView) v.findViewById(R.id.recyclerViewGrid);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(mLayoutManager);
        //디비생성
        db = AppDatabase.getInstance(getContext());
        new SelectAllBookmark(db.bookmarkDao()).execute();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        new SelectAllBookmark(db.bookmarkDao()).execute();
    }

    public class SelectAllBookmark extends  AsyncTask<Void, Void, List<BookmarkVo>> {
        private BookmarkDao bookmarkDao;

        public SelectAllBookmark(BookmarkDao bookmarkDao){ this.bookmarkDao = bookmarkDao; }

        @Override
        protected List<BookmarkVo> doInBackground(Void... voids) {
            return bookmarkDao.getAll(((MainActivity)getActivity()).getUser());
        }

        @Override
        protected void onPostExecute(List<BookmarkVo> results) {
            // 리사이클러뷰에 VideoAdapter 객체 지정.
            bookmarkList = new ArrayList<BookmarkVo>(results);
            bookMarkAdapter = new BookmarkAdapter(bookmarkList,((MainActivity)getActivity()).getUser(),((MainActivity)getActivity()).getProfile());
            recyclerView.setAdapter(bookMarkAdapter);
        }
    }
}
