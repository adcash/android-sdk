package com.adcash.mobileads.exampleproject.native_ad.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adcash.mobileads.exampleproject.R;
import com.adcash.mobileads.exampleproject.native_ad.adapters.WallRecyclerViewAdapter;
import com.adcash.mobileads.exampleproject.native_ad.dummy.DummyContent;

public class WallFragment extends Fragment {

    private WallRecyclerViewAdapter adapter;

    public WallFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycle_view, container, false);
        adapter = new WallRecyclerViewAdapter(getActivity(), DummyContent.ITEMS);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (adapter != null) adapter.destroy();
    }
}
