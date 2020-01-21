package kr.co.ldcc.assignment.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import kr.co.ldcc.assignment.Fragment.FmBookmark;
import kr.co.ldcc.assignment.Fragment.FmMain;

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
                FmMain fragmentForMain = new FmMain();
                return fragmentForMain;
            case 1:
                FmBookmark fragmentForBookmark = new FmBookmark();
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