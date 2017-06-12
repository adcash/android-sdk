package com.adcash.mobileads.exampleproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.adcash.mobileads.Adcash;
import com.adcash.mobileads.exampleproject.native_ad.NativeAdActivity;
import com.adcash.mobileads.listeners.AdcashListener;
import com.adcash.mobileads.listeners.AdcashRewardedListener;
import com.adcash.mobileads.models.AdcashError;
import com.adcash.mobileads.models.AdcashReward;
import com.adcash.mobileads.ads.AdcashBannerView;
import com.adcash.mobileads.ads.AdcashInterstitial;
import com.adcash.mobileads.ads.AdcashRewardedVideo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AdcashBannerView mBanner;
    private AdcashInterstitial mInterstitial;
    private AdcashRewardedVideo mRewardedVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Adcash.initialize(this);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bannerButton).setOnClickListener(this);
        findViewById(R.id.interstitialButton).setOnClickListener(this);
        findViewById(R.id.rewardedVideoButton).setOnClickListener(this);
        findViewById(R.id.nativeAdsButton).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bannerButton:
                showBanner();
                return;
            case R.id.interstitialButton:
                showInterstitial();
                return;
            case R.id.rewardedVideoButton:
                showRewardedVideo();
                return;
            case R.id.nativeAdsButton:
                startActivity(new Intent(this, NativeAdActivity.class));
                return;
            default:
                return;
        }
    }

    private void showBanner() {
        mBanner = (AdcashBannerView) findViewById(R.id.banner);
        // If needed implement event listener to catch banner loaded event
        mBanner.setAdListener(new AdcashListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(MainActivity.this, "Banner loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(AdcashError error) {
                String message = "Banner error: " + error.getErrorMessage();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(MainActivity.this, "Banner opened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(MainActivity.this, "Banner closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(MainActivity.this, "Banner left Application", Toast.LENGTH_SHORT).show();
            }
        });
        mBanner.loadAd();
        Toast.makeText(MainActivity.this, "Loading banner", Toast.LENGTH_SHORT).show();
    }

    private void showInterstitial() {
        mInterstitial = new AdcashInterstitial("1461177");
        mInterstitial.setAdListener(new AdcashListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(MainActivity.this, "Interstitial loaded", Toast.LENGTH_SHORT).show();
                mInterstitial.show(MainActivity.this);
            }

            @Override
            public void onAdFailedToLoad(AdcashError error) {
                String message = "Interstitial error: " + error.getErrorMessage();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(MainActivity.this, "Interstitial opened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(MainActivity.this, "Interstitial closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(MainActivity.this, "Interstitial left Application", Toast.LENGTH_SHORT).show();
            }
        });
        mInterstitial.loadAd();
        Toast.makeText(MainActivity.this, "Loading interstitial", Toast.LENGTH_SHORT).show();
    }

    private void showRewardedVideo() {

        mRewardedVideo = new AdcashRewardedVideo("1461181");
        mRewardedVideo.setAdListener(new AdcashRewardedListener() {
            @Override
            public void onAdLoaded(AdcashReward reward) {
                Toast.makeText(MainActivity.this, "Rewarded video loaded", Toast.LENGTH_SHORT).show();
                mRewardedVideo.show(MainActivity.this);
            }

            @Override
            public void onAdFailedToLoad(AdcashError error) {
                String message = "Rewarded video error: " + error.getErrorMessage();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(MainActivity.this, "Rewarded video opened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdReward(AdcashReward reward) {
                Toast.makeText(MainActivity.this, "Rewarded video viewed, reward" + reward.amount + " " + reward.name , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(MainActivity.this, "Rewarded video closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(MainActivity.this, "Rewarded video left Application", Toast.LENGTH_SHORT).show();
            }
        });
        mRewardedVideo.loadAd();
        Toast.makeText(MainActivity.this, "Loading rewarded video", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Destroy ads you have created:
        if (mBanner != null) mBanner.destroy();
        if (mInterstitial != null) mInterstitial.destroy();
        if (mRewardedVideo != null) mRewardedVideo.destroy();
    }
}
