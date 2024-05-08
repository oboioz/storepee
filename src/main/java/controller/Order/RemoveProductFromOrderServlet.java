package controller.Order;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import entity.Order;
import entity.OrderDetails;

/**
 * Servlet implementation class RemoveProductFromOrderServlet
 */

@WebServlet("/admin/remove_product_from_order")
public class RemoveProductFromOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveProductFromOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int productId = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		Order order = (Order) session.getAttribute("order");

		Set<OrderDetails> orderDetails = order.getOrderDetails();
		Iterator<OrderDetails> iterator = orderDetails.iterator();
		
		while (iterator.hasNext()) {
			OrderDetails orderDetail = iterator.next();
			
			if (orderDetail.getProduct().getProductId() == productId) {
				float newTotal = order.getTotal() - orderDetail.getSubtotal();
				order.setTotal(newTotal);
				iterator.remove();
			}			
		}
		
		String editOrderFormPage = "order_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(editOrderFormPage);
		dispatcher.forward(request, response);
	}

}
