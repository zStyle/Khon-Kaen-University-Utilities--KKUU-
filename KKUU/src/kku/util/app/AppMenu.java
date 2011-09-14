package kku.util.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class AppMenu extends Activity {
	/** Called when the activity is first created. */

	public ImageButton news, information, find, system;
	public ImageButton news_text,information_text,find_text,system_text ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		news = (ImageButton) findViewById(R.id.Button_news);
		news.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "Not Available yet", Toast.LENGTH_SHORT).show();
				Intent a = new Intent(getApplicationContext(), Feed.class);
				startActivity(a);
			}
		});
		news_text = (ImageButton) findViewById(R.id.imageView1);
		news_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "Not Available yet", Toast.LENGTH_SHORT).show();
				Intent a = new Intent(getApplicationContext(), Feed.class);
				startActivity(a);
//
//				Intent u = new Intent(getApplicationContext(), News.class);
//
//				startActivity(u);
			}
		});
		information = (ImageButton) findViewById(R.id.Button_infor);
		information.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub

				Intent b = new Intent(getApplicationContext(), Info.class);

				startActivity(b);
			}
		});

		find = (ImageButton) findViewById(R.id.Button_find);
		find.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent c = new Intent(getApplicationContext(), Find.class);

				startActivity(c);
			}
		});

		system = (ImageButton) findViewById(R.id.Button_system);
		system.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent d = new Intent(getApplicationContext(), System.class);

				startActivity(d);
			}
		});
		

		
		information_text = (ImageButton) findViewById(R.id.imageView2);
		information_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent x = new Intent(getApplicationContext(), Info.class);

				startActivity(x);
			}
		});
		
		find_text = (ImageButton) findViewById(R.id.imageView3);
		find_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent y = new Intent(getApplicationContext(), Find.class);

				startActivity(y);
			}
		});
		
		system_text = (ImageButton) findViewById(R.id.imageView4);
		system_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent o = new Intent(getApplicationContext(), System.class);

				startActivity(o);
			}
		});

	}
}