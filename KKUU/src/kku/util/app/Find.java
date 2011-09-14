package kku.util.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Find extends Activity {
    /** Called when the activity is first created. */
	public ImageButton locate,schedule ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find);
        
        locate = (ImageButton) findViewById(R.id.bt_location);
        locate.setOnClickListener(new OnClickListener() {
		
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
        		 //Intent	g = new Intent(getApplicationContext(),Page2.class);
        		 
			    //startActivity(g);
			}       	
        });
		
        schedule = (ImageButton) findViewById(R.id.bt_schedule);
        schedule.setOnClickListener(new OnClickListener() {
		
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
        		Intent	h = new Intent(getApplicationContext(),TimeTable.class);
        		 
			    startActivity(h);
			}       	
        });
        
    }
}

