package controller.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import Services.CategoryService;

/**
 * Servlet implementation class UpdateCategoryServlet
 */
@WebServlet("/admin/update_category")
public class UpdateCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCategoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryService categoryServices = new CategoryService(request, response);
		categoryServices.updateCategory();
	}

}
