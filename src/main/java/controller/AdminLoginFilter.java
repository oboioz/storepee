package controller;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet Filter implementation class AdminLoginFilter
 */

@WebFilter("/admin/*")
public class AdminLoginFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public AdminLoginFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(false);
		
		boolean loggedIn = session != null && session.getAttribute("useremail") != null;
		String loginURI = httpRequest.getContextPath() + "/admin/login";
		boolean loginRequest = httpRequest.getRequestURI().equals(loginURI);
		boolean loginPage = httpRequest.getRequestURI().endsWith("login.jsp");
		
		if (loggedIn && (loginRequest || loginPage)) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/");
			dispatcher.forward(request, response);			
			
		} else if (loggedIn || loginRequest) {
			System.out.println("user logged in");
			chain.doFilter(request, response);	
		} else {
			System.out.println("user not logged in");
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
			
		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
