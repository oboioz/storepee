package Services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import dao.CategoryDAO;
import dao.ProductDAO;
import entity.Category;
import entity.Product;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

public class ProductService {
	private ProductDAO productDAO;
	private CategoryDAO categoryDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public ProductService(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
		productDAO = new ProductDAO();
		categoryDAO = new CategoryDAO();
	}

	public void listProducts() throws ServletException, IOException {
		listProducts(null);
	}
	
	public void listProducts(String message) throws ServletException, IOException {
		List<Product> listProducts = productDAO.listAll();
		request.setAttribute("listProducts", listProducts);
		
		if (message != null) {
			request.setAttribute("message", message);
		}
		
		String listPage = "product_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);
		
	}
	
	public void showProductNewForm() throws ServletException, IOException {
		List<Category> listCategory = categoryDAO.listAll();
		request.setAttribute("listCategory", listCategory);
		request.setAttribute("pageTitle", "Create New Product");
		
		String newPage = "product_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(newPage);
		requestDispatcher.forward(request, response);		
	}

	public void createProduct() throws ServletException, IOException {
		String name = request.getParameter("name");
		
		Product existProduct = productDAO.findByName(name);
		
		if (existProduct != null) {
			String message = "Could not create new book because the product '"
					+ name + "' already exists.";
			listProducts(message);
			return;
		}
		
		Product newProduct = new Product();
		readProductFields(newProduct);
		
		Product createdProduct = productDAO.create(newProduct);
		
		if (createdProduct.getProductId() > 0) {
			String message = "A new product has been created successfully.";
			listProducts(message);
		}
	}

	public void readProductFields(Product product) throws ServletException, IOException {
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		float price = Float.parseFloat(request.getParameter("price"));
				
		product.setName(name);
		product.setDescription(description);
		
		Integer categoryId = Integer.parseInt(request.getParameter("category"));
		Category category = categoryDAO.get(categoryId);
		product.setCategory(category);
		
		product.setPrice(price);
		
		Part part = request.getPart("productImage");
		
		if (part != null && part.getSize() > 0) {
			long size = part.getSize();
			byte[] imageBytes = new byte[(int) size];
			
			InputStream inputStream = part.getInputStream();
			inputStream.read(imageBytes);
			inputStream.close();
			
			product.setImage(imageBytes);
		}
		
	}
	
	public void editProduct() throws ServletException, IOException {
		Integer productId = Integer.parseInt(request.getParameter("id"));
		Product product = productDAO.get(productId);
		List<Category> listCategory = categoryDAO.listAll();
		
		request.setAttribute("product", product);
		request.setAttribute("listCategory", listCategory);
		
		String editPage = "product_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(editPage);
		requestDispatcher.forward(request, response);		
		
	}

	public void updateProduct() throws ServletException, IOException {
		Integer productId = Integer.parseInt(request.getParameter("productId"));
		String name = request.getParameter("name");
		
		Product existProduct = productDAO.get(productId);
		Product productByName = productDAO.findByName(name);
		
		if (productByName != null && !existProduct.equals(productByName)) {
			String message = "Could not update product because there's another product having same name.";
			listProducts(message);
			return;
		}
		
		readProductFields(existProduct);
		
		productDAO.update(existProduct);
		
		String message = "The product has been updated successfully.";
		listProducts(message);
	}

	public void deleteProduct() throws ServletException, IOException {
		Integer productId = Integer.parseInt(request.getParameter("id"));
		
		productDAO.delete(productId);
		
		String message = "The product has been deleted successfully.";
		listProducts(message);		
	}

	public void listProductsByCategory() throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("id"));
		List<Product> listProducts = productDAO.listByCategory(categoryId);
		Category category = categoryDAO.get(categoryId);
		
		request.setAttribute("listBooks", listProducts);
		request.setAttribute("category", category);
		
		String listPage = "frontend/products_list_by_category.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);		
	}

	public void viewProductDetail() throws ServletException, IOException {
		Integer productId = Integer.parseInt(request.getParameter("id"));
		Product product = productDAO.get(productId);
		
		request.setAttribute("product", product);

		String detailPage = "frontend/product_detail.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(detailPage);
		requestDispatcher.forward(request, response);
	}

	public void search() throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		List<Product> result = null; 
				
		if (keyword.equals("")) {
			result = productDAO.listAll();
		} else {
			result = productDAO.search(keyword);
		}
		
		request.setAttribute("keyword", keyword);
		request.setAttribute("result", result);
		
		String resultPage = "frontend/search_result.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(resultPage);
		requestDispatcher.forward(request, response);		
	}
}
