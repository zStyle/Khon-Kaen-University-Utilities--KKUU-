package kku.util.app;



import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//import android.os.StrictMode;

public class Table {
	String id;
	public Table(String id){
		this.id = id;
	}
	protected String getDayStartTime(int dayN) {
		if (dayN == 0) {
			dayN = 6;
		} else {
			dayN--;
		}
//		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//				.permitAll().build();
//		StrictMode.setThreadPolicy(policy);
		String re = null;
		try {
			URL url = new URL(
					"http://www.kittydev.net/KKU_TimeTable/timetable.php?std_id="
							+ id + "&mode=xml");
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document docRSS = builder.parse(url.openStream());
			NodeList days = docRSS.getElementsByTagName("day");

			Element dayElement = (Element) days.item(dayN);
			NodeList subject = dayElement.getElementsByTagName("subject");
			Element courseE = (Element) subject.item(0);

			re = getElementValue(courseE, "start");

		
		} catch (Throwable t) {
			return "Exception: " + t.toString();
		}
		return re;
	}

	protected String getDayStartRoom(int dayN) {
		if (dayN == 0) {
			dayN = 6;
		} else {
			dayN--;
		}
//		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//				.permitAll().build();
//		StrictMode.setThreadPolicy(policy);
		String re = null;
		try {
			URL url = new URL(
					"http://www.kittydev.net/KKU_TimeTable/timetable.php?std_id="
							+ id + "&mode=xml");
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document docRSS = builder.parse(url.openStream());
			NodeList days = docRSS.getElementsByTagName("day");

			Element dayElement = (Element) days.item(dayN);
			NodeList subject = dayElement.getElementsByTagName("subject");
			Element courseE = (Element) subject.item(0);

		re = getElementValue(courseE, "room");

			// in.close();
		} catch (Throwable t) {
			return "Exception: " + t.toString();
		}
		return re;
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
	}
}
