package info.chees.patheutils.servlets;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

@SuppressWarnings("serial")
public class CronServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar c = Calendar.getInstance(Locale.forLanguageTag("nl-NL"));
		for (int i = 0; i < 7; i++) {
			Date d = c.getTime();
			
			Queue queue = QueueFactory.getQueue("movies");
			queue.add(withUrl("/getmovies").method(Method.GET).param("date", format.format(d)));

			c.add(Calendar.DATE, 1);
		}
	}
}
