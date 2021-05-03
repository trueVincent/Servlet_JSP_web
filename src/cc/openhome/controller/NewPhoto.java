package cc.openhome.controller;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import cc.openhome.model.Message;
import cc.openhome.model.Photo;
import cc.openhome.model.UserService;

@MultipartConfig
@WebServlet(
    urlPatterns={"/new_photo"}, 
    initParams={
        @WebInitParam(name = "MEMBER_PATH", value = "member"),
        @WebInitParam(name = "FORM_PATH", value = "/WEB-INF/jsp/newPhoto.jsp")
    }
)
@ServletSecurity(
    @HttpConstraint(rolesAllowed = {"member"})
)
//1.圖片存到photo資料夾 2.資料庫存圖片序號, 分類
public class NewPhoto extends HttpServlet {
	
	protected void doGet(
            HttpServletRequest request, HttpServletResponse response) 
                            throws ServletException, IOException {
		request.getRequestDispatcher(getInitParameter("FORM_PATH")).forward(request, response);
	}
	
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response) 
                            throws ServletException, IOException {
          
    	request.setCharacterEncoding("UTF-8");
    	String title = request.getParameter("title");
    	String message = request.getParameter("message");
    	UserService userService = (UserService) getServletContext().getAttribute("userService");
    	
    	Message message2 = userService.message(getUsername(request), title);
    	if(message2 !=null) {
    		request.setAttribute("errors", Arrays.asList("Title已存在"));
            request.getRequestDispatcher(getInitParameter("FORM_PATH"))
                   .forward(request, response);
    	} else {
    		userService.addMessage(getUsername(request), title, message);
    		
    		for(Part part:request.getParts()) {
        		if(part.getName().startsWith("photo")) {
        			try {
                		userService.addPhoto(getUsername(request), title);
                		String filename = userService.newestPhotos(1).get(0).getPhotoPath();
                		part.write("D:/workplace/Servlet&JSP/FinalProject/WebContent/Photos/" + filename +".jpg");
                	} catch(IOException e) {
                		throw new UncheckedIOException(e);
                	}
        		}
        	}
        	
        	response.sendRedirect(getInitParameter("MEMBER_PATH"));
    	}
    }
    
    private String getUsername(HttpServletRequest request) {
        return  (String) request.getSession().getAttribute("login");
    }
}
