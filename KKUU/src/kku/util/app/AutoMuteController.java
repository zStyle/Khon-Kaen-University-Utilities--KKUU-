package kku.util.app;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AutoMuteController extends Activity {
	AudioManager mAudioManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.automute);
		mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		CheckBox service = (CheckBox) findViewById(R.id.automute_cb);
		service.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean enable) {
				// TODO Auto-generated method stub
				if(enable){
					Intent i = new Intent(getApplicationContext(),
							AutoMuteService.class);
					startService(i);
					Toast.makeText(getApplicationContext(), "AutoMute Service Enabled", Toast.LENGTH_SHORT).show();
				}else{
					Intent i = new Intent(getApplicationContext(),
							AutoMuteService.class);
					stopService(i);
					Toast.makeText(getApplicationContext(), "AutoMute Service Disabled", Toast.LENGTH_SHORT).show();
				}
			}});

		ImageButton mute = (ImageButton) findViewById(R.id.mute_img);
		mute.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
				AutoMuteService.muted = true;
				Toast.makeText(getApplicationContext(), "Muted", Toast.LENGTH_SHORT).show();
			}
		});
		ImageButton unmute = (ImageButton) findViewById(R.id.unmute_img);
		unmute.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				AutoMuteService.muted = false;
				Toast.makeText(getApplicationContext(), "Unmuted", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
