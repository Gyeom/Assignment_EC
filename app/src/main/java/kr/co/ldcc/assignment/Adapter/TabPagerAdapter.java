package kr.co.ldcc.assignment.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import kr.co.ldcc.assignment.Fragment.BookmarkFragment;
import kr.co.ldcc.assignment.Fragment.MainFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    //Count number of tabs
    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        //Returning the current tabs
        switch (position){
            case 0:
                MainFragment fragmentForMain = MainFragment.newInstance();
                return fragmentForMain;
            case 1:
                BookmarkFragment fragmentForBookmark = BookmarkFragment.newInstance();
                return fragmentForBookmark;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}