package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import dao.CustomerDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import dao.UserDAO;
import entity.Order;

/**
 * Servlet implementation class AdminHomeServlet
 */

@WebServlet("/admin/")
public class AdminHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminHomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		UserDAO userDao = new UserDAO();
		OrderDAO orderDao = new OrderDAO();
		ProductDAO productDao = new ProductDAO();
		CustomerDAO customerDao = new CustomerDAO();
		
		List<Order> listMostRecentSales = orderDao.listMostRecentSales();
		
		long totalUsers = userDao.count();
		long totalProducts = productDao.count();
		long totalCustomers = customerDao.count();
		long totalOrders = orderDao.count();
		
		request.setAttribute("listMostRecentSales", listMostRecentSales);
		
		request.setAttribute("totalUsers", totalUsers);
		request.setAttribute("totalBooks", totalProducts);
		request.setAttribute("totalCustomers", totalCustomers);
		request.setAttribute("totalOrders", totalOrders);
		
		String homepage = "index.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);
	}

}
