package com.adcash.mobileads.exampleproject.native_ad.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adcash.mobileads.exampleproject.R;
import com.adcash.mobileads.exampleproject.native_ad.dummy.DummyContent;
import com.adcash.mobileads.models.NativeAdData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WallRecyclerViewAdapter extends RecyclerViewWithAdsAdapter<DummyContent.DummyItem> {


    public WallRecyclerViewAdapter(Activity activity, List<DummyContent.DummyItem> itemList) {
        super(activity, itemList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_AD:
                View adItemView = inflater.inflate(R.layout.fragment_wall_ad_item, parent, false);
                return new AdHolder(adItemView);
            case VIEW_TYPE_NORMAL:
            default:
                View view = inflater.inflate(R.layout.fragment_wall_item, parent, false);
                return new DummyHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_AD:
                AdHolder adHolder = (AdHolder) holder;
                NativeAdData adData = (NativeAdData) getItem(position);
                if (adData == null) {
                    adHolder.itemView.setVisibility(View.GONE);
                } else {
                    adHolder.itemView.setVisibility(View.VISIBLE);
                    adHolder.mTitleView.setText(adData.title);
                    adHolder.mButtonView.setText(adData.buttonLabel.toUpperCase());
                    adHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            adcashNativeAd.click();
                        }
                    });
                    adcashNativeAd.bindView(adHolder.itemView);

                    Picasso.with(adHolder.itemView.getContext())
                            .load(adData.images[0])
                            .into(adHolder.mImageView);
                }
                break;
            case VIEW_TYPE_NORMAL:
            default:
                DummyContent.DummyItem item = (DummyContent.DummyItem) getItem(position);
                DummyHolder dummyHolder = (DummyHolder) holder;
                dummyHolder.mImageView.setImageResource(item.imageId);
                dummyHolder.mTitleView.setText(item.title);
                break;
        }
    }

    public static class DummyHolder extends RecyclerView.ViewHolder {
        final ImageView mImageView;
        final TextView mTitleView;

        public DummyHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.image);
            mTitleView = (TextView) view.findViewById(R.id.title);
        }
    }

    public static class AdHolder extends RecyclerView.ViewHolder {
        final TextView mTitleView;
        final TextView mButtonView;
        final ImageView mImageView;

        public AdHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.image);
            mTitleView = (TextView) view.findViewById(R.id.title);
            mButtonView = (TextView) view.findViewById(R.id.button);
        }
    }
}
