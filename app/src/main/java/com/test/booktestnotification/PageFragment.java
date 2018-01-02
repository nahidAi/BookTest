package com.test.booktestnotification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.booktestnotification.Activity.MainActivity;
import com.test.booktestnotification.Adapter.AdapterBigPerson;
import com.test.booktestnotification.Adapter.AdapterFavorite;


public class PageFragment extends android.support.v4.app.Fragment{
    RecyclerView recyclerView;
    public int mPage;
    public static final  String ARG_PAGE = "ARG_PAGE";


    public static  PageFragment newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE,page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page,container,false);
        if (mPage==2){
            recyclerView = view.findViewById(R.id.recyclerView);
            AdapterBigPerson adapterCardView = new AdapterBigPerson(MainActivity.context,DummyData.dataList());
            recyclerView.setAdapter(adapterCardView);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.context));

        }
        if (mPage==1){
            recyclerView = view.findViewById(R.id.recyclerView);
            AdapterFavorite adapterFavorite = new AdapterFavorite(MainActivity.context,DummyData.dataList());
            recyclerView.setAdapter(adapterFavorite);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.context));

        }




        return view;
    }
}
