package kku.util.app;


import android.net.Uri;
import android.os.Bundle;

import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Reg extends ListActivity {
	TextView selection;
	ArrayList<String> items = new ArrayList<String>();
	ArrayList<String> strURL = new ArrayList<String>();
	ArrayList<String> description = new ArrayList<String>();

	@Override
	public void onCreate(Bundle icicle) {
	super.onCreate(icicle);
	setContentView(R.layout.reglayout);
	selection = (TextView) findViewById(R.id.selection);
	registerForContextMenu(selection);
	try {
		URL urlRSS = new URL("http://202.28.92.175/rss");
		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document docRSS = builder.parse(urlRSS.openStream());
		NodeList words = docRSS.getElementsByTagName("item");
	
		for (int i = 0; i < words.getLength(); i++) {
			Element element = (Element) words.item(i);
			String temps = getElementValue(element, "title");
			String urlTemps = getElementValue(element, "link");
			String deTemps = getElementValue(element, "description");
			items.add(temps);
			strURL.add(urlTemps);
			description.add(deTemps);
		}
		// in.close();
	} catch (Throwable t) {
		Toast.makeText(this, "Exception: " + t.toString(), 2000).show();
	}
	
	setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items));

}

@Override
public void onListItemClick(ListView parent, View v, int position, long id) {
	 final Dialog dialog = new Dialog(Reg.this);
     dialog.setContentView(R.layout.dialog);
     dialog.setTitle("Description");
     dialog.setCancelable(true);
     
     TextView text = (TextView) dialog.findViewById(R.id.TextView01);
     text.setText(description.get(position).toString());

     final String url = strURL.get(position).toString();

     Button linkButton = (Button) dialog.findViewById(R.id.link);
     linkButton.setOnClickListener(new OnClickListener() {
     public void onClick(View v) {
   	  Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

   		startActivity(myIntent);
   		dialog.cancel();
         }
     });
     Button cancelButton = (Button) dialog.findViewById(R.id.cancel);
     cancelButton.setOnClickListener(new OnClickListener() {
     public void onClick(View v) {
   	  dialog.cancel();
         }
     });
	
     dialog.show();
}

protected String getElementValue(Element parent, String label) {
	return getCharacterDataFromElement((Element) parent
			.getElementsByTagName(label).item(0));
}

private String getCharacterDataFromElement(Element e) {
	try {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
	} catch (Exception ex) {

	}
	return "";
} // private String getCharacterDataFromElement

protected float getFloat(String value) {
	if (value != null && !value.equals("")) {
		return Float.parseFloat(value);
	}
	return 0;
}

}
