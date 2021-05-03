package cc.openhome.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cc.openhome.model.UserService;

@WebServlet(
    urlPatterns={"/del_photo"}, 
    initParams={
        @WebInitParam(name = "SHOW_PATH", value = "showPhoto")
    }
)
@ServletSecurity(
    @HttpConstraint(rolesAllowed = {"member"})
)
public class DelPhoto extends HttpServlet {    
	
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		processRequest(request,response);
	}
	
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	processRequest(request,response);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	String millis = request.getParameter("millis");
        String photoPath = request.getParameter("photoPath");
        
        if(millis != null) {
        	File file = new File("D:/workplace/Servlet&JSP/FinalProject/WebContent/Photos/" + photoPath + ".jpg");
            file.delete();
        	UserService userService = (UserService) getServletContext().getAttribute("userService");
            userService.deletePhoto(getUsername(request), millis);
        }
        
        request.getRequestDispatcher(getInitParameter("SHOW_PATH")).forward(request, response);
    }
    
    private String getUsername(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("login");
    }
}
