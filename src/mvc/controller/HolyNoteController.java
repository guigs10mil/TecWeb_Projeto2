package mvc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import mvc.model.*;

@Controller
public class HolyNoteController {
	@RequestMapping("/")
	public String start() {
		return "index";
	}
	
	@RequestMapping(value="index", method=RequestMethod.GET)
	public String home() {
		return "index";
	}
	
	@RequestMapping(value="signUp", method=RequestMethod.GET)
	public String signUp() {
		return "signUp";
	}
	
	@RequestMapping(value="signIn", method=RequestMethod.GET)
	public String signIn() {
		return "signIn";
	}
	
	@RequestMapping(value="editUser", method=RequestMethod.GET)
	public String editUser() {
		return "editUser";
	}
	
	@RequestMapping(value="addNote", method=RequestMethod.POST)
	public String addNote(Note note, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		DAO dao = new DAO();
		HttpSession session = request.getSession(true);
		note.setColor(request.getParameter("color"));
		note.setDateCreated(Calendar.getInstance());
		
		String text = request.getParameter("text");
		text = text.trim();
		text = text.replaceAll("\\s+", "+");
		
		HttpResponse<JsonNode> jj = null;
		try {
			jj = Unirest.get("https://montanaflynn-spellcheck.p.mashape.com/check/?text=" + text)
			.header("X-Mashape-Key", "SnZq2ejHP1mshQdJWFiVQV2YvnBmp1wIQExjsnEgGwEoz9BqPD")
			.header("Accept", "application/json")
			.asJson();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONObject myObj = jj.getBody().getObject();
	    System.out.println(myObj);
	    
	    
	    
	    if (myObj.get("suggestion").equals(null) | myObj.get("suggestion").equals(false)) {
	    	note.setText((String)myObj.get("original"));
	    }
	    else {
	    	note.setText((String)myObj.get("suggestion"));
	    }
		
		note.setIdUser((Integer)session.getAttribute("idUser"));
		note.setLabel(request.getParameter("label"));
		note.setLocation(request.getParameter("loc"));
		if (note.getText().length()>0 && note.getLabel().length()>0){
			dao.addNote(note);
		}
		dao.close();
		return "notes";
	}
	
	
	@RequestMapping(value="FilterColor", method=RequestMethod.GET)
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
	
	@RequestMapping(value="removeNote", method=RequestMethod.DELETE)
	public String removeNote(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO dao = new DAO();
        HttpSession session = request.getSession(true);
        dao.removeNote(Integer.valueOf(request.getParameter("idNote")));
        System.out.println((Integer)session.getAttribute("idUser"));
        dao.close();
        return "notes";
    }
	
	@RequestMapping(value="removeUser", method=RequestMethod.DELETE)
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
	
	@RequestMapping(value="searchUser", method=RequestMethod.GET)
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
			System.out.println("Sign Up Succeeded!");
			dao.close();
			return "index";
		}
		else {
			System.out.println("Sign Up Failed!");
			dao.close();
			return "signUp";
		}
	}
	
	@RequestMapping(value="updateNote", method=RequestMethod.PUT)
	public String updateNote(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DAO dao = new DAO();
		Note note = new Note();
		Calendar date = Calendar.getInstance();
		note.setId(Integer.valueOf(request.getParameter("idNote")));
		
		String text = request.getParameter("text");
		text = text.trim();
		text = text.replaceAll("\\s+", "+");
		
		HttpResponse<JsonNode> jj = null;
		try {
			jj = Unirest.get("https://montanaflynn-spellcheck.p.mashape.com/check/?text=" + text)
			.header("X-Mashape-Key", "SnZq2ejHP1mshQdJWFiVQV2YvnBmp1wIQExjsnEgGwEoz9BqPD")
			.header("Accept", "application/json")
			.asJson();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONObject myObj = jj.getBody().getObject();
	    System.out.println(myObj);
	    
	    
	    
	    if (myObj.get("suggestion").equals(null) | myObj.get("suggestion").equals(false)) {
	    	note.setText((String)myObj.get("original"));
	    }
	    else {
	    	note.setText((String)myObj.get("suggestion"));
	    }
		
		note.setLabel(request.getParameter("label"));
		note.setLocation(request.getParameter("loc"));
		note.setDateCreated(date);
		dao.updateNote(note);

		dao.close();
		return "notes";
	}
	
	@RequestMapping(value="updateUser", method=RequestMethod.PUT)
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
	
	@RequestMapping(value="verifyLogin", method=RequestMethod.GET)
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
