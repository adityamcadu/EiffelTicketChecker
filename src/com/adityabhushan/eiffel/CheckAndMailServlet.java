package com.adityabhushan.eiffel;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CheckAndMailServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		List<String> dates = EiffelTicketChecker
				.getAvailableDates("1379116800");
		String dateStr = "";
		for (String date : dates) {
			if (date.trim().equals("14") || date.trim().equals("15")
					|| date.trim().equals("16") || date.trim().equals("2")) {
				dateStr += date + " , ";
			}
		}
		if(!"".equals(dateStr)){
			try {
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("adityabhushan@gmail.com",
						"EiffelChecker Admin"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						"adityabhushan@gmail.com", "Aditya Bhushan"));
				msg.setSubject("Available Dates for Eiffel in September");
				msg.setText("Available Dates : " + dateStr);
				Transport.send(msg);
				resp.getWriter().println("mail sent");
			} catch (Exception e) {
				e.printStackTrace(resp.getWriter());
			}
		}else{
			resp.getWriter().println("no dates available");
		}

	}

}
