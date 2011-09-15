package kku.util.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class KKUInfo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kkuinfo);
		Button moreInfo = (Button) findViewById(R.id.moreinfo);
		moreInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri
				 .parse("http://www.kku.ac.th/kku.php?page=about"));
				
				 startActivity(myIntent);
			}
		});
	}

}
