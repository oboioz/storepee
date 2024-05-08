package controller.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import Services.CategoryService;

/**
 * Servlet implementation class CreateCategoryServlet
 */
@WebServlet("/admin/create_category")
public class CreateCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateCategoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryService categoryServices = new CategoryService(request, response);
		categoryServices.createCategory();
	}

}
