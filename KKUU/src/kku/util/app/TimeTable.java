package kku.util.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class TimeTable extends Activity {
	WebView webview;
	EditText id;
	Button ok;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable);
        webview = (WebView) findViewById(R.id.webView);
        id = (EditText) findViewById(R.id.stuId);
        ok = (Button)findViewById(R.id.okButton);
        webview.getSettings().setJavaScriptEnabled(true);
   
        ok.setOnClickListener(new OnClickListener(){
        	
			public void onClick(View v) {
				 webview.loadUrl("http://www.kittydev.net/KKU_TimeTable/timetable.php?std_id="+id.getText().toString());
				
			}
        	
        });
       
    }
}