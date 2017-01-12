package com.adcash.mobileads.exampleproject.native_ad.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.adcash.mobileads.AdcashError;
import com.adcash.mobileads.models.NativeAdData;
import com.adcash.mobileads.ui.AdcashNativeAd;

import java.util.List;

public abstract class RecyclerViewWithAdsAdapter<ItemObject> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected static final int VIEW_TYPE_AD = 1;
    protected static final int VIEW_TYPE_NORMAL = 0;
    private static final int AD_MODIFIER = 6;

    private List<ItemObject> itemList;
    protected AdcashNativeAd adcashNativeAd;
    private AdcashNativeAd.Listener adcashAdListener = new AdcashNativeAd.Listener() {
        @Override
        public void onAdLoaded(NativeAdData nativeAdData) {
            notifyDataSetChanged();
        }

        @Override
        public void onAdFailedToLoad(AdcashError adcashError) {
            Log.e(getClass().getSimpleName(), "Error loading ad: " + adcashError.getErrorMessage());
        }
    };

    public RecyclerViewWithAdsAdapter(Activity activity, List<ItemObject> itemList) {
        this.itemList = itemList;
        adcashNativeAd = new AdcashNativeAd(activity);
        adcashNativeAd.setListener(adcashAdListener);
        adcashNativeAd.setZoneId("1479699");
        adcashNativeAd.loadAd();
    }

    public void reloadAd() {
        if (adcashNativeAd != null) adcashNativeAd.loadAd();
    }

    protected Object getItem(int position) {
        if (getItemViewType(position) == VIEW_TYPE_AD) {
            if (adcashNativeAd == null) return null;
            return adcashNativeAd.getLastLoadedAd();
        }
        return itemList.get(position - position / AD_MODIFIER);
    }

    @Override
    public int getItemViewType(int position) {
        return (position + 1) % AD_MODIFIER == 0 ? VIEW_TYPE_AD : VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return itemList.size() + (itemList.size() / AD_MODIFIER);
    }

    public void destroy() {
        if (adcashNativeAd != null) {
            adcashNativeAd.destroy();
            adcashNativeAd = null;
        }
    }
}
