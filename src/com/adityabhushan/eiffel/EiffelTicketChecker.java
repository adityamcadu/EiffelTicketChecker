package com.adityabhushan.eiffel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class EiffelTicketChecker {

	public static List<String> getAvailableDates(String timeStamp){

		try {
			final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17);
//		webClient.waitForBackgroundJavaScript(10000);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			WebRequest requestSettings = new WebRequest(new URL("http://ticket.toureiffel.fr/index-css5-sete-lgen-pg51.html"), HttpMethod.POST);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new NameValuePair("idEvt", "56227"));
			nvps.add(new NameValuePair("date", timeStamp));
			nvps.add(new NameValuePair("tarif55221", "3"));
			nvps.add(new NameValuePair("tarif55231", "0"));
			nvps.add(new NameValuePair("tarif55237", "0"));
			nvps.add(new NameValuePair("tarif54621", "0"));
			nvps.add(new NameValuePair("tarif55243", "0"));
			nvps.add(new NameValuePair("tarif55239", "0"));
			requestSettings.setRequestParameters(nvps);
			final HtmlPage page = webClient.getPage(requestSettings);
			
			DomElement contenuCalendar = page.getElementById("contenuCalendar");
//	    resp.getWriter().print(contenuCalendar.getTagName());
			DomNodeList<HtmlElement> tables =  contenuCalendar.getElementsByTagName("table");
			HtmlElement calendarTable = tables.get(0);
//	    resp.getWriter().print(calendarTable.getNodeName());
			List<HtmlElement> weekdaysAvailable = calendarTable.getElementsByAttribute("td", "class", "current-month weekday ");
			List<HtmlElement> weekendsAvailable = calendarTable.getElementsByAttribute("td", "class", "current-month weekend ");
			List<String> availableDates = new ArrayList<>();
			for(HtmlElement weekday:weekdaysAvailable){
				availableDates.add(weekday.getTextContent());
			}
			for(HtmlElement weekend:weekendsAvailable){
				availableDates.add(weekend.getTextContent());	
			}
			webClient.closeAllWindows();
			return availableDates;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	
	}
}
