package kku.util.app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class AutoMuteService extends Service {
	LocationManager mlocManager;
	LocationListener mlocListener;
	AudioManager mAudioManager;
	static boolean muted = false;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return START_STICKY;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mlocListener = new MyLocationListener();
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				mlocListener);
		mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
	}

	class MyLocationListener implements LocationListener {
		
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			double lat = location.getLatitude();
			double lon = location.getLongitude();
			//Toast.makeText(AutoMuteService.this, lat + " " + lon, Toast.LENGTH_SHORT).show();
			if(mAudioManager.getMode() == AudioManager.MODE_RINGTONE)
				muted = false;
			mute(lat,lon);
			
		}

		private void mute(double lat, double lon) {
			// TODO Auto-generated method stub
			double top = 16.476707,bot = 16.467901,left = 102.822933,right=102.828169;
			if(lat < top && lat > bot && lon > left && lon < right){
				mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
				muted = true;
				Toast.makeText(AutoMuteService.this, "muted", Toast.LENGTH_SHORT).show();
			}else{
				if(muted == true){
					mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
					muted = false;
					Toast.makeText(AutoMuteService.this, "unmuted", Toast.LENGTH_SHORT).show();
				}else{
					Log.e("gps", bot + " " + lon + " " + top + "\n" + left + " "
									+ lon + " " + right);
//					Toast.makeText(
//							AutoMuteService.this,
//							bot + " " + lat + " " + top + "\n" + left + " "
//									+ lon + " " + right, Toast.LENGTH_SHORT)
//							.show();
					Log.v("AutoMuteService", bot + " " + lat + " " + top + "\n" + left + " "
									+ lon + " " + right);
				}
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		mlocManager.removeUpdates(mlocListener);
		super.onDestroy();
	}
}