package com.adcash.mobileads.exampleproject.native_ad.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adcash.mobileads.exampleproject.R;
import com.adcash.mobileads.exampleproject.native_ad.adapters.StreamRecyclerViewAdapter;
import com.adcash.mobileads.exampleproject.native_ad.dummy.DummyContent;

public class StreamFragment extends Fragment {

    StreamRecyclerViewAdapter adapter;

    public StreamFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycle_view, container, false);
        Context context = recyclerView.getContext();
        adapter = new StreamRecyclerViewAdapter(getActivity(), DummyContent.ITEMS);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (adapter != null) adapter.destroy();
    }
}
