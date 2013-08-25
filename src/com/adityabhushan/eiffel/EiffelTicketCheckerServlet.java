package com.adityabhushan.eiffel;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class EiffelTicketCheckerServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");

		UserService userService = UserServiceFactory.getUserService();

		String thisURL = req.getRequestURI();
		Principal userPrincipal = req.getUserPrincipal();

		if (userPrincipal != null
				&& !userPrincipal.getName().equalsIgnoreCase(
						"adityabhushan@gmail.com")) {

			resp.getWriter().println(
					"<p>Hello, NonAdministrator..." + userPrincipal.getName()
							+ " Go Away" + "!  You can <a href=\""
							+ userService.createLogoutURL(thisURL)
							+ "\">sign out</a>.</p>");
		} else if (userPrincipal == null) {
			resp.sendRedirect(userService.createLoginURL(thisURL));
		} else {

			resp.getWriter()
					.println(
							"The dates in September on which Eiffel tickets are available  : ");
			List<String> availableDates = EiffelTicketChecker
					.getAvailableDates("1379116800");
			for (String date : availableDates) {
				resp.getWriter().println(date);
			}
		}
	}
}
