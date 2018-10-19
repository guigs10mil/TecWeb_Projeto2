package mvc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mvc.model.*;

@Controller
public class HolyNoteController {
	@RequestMapping("/")
	public String start() {
		return "index";
	}
	
	@RequestMapping("index")
	public String home() {
		return "index";
	}
	
	@RequestMapping("signUp")
	public String signUp() {
		return "signUp";
	}
	
	@RequestMapping("signIn")
	public String signIn() {
		return "signIn";
	}
	
	@RequestMapping("editUser")
	public String editUser() {
		return "editUser";
	}
	
	@RequestMapping("addNote")
	public String addNote(Note note, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		DAO dao = new DAO();
		HttpSession session = request.getSession(true);
		note.setColor(request.getParameter("color"));
		note.setDateCreated(Calendar.getInstance());
		note.setText(request.getParameter("text"));
		note.setIdUser((Integer)session.getAttribute("idUser"));
		note.setLabel(request.getParameter("label"));
		if (note.getText().length()>0 && note.getLabel().length()>0){
			dao.addNote(note);
		}
		dao.close();
		return "notes";
	}
	
	
	@RequestMapping("FilterColor")
	public String filterColor(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		List<String> filter = new ArrayList<String>();
		if (request.getParameterValues("color") != null) {
			String[] filterVector = request.getParameterValues("color");

			for (String color : filterVector) {
				filter.add(color);
			}
		}
		session.setAttribute("filter", filter);
		return "notes";
	}
	
	@RequestMapping("removeNote")
	public String removeNote(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO dao = new DAO();
        HttpSession session = request.getSession(true);
        dao.removeNote(Integer.valueOf(request.getParameter("idNote")));
        System.out.println((Integer)session.getAttribute("idUser"));
        dao.close();
        return "notes";
    }
	
	@RequestMapping("removeUser")
	public String removeUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DAO dao = new DAO();
		HttpSession session = request.getSession(true);
		User user = new User();
		user.setName((String)session.getAttribute("username"));
		user.setPassword(request.getParameter("password"));
		if (dao.verifyLogin(user)) {
			dao.removeUser((Integer)session.getAttribute("idUser"));
		}
        dao.close();
        
        return "index";
	}
	
	@RequestMapping("searchUser")
	public String searchUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		if (request.getParameterValues("search") != null) {
			String search = request.getParameter("search");
			session.setAttribute("search", search);
		}
		return "notes";
	}
	
	@RequestMapping(value="signUpUser", method = RequestMethod.POST)
	public String signUpUser(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {
		DAO dao = new DAO();
		User user = new User();
		user.setName(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		
		if (dao.verifySignUp(user)) {
			dao.addUser(user);
			System.out.println("Sign Up Succeeded! YABOIII");
			dao.close();
			return "index";
		}
		else {
			System.out.println("Sign Up Failed!");
			dao.close();
			return "signUp";
		}
	}
	
	@RequestMapping("updateNote")
	public String updateNote(HttpServletRequest request, HttpServletResponse response)
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
		return "notes";
	}
	
	@RequestMapping("updateUser")
	public String updateUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DAO dao = new DAO();
		User user = new User();
		HttpSession session = request.getSession(true);
		int idUser = (Integer)session.getAttribute("idUser");
		user.setName((String)session.getAttribute("username"));
		user.setPassword(request.getParameter("old_password"));
		if (dao.verifyLogin(user)) {
			user.setId(idUser);
			user.setName(request.getParameter("new_username"));
			user.setPassword(request.getParameter("new_password"));
			dao.updateUser(user);
			dao.close();
		}

		request.setAttribute("idUser", idUser);
		return "notes";
	}
	
	@RequestMapping("verifyLogin")
	public String verifyLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = new User();
		user.setName(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));

		DAO dao = new DAO();
		if (dao.verifyLogin(user)) {
			int idUser = dao.getIdUser(user);
			HttpSession session = request.getSession(true);
			session.setAttribute("idUser", idUser);
			List<String> filter = new ArrayList<String>();
			String search = new String();
			session.setAttribute("filter", filter);
			session.setAttribute("search", search);
			dao.close();
			System.out.println("Sign In Succeeded!");
			return "notes";
		} else {
			dao.close();
			System.out.println("Sign In Failed!");
			return "signIn";
			
		}
	}
}
