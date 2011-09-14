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
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Reg extends ListActivity {
	TextView selection;
	ArrayList<String> items = new ArrayList<String>();
	ArrayList<String> strURL = new ArrayList<String>();

	@Override
	public void onCreate(Bundle icicle) {
	super.onCreate(icicle);
	setContentView(R.layout.reglayout);
	selection = (TextView) findViewById(R.id.selection);
	registerForContextMenu(selection);
	try {
		URL urlRSS = new URL("http://www.news.kku.ac.th/kkunews/index2.php?option=com_rss&feed=RSS1.0&no_html=1");
		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document docRSS = builder.parse(urlRSS.openStream());
		NodeList words = docRSS.getElementsByTagName("item");
	
		for (int i = 0; i < words.getLength(); i++) {
			Element element = (Element) words.item(i);
			String temps = getElementValue(element, "title");
			String urlTemps = getElementValue(element, "link");
			items.add(temps);
			strURL.add(urlTemps);
		}
		// in.close();
	} catch (Throwable t) {
		Toast.makeText(this, "Exception: " + t.toString(), 2000).show();
	}
	
	setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items));

}

@Override
public void onListItemClick(ListView parent, View v, int position, long id) {
	Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(strURL.get(position).toString()));

	startActivity(myIntent);
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
