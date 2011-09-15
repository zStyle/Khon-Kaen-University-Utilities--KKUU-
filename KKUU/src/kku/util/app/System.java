package kku.util.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class System extends Activity {
	/** Called when the activity is first created. */
	public ImageButton mute, cl_alarm, wifi;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system);

		mute = (ImageButton) findViewById(R.id.bt_mute);
		mute.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), AutoMuteController.class);
				startActivity(i);
			}
		});

		cl_alarm = (ImageButton) findViewById(R.id.bt_clock);
		cl_alarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(getApplicationContext(),
				// "This feature is not available yet",
				// Toast.LENGTH_SHORT).show();
				Intent k = new Intent(getApplicationContext(), setAlarm.class);

				startActivity(k);
			}
		});

		wifi = (ImageButton) findViewById(R.id.bt_wifi);
		wifi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), kku.util.al.Preferences.class);
				startActivity(i);
			}
		});

	}
}
