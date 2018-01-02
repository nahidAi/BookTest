package com.test.booktestnotification.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.test.booktestnotification.PageFragment;



public class AdapterFragment extends FragmentPagerAdapter {

    private String tabTitle[] = new String[]{"علاقه مندی","جملات ناب"};
    public AdapterFragment(FragmentManager fm) {
        super(fm);
    }
//تبی که وجود نداره و قراره بوجو بیاد را ریترن میکنه
    @Override
    public Fragment getItem(int position) {
       return PageFragment.newInstance( position + 1);
    }
//تعداد تب هارو ریترن میکنه
    @Override
    public int getCount() {
        return 2;
    }
//عنوان تب هارو ریترن میکنه
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
