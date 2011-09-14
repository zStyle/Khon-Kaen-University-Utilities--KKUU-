package kku.util.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Info extends Activity {
    /** Called when the activity is first created. */
	
	public ImageButton information2,prepation ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        
        information2 = (ImageButton) findViewById(R.id.bt_infor);
        information2.setOnClickListener(new OnClickListener() {
		
		
			@Override
			public void onClick(View v) {
				
			}       	
        });
        
        prepation = (ImageButton) findViewById(R.id.bt_prepa);
        prepation.setOnClickListener(new OnClickListener() {
		
		
			@Override
			public void onClick(View v) {
				
			}       	
        });
        
    }
}
