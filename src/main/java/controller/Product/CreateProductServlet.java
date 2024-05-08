package controller.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import Services.ProductService;

/**
 * Servlet implementation class CreateProductServlet
 */
@WebServlet("/admin/create_product")
public class CreateProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService productServices = new ProductService(request, response);
		productServices.createProduct();
	}

}
