package keep;

import java.io.*;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updateNote")
public class UpdateNote extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DAO dao = new DAO();
		Note note = new Note();
		Calendar date = Calendar.getInstance();
		note.setId(Integer.valueOf(request.getParameter("idNote")));
		note.setText(request.getParameter("text"));
		note.setLabel(request.getParameter("label"));
		note.setDateCreated(date);
		dao.updateNote(note);

		dao.close();
		request.getRequestDispatcher("./notes.jsp").forward(request, response);
	}
}