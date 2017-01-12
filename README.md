## Prerequisites

* **ZONE ID(s)**. You create at [Adcash website](https://www.adcash.com/console/scripts.php).
* Android Studio 1.0 or higher. Instructions to [install Android Studio](https://developer.android.com/studio/install.html).
* Android API level 9 or higher

## Integration of SDK

#### Add Dependencies

##### JCenter repository

Add **JCenter** repository if you havn't added it yet.  
In your project base `build.gradle` file add:

```groovy
repositories {
    jcenter()
}
```
![alt tag](http://developer.adca.sh/wp-content/uploads/2015/12/integration_with_gradle_start.png)




Add Adcash SDK other required dependencies - <b>Google Play Services</b> and <b>Android Support Library v4</b> to your module based `build.gradle` file:

```groovy
dependencies {
    // Integrate Adcash SDK:
    compile 'com.adcash:adcash-sdk-lib:2.2.2'

    // Required by Adcash SDK:
    compile 'com.android.support:support-v4:24.2.0'
    compile 'com.google.android.gms:play-services:9.4.0'
}
```
![alt tag](http://developer.adca.sh/wp-content/uploads/2015/12/integration_with_gradle.png)

##### Local library file
If you prefer to use local file instead of Jcenter repository, you can also do it.

First, download the [Adcash SDK](https://github.com/adcash/android-sdk/raw/master/adcash-sdk-lib.zip) archive. Then create a `libs` folder (if you do not already have one) in your application / app module at the same level as your `src` and `build` folders and add the .AAR file from previously downloaded archive to it.
Then, in your project module `build.gradle` file add this in order to allow the .AAR to be accessible through the `libs` folder easily:

```groovy
repositories {
    flatDir {
       dirs 'libs'
    }
}
```
![alt tag](http://developer.adca.sh/wp-content/uploads/2015/12/integration_with_aar.png)

Finally, include the **Adcash SDK** to your project dependencies. Make sure to also include the two library-dependencies used and needed by the Adcash SDK - **Google Play Services** and **Android Support Library v4**.

> Note: It will be assumed here that you are integrating the two fore-mentioned libraries with Gradle but in case you want to use already prepared .AARs or .JARs, just add them to your libs folder and use the same procedure as with the Adcash SDK manual integration.

Now add the following dependencies to your module `build.gradle` file:

```groovy
dependencies {
    // Integrate Adcash SDK:
    compile(name: 'adcash-sdk-lib', ext: 'aar')

    // Required by Adcash SDK:
    compile 'com.android.support:support-v4:24.2.0'
    compile 'com.google.android.gms:play-services-ads:9.4.0'
}
```
![alt tag](http://developer.adca.sh/wp-content/uploads/2015/12/integration_with_aar_final.png)
> If your Adcash library file has different name than 'adcash-sdk-lib.aar', then please rename it to 'adcash-sdk-lib.aar'

  
Click **Sync Now** or request Gradle sync yourself if you have not been promoted automatically. Wait till Gradle finishes.

##### (Optional) Proguard settings

If you want to enable <b>Proguard</b> in your project, add following line to your  `proguard.cfg` file:

> -keep class com.adcash.mobileads.** { *; }



#### SDK General settings

At this point you have added all necessary dependencies to your project and need small modifications to your module `AndroidManifest.xml` file to finish with the integration.  

1. Add the following `<uses-permission>` tags: 
    * `INTERNET` - required to allow the Adcash SDK to make ad requests.
    * `ACCESS_NETWORK_STATE` - used to check the Network connection availability.  

```xml
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```

2. Add `<meta-data>` that is required by the **Google Play Services**.   

```xml
    <meta-data 
        android:name="com.google.android.gms.version" 
        android:value="@integer/google_play_services_version"/>
```

3. Add **AdcashActivity** for full screen ads to work (interstitial and rewarded video)  

```xml
    <activity
        android:name="com.adcash.mobileads.ui.AdcashActivity"
        android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
        android:hardwareAccelerated="true"
        android:theme="@android:style/Theme.Translucent" />
```

##### Here is a reference snippet for the manifest     
     
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.adcash.demo.banner">

    <!-- Include required permissions for Adcash SDK to run -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme">
        <activity
            android:name=".MainActivity"
            android:label="@string/app_name">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- Include the AdcashActivity -->
        <activity
            android:name="com.adcash.mobileads.ui.AdcashActivity"
            android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize" 
            android:theme="@android:style/Theme.Translucent"
            android:hardwareAccelerated="true" />

        <!-- This meta-data tag is required by Google Play Services -->
        <meta-data
            android:name="com.google.android.gms.version"
            android:value="@integer/google_play_services_version" />
    </application>

</manifest>
```
  
## Implementation of Ads
### Banner

Here is how you can easily integrate a banner into your app in just a 3 steps.

##### 1. Create ad
In xml layout file where you want to display banner, add namespace for **http://schemas.android.com/apk/res-auto**. Then define `AdcashBannerView` in layout.
```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:adcash="http://schemas.android.com/apk/res-auto" 
...>

    ...

    <com.adcash.mobileads.ui.AdcashBannerView
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        adcash:zone_id="<YOUR_BANNER_ZONE_ID>" />

</RelativeLayout>
```

##### (Optional) Add listener</b>"]
You can set `AdcashListener` for banner with the `setAdListener(<NEW_LISTENER>)` method. This listener is optional and is required only if you are interested in the events reported by the banner such as **ad loaded, failed to load, opened, closed and left application**.
**Reference to example snippet for more.**

##### 2. Request ad
All that is left to display banner is to call `loadAd()` on created AdcashBannerView.
```java
adcashBanner = (AdcashBannerView) findViewById(R.id.banner);
adcashBanner.loadAd();
```

##### 3. Destroy ad
After you don't need instance of `AdcashBannerView` anymore, you need to call `destroy()`. Usually you do it in `onDestroy()` of you activity.
```java
@Override
protected void onDestroy() {
    super.onDestroy();
    adcashBanner.destroy();
}
```

##### Here is an example snippet
```java
package com.adcash.demo.banner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.adcash.mobileads.AdcashError;
import com.adcash.mobileads.AdcashListener;
import com.adcash.mobileads.ui.AdcashBannerView;


public class BannerDemoActivity extends Activity {

    private AdcashBannerView mBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_demo);

        mBanner = (AdcashBannerView) findViewById(R.id.banner);

        // If needed implement event listener to catch banner loaded event
        mBanner.setAdListener(new AdcashListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(BannerDemoActivity.this, "Ad loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(AdcashError adcashError) {
                String message = adcashError.getErrorMessage();
                Toast.makeText(BannerDemoActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(BannerDemoActivity.this, "Ad opened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(BannerDemoActivity.this, "Ad closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(BannerDemoActivity.this, "Ad left Application", Toast.LENGTH_SHORT).show();
            }
        });
        mBanner.loadAd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBanner.destroy();
    }
}
```

##### Banner sizes

The **Adcash Android SDK** currently only supports one banner size – Smart Banners.    
Smart Banners are ad units with a screen-wide width and their height depends on the size of the device. The table below shows how the Smart Banner size varies in different devices:

| Height  | Device  | Device Orientation  |
|:--------|:--------|:--------------------|
| 50      | Phone   | Portrait, Landscape |
| 90      | Tablet  | Portrait, Landscape |


### Interstitial

Interstitial can display full screen static and video ads, you can setup which ads to display in publisher panel.

##### 1. Create ad
```java
mInterstitial = new AdcashInterstitial();
```

##### (Optional) Add listener
You can set `AdcashListener` for interstitial with the `setAdListener(<NEW_LISTENER>)` method. This listener is optional and is required only if you are interested in the events reported by the banner such as **ad loaded, failed to load, opened, closed and left application**.
**Reference to example snippet for more.**

##### 2. Request ad
```java
mInterstitial.setZoneId("<YOUR_ZONE_ID>");
mInterstitial.loadAd(activity);
```

##### 3. Show ad
```java
mInterstitial.showAd(activity);
```
> You should not call `showAd` right after calling `loadAd` since it takes some time to load full screen ad. To know when ad finished loading you could use `AdcashListener#onAdLoaded()` or `mInterstitial.isReady()`

##### 4. Destroy ad
After you don't need instance of `AdcashInterstitial` anymore, you need to call `destroy()`. Usually you do it in `onDestroy()` of you activity.
```java
@Override
protected void onDestroy() {
    super.onDestroy();
    mInterstitial.destroy();
}
```

##### Here is an example snippet
```java
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.adcash.mobileads.Adcash;
import com.adcash.mobileads.AdcashError;
import com.adcash.mobileads.AdcashListener;
import com.adcash.mobileads.ui.AdcashInterstitial;


public class InterstitialDemoActivity extends Activity {

    private AdcashInterstitial mInterstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_demo);

        mInterstitial = new AdcashInterstitial();
        mInterstitial.setZoneId("<INTERSTITIAL_ZONE_ID>");
        mInterstitial.setAdListener(new AdcashListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(InterstitialDemoActivity.this, "Ad loaded",
                        Toast.LENGTH_SHORT).show();
                mInterstitial.showAd(InterstitialDemoActivity.this);
            }

            @Override
            public void onAdFailedToLoad(AdcashError error) {
                String message = error.getErrorMessage();
                Toast.makeText(InterstitialDemoActivity.this, message,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(InterstitialDemoActivity.this, "Ad opened",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(InterstitialDemoActivity.this, "Ad closed",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(InterstitialDemoActivity.this,
                        "Ad left Application", Toast.LENGTH_SHORT).show();
            }
        });
        mInterstitial.loadAd(InterstitialDemoActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mInterstitial.destroy();
    }
}
```


### Rewarded

Rewarded Video is a high performing Ad-Format which provide capability to integrate Un-Skippable Video ads into your App and incentivizes user with In-App virtual currency (Coins, Life etc.) for watching Un-Skippable video ad.

##### 1. Create ad
```java
mRewardedVid = new AdcashRewardedVideo();
``` 

##### 2. Request ad
```java
mRewardedVid.setZoneId("<YOUR_ZONE_ID>");
mRewardedVid.loadAd(activity);
``` 

##### 3. Show ad
```java
mRewardedVid.showAd(activity);
``` 
> You should not call `showAd` right after calling `loadAd` since it takes some time to load full screen ad. Usually you should call `showAd` only after receiving `onAdLoaded` callback. </em>

##### 4. Destroy ad
After you don't need instance of `AdcashRewardedVideo` anymore, you need to call `destroy()`. Usually you do it in `onDestroy()` of you activity.
```java
@Override
protected void onDestroy() {
    super.onDestroy();
    mRewardedVid.destroy();
}
``` 


##### 5. Credit reward
Main idea about rewarded video is the reward. And to receive information about the reward (if you have set it up in publisher panel) you would have to set your instance of `AdcashRewardedVideo.Listener` on your Adcash rewarded video object using `setAdListener(AdcashRewardedVideo.Listener listener)`. This has to be done before `loadAd`.

```java
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.adcash.mobileads.AdcashError;
import com.adcash.mobileads.AdcashReward;
import com.adcash.mobileads.ui.AdcashRewardedVideo;

public class RewardedDemoActivity extends Activity {

    private AdcashRewardedVideo mRewardedVid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ewrarded_demo);

        mRewardedVid = new AdcashRewardedVideo();
        mRewardedVid.setZoneId("<REWARDED_ZONE_ID>");
        mRewardedVid.setAdListener(new AdcashRewardedVideo.Listener() {
            @Override
            public void onAdLoaded(AdcashReward reward) {
                // New rewarded video is loaded, you can extract information about reward
                Log.d("Rewarded video is ready", "Reward: " + reward.amount + " " + reward.name);
                mRewardedVid.showAd(RewardedDemoActivity.this);
            }

            @Override
            public void onAdFailedToLoad(AdcashError error) {
                //Some problems have arisen in the course of video preparation
                Log.d("Rewarded error", "Error: " + error.getErrorMessage());
            }

            @Override
            public void onAdOpened() {
                // Video is displayed to user
                Log.d("Rewarded video opened", "User is viewing ad");
            }

            @Override
            public void onAdReward(AdcashReward reward) {
                // Rewarded video have been watched and you can reward your user
                Log.d("Rewarded video closed", "Reward: " + reward.amount + " " + reward.name);
            }

            @Override
            public void onAdClosed() {
                // Rewarded video ad has been closed
                Log.d("Rewarded video closed", "User has closed rewarded video ad");
            }

            @Override
            public void onAdLeftApplication() {
                // User clicked some link in rewarded video which redirected user outside of your app
                Log.d("Rewarded video left", "User dot redirected");
            }
        });
        mRewardedVid.loadAd(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRewardedVid.destroy();
    }

}
``` 

### Native

Native is an ad format that is rendered by the publisher, allowing them to give users a unique experience with finely tuned look and design.

##### 1. Create ad and set zone id
```java
nativeAd= new AdcashNativeAd(activity);
```  

##### 2. Setup your native ad instance
```java
nativeAd.setZoneId("<YOUR_ZONE_ID>");
nativeAd.setListener(new AdcashNativeAd.Listener() {...});
```  

##### 3. Request ad
```java
nativeAd.loadAd();
```

##### 3. Create and bind your custom ad view
After loading, if response is successful, Adcash SDK will call 'onAdFailedToLoad(AdcashError adcashError)' of your 'AdcashNativeAd.Listener' passing information about the ad: title, description, rating, icon, image and action button text. You should use this information to create advertisement view best fit for your application to maximize user interest.
After you created and displayed your custom view for native ad you need to notify Adcash SDK for impression to be counted.
```java
View customAdView = createAdViewFromData(nativeAdData);
nativeAd.bindView(customAdView);
```

You can check our [example project](https://github.com/adcash/android-sdk/tree/master/ExampleProject) for ideas

##### 4. Destroy ad
After you don't need instance of `AdcashNativeAd` anymore, you need to call `destroy()`. Usually you do it in `onDestroy()` of you activity.
```java
@Override
protected void onDestroy() {
    super.onDestroy();
    nativeAd.destroy();
}
``` 

## Ad Events  

The `AdcashListener` class provides the following events that you can subscribe to for your ad format:

* `onAdLoaded` - when your ad is successfully loaded.
* `onAdFailedToLoad` - when for some reason (see below) your ad fails to load.
* `onAdOpened` - when your ad has occupied the whole screen of the device (for an interstitial that would be right after it is shown, while for a banner that would be when on clicking on it, the user gets redirected to the browser).
* `onAdClosed` - when your ad is no longer full screen (for interstitial this means it is no longer visible).
* `onAdLeftApplication` - when your ad takes the user to another application outside of your own (a browser, Google Play Store, etc.).

#### Ad Error Messages
The `onAdFailedToLoad` event returns an integer error code that can be one of the following:

* `NO_NETWORK` - There is no network connection.
* `NO_AD` - There is currently no ad available.
* `REQUEST_FAILED` - The request was not successful.
* `NETWORK_FAILURE` - Network was lost.
* `NOT_READY` - Ad was not loaded, so there is nothing to display.
* `ALREADY_DISPLAYED` - One interstitial is already displayed.

```java
        @Override
        public void onAdFailedToLoad(AdcashError error) {
            switch (error) {
                case NO_NETWORK:
                    // Handle the "No internet connection" situation here
                    break;
                case REQUEST_FAILED:
                    // Handle the "Request failed" situation here
                    break;
                case NETWORK_FAILURE:
                    // Handle the "Network failure" situation here
                    break;
                case NO_AD:
                    // Handle the "There is no ad" situation here
                    break;
                case NOT_READY:
                    // Handle the "Ad not loaded" situation here
                    break;
                case ALREADY_DISPLAYED:
                    // Handle the "Already displayed" situation here
                    break;
            }
        }
```

## Support  
If you need any help or assistance you can contact us by sending email to <mobile@adcash.com>.