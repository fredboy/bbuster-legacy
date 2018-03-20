package ru.fredboy.ballsbuster;

import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
// import com.google.android.gms.ads.*;


public class AndroidLauncher extends AndroidApplication  implements IActivityRequestHandler{

	// private AdView adView;
	// private InterstitialAd interAd;

	// private static final String ADMOB_APP_ID = "";
	// private static final String ADMOB_BANNER_ID = "";
	// private static final String INTERSTITIAL_ID = "";

	// private final int SHOW_ADS = 1;
	// private final int HIDE_ADS = 0;

	// protected Handler handler = new Handler(){
	// 	@Override
	// 	public void handleMessage(Message msg) {
	// 		switch(msg.what) {
	// 			case SHOW_ADS:
	// 			{
	// 				adView.setVisibility(View.VISIBLE);
	// 				break;
	// 			}
	// 			case HIDE_ADS:
	// 			{
	// 				adView.setVisibility(View.GONE);
	// 				break;
	// 			}
	// 		}
	// 	}
	// };

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useGyroscope = false;
		config.hideStatusBar = true;
		config.useImmersiveMode = true;
		RelativeLayout layout = new RelativeLayout(this);

		View gameView = initializeForView(new BallsBuster(this), config);
		// MobileAds.initialize(this,ADMOB_APP_ID);

		// adView = new AdView(this);
		// adView.setAdSize(AdSize.BANNER);
		// adView.setAdUnitId(ADMOB_BANNER_ID);
		// AdRequest adRequest = new AdRequest.Builder().build();
		// adView.loadAd(adRequest);
		layout.addView(gameView);
		// RelativeLayout.LayoutParams adParams =
				// new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
						// RelativeLayout.LayoutParams.WRAP_CONTENT);
		// adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		// adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		// layout.addView(adView, adParams);
		setContentView(layout);
		showBanner(true);

		// interAd = new InterstitialAd(this);
		// interAd.setAdUnitId(INTERSTITIAL_ID);
		// loadIntersitialAd();


	}

	// private void loadIntersitialAd(){

	// 	AdRequest interstitialRequest = new AdRequest.Builder().build();
	// 	interAd.loadAd(interstitialRequest);
	// }

	@Override
	public void showBanner(boolean show) {
		// if (!show) {
		// 	runOnUiThread(new Runnable() {
	    //                     public void run() {
        //                 		AdRequest adRequest = new AdRequest.Builder().build();
        //         	        	adView.loadAd(adRequest);
        // 	                }
	    //             });
		// }
		// handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}

	@Override
	public void showInterstitial() {
		// runOnUiThread(new Runnable() {
		// 	public void run() {
		// 		if (interAd.isLoaded()){
		// 			interAd.show();
		// 		} else {
		// 			loadIntersitialAd();
		// 		}
		// 	}
		// });
	}

	@Override
	public void onBackPressed() {

	}
}
