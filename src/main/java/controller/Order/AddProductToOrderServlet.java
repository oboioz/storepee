package controller.Order;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import dao.ProductDAO;
import entity.Order;
import entity.OrderDetails;
import entity.Product;

/**
 * Servlet implementation class AddProductToOrderServlet
 */

@WebServlet("/admin/add_product_to_order")
public class AddProductToOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddProductToOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int productId = Integer.parseInt(request.getParameter("productId"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		
		ProductDAO productDao = new ProductDAO();
		Product product = productDao.get(productId);
		
		HttpSession session = request.getSession();
		Order order = (Order) session.getAttribute("order");
		
		float subtotal = quantity * product.getPrice();
		
		OrderDetails orderDetail = new OrderDetails();
		orderDetail.setProduct(product);
		orderDetail.setQuantity(quantity);
		orderDetail.setSubtotal(subtotal);
		
		float newTotal = order.getTotal() + subtotal;
		order.setTotal(newTotal);
		
		order.getOrderDetails().add(orderDetail);
		
		request.setAttribute("product", product);
		session.setAttribute("NewBookPendingToAddToOrder", true);
		
		String resultPage = "add_product_result.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
	}

}
