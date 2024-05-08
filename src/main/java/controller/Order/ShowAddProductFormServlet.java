package controller.Order;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import dao.ProductDAO;
import entity.Product;

/**
 * Servlet implementation class ShowAddProductFormServlet
 */
@WebServlet("/admin/add_product_form")
public class ShowAddProductFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowAddProductFormServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductDAO productDao = new ProductDAO();
		List<Product> listProduct = productDao.listAll();
		request.setAttribute("listProduct", listProduct);
		
		String addFormPage = "add_product_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(addFormPage);
		dispatcher.forward(request, response);
		
	}

}
