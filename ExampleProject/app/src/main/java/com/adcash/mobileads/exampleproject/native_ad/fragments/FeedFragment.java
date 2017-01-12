package com.adcash.mobileads.exampleproject.native_ad.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adcash.mobileads.exampleproject.R;
import com.adcash.mobileads.exampleproject.native_ad.adapters.FeedRecyclerViewAdapter;
import com.adcash.mobileads.exampleproject.native_ad.dummy.DummyContent;

public class FeedFragment extends Fragment {

    private FeedRecyclerViewAdapter adapter;

    public FeedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycle_view, container, false);
        Context context = recyclerView.getContext();
        adapter = new FeedRecyclerViewAdapter(getActivity(), DummyContent.ITEMS);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (adapter != null) adapter.destroy();
    }
}
