package cc.openhome.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cc.openhome.model.Message;
import cc.openhome.model.Photo;
import cc.openhome.model.UserService;

@WebServlet(
    urlPatterns={"/showPhoto"},
    initParams={
    @WebInitParam(name = "SHOW_PATH", value = "/WEB-INF/jsp/showPhoto.jsp")
    }
)
@ServletSecurity(
    @HttpConstraint(rolesAllowed = {"member"})
)
public class ShowPhoto extends HttpServlet {
    
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response) 
                    throws ServletException, IOException {
    	UserService userService = (UserService) getServletContext().getAttribute("userService");
    	Message message = userService.message(getUsername(request), request.getParameter("title"));
        List<Photo> photos = userService.photos(getUsername(request), request.getParameter("title"));
        
        request.setAttribute("message", message);
        request.setAttribute("photos", photos);
        request.getRequestDispatcher(getInitParameter("SHOW_PATH")).forward(request, response);
    }

    private String getUsername(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("login");
    }
}
