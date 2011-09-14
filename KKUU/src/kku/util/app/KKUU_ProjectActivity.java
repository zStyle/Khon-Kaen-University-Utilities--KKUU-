package kku.util.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class KKUU_ProjectActivity extends Activity {
    /** Called when the activity is first created. */
	public ImageButton go;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		go = (ImageButton) findViewById(R.id.enter);
		go.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(getApplicationContext(),AppMenu.class);
				startActivity(i);
			}
		});

	}
}