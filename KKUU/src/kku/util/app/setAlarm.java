package kku.util.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class setAlarm extends Activity {
	/** Called when the activity is first created. */
	String id;
	int firstClassHourToday;
	int firstClassHourWakeday;
	int firstClassMinWakeday;
	static String[] dayArray = { "", "Monday", "Tuesday", "Wednesday",
			"Thursday", "Friday", "" };
	static String[] wakeTimeWeek = { "", "", "", "", "", "", "" };
	Button sync, cancel;
	static Table timeTable;
	EditText idText, beforeTimeText;
	static int beforeTime;
	TextView tableUpdate;
	Intent intent;
	PendingIntent sender;
	AlarmManager am;
	private final static String NOTES = "notes.txt";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.autoalarm);
		sync = (Button) findViewById(R.id.syncalarm);
		idText = (EditText) findViewById(R.id.idalarm);
		beforeTimeText = (EditText) findViewById(R.id.beforTime);
		tableUpdate = (TextView) findViewById(R.id.tabletextview);
		cancel = (Button) findViewById(R.id.cancelalarm);
		// Set up interupt
		intent = new Intent(setAlarm.this, AlarmService_Service.class);
		sender = PendingIntent.getBroadcast(setAlarm.this, 0,
				intent, 0);
		am = (AlarmManager) getSystemService(ALARM_SERVICE);

		sync.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// get id from editText
				id = idText.getText().toString();
				// Create Class Timetable
				timeTable = new Table(id);
				// find waketime all the week

				beforeTime = Integer.parseInt(beforeTimeText.getText()
						.toString());
				String updateText = id + "\nFirst class of of each day start at\n";
				String checker = timeTable.getDayStartTime(1);
				if (checker.length() > 5) {
					Toast.makeText(getApplicationContext(), checker,
							Toast.LENGTH_LONG);
					return;
				}
				for (int i = 1; i <= 5; i++) {
					String tempString = timeTable.getDayStartTime(i);
					if (tempString.length() == 4) {
						String temp2 = tempString;
						tempString = "0";
						tempString += temp2;
					}
					wakeTimeWeek[i] = tempString;
					updateText += dayArray[i] + " : ";
					updateText += wakeTimeWeek[i] + "\n";
					// tempString += wakeTimeWeek[i];
				}

				// cast today
				Date today = new Date(java.lang.System.currentTimeMillis());

				// find out which day we have to wake up
				{
					String firstHourString = wakeTimeWeek[(today.getDay())];
					firstClassHourToday = Integer.parseInt(firstHourString
							.substring(0, 2));
				}
				Date wakeDay = getWakeDay(today);

				// get wakeuptime of wakeDay
				{
					String firstHourString = wakeTimeWeek[wakeDay.getDay()];
					firstClassHourWakeday = Integer.parseInt(firstHourString
							.substring(0, 2));
					firstClassMinWakeday = Integer.parseInt(firstHourString
							.substring(3, 5));
				}
				wakeDay.setHours(firstClassHourWakeday);
				wakeDay.setMinutes(firstClassMinWakeday);
				wakeDay.setSeconds(0);
				// set beforeTime
				wakeDay.setTime(wakeDay.getTime() - (60000 * beforeTime));
				// set the first interupt

				am.set(AlarmManager.RTC_WAKEUP, wakeDay.getTime(), sender);
				// Let the user know
				Toast.makeText(getApplicationContext(),
						"First waketime is" + wakeDay.toLocaleString(),
						Toast.LENGTH_SHORT).show();
				updateText += "The alarm will wake you up " + beforeTime
						+ " Minutes";
				tableUpdate.setText(updateText);
			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				am.cancel(sender);
				tableUpdate.setText("You did not syncronize yet");
			}
		});
	}

	Date getWakeDay(Date today) {
		int todayInt = today.getDay();
		long timeNow = today.getHours();
		if (todayInt >= 1 && todayInt <= 5) {
			if (timeNow < firstClassHourToday) {
				// so we will wakeup today
				return today;
			} else {
				Date newday = new Date(today.getTime()
						+ (long) (8.64 * Math.pow(10, 7)));
				return newday;
			}
		}
		// wakeDate is the next monday
		if (todayInt >= 6)
			today.setTime(today.getTime() + (long) (8.64 * Math.pow(10, 7)));
		if (todayInt == 7)
			today.setTime(today.getTime() + (long) (8.64 * Math.pow(10, 7)));
		return today;
	}
	public void onPause() {
		super.onPause();
		try {
			OutputStreamWriter out = new OutputStreamWriter(openFileOutput(
					NOTES, 0));
			out.write(tableUpdate.getText().toString());
			out.close();
		} catch (Throwable t) {
			Toast.makeText(this, "Exception: " + t.toString(), 2000).show();
		}
	}

	public void onResume() {
		super.onResume();
		try {
			InputStream in = openFileInput(NOTES);
			if (in != null) {
				InputStreamReader tmp = new InputStreamReader(in);
				BufferedReader reader = new BufferedReader(tmp);
				String str;
				StringBuffer buf = new StringBuffer();
				while ((str = reader.readLine()) != null) {
					buf.append(str + "\n");
				}
				in.close();
				tableUpdate.setText(buf.toString());
				idText.setText(buf.toString().substring(0,12));
				beforeTimeText.setText(buf.toString().substring(buf.length()-11,buf.length()));
			}
		} catch (java.io.FileNotFoundException e) {
			// that's OK, we probably haven't created it yet
		} catch (Throwable t) {
			Toast.makeText(this, "Exception: " + t.toString(), 2000).show();
		}

	}
}