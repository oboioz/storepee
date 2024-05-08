package dao;

import java.util.List;

import entity.Product;

public class ProductDAO extends JpaDAO<Product> implements GenericDAO<Product>{
	
	public ProductDAO() {
	}

	@Override
	public Product create(Product product) {
		return super.create(product);
	}

	@Override
	public Product update(Product product) {
		return super.update(product);
	}

	@Override
	public Product get(Object productId) {
		return super.find(Product.class, productId);
	}

	@Override
	public void delete(Object productId) {
		super.delete(Product.class, productId);
	}

	@Override
	public List<Product> listAll() {		
		return super.findWithNamedQuery("Product.findAll");
	}

	public Product findByName(String name) {
		List<Product> result = super.findWithNamedQuery("Product.findByName", "name", name);
		
		if (!result.isEmpty()) {
			return result.get(0);
		}
		
		return null;
	}
	
	public List<Product> listByCategory(int categoryId) {
		return super.findWithNamedQuery("Product.findByCategory", "catId", categoryId);
	}
	
	public List<Product> search(String keyword) {
		return super.findWithNamedQuery("Product.search", "keyword", keyword);
	}
	
	public List<Product> listNewProducts() {		
		return super.findWithNamedQuery("Product.listNew", 0, 4);
	}
	
	@Override
	public long count() {
		return super.countWithNamedQuery("Product.countAll");
	}
	
	public long countByCategory(int categoryId) {
		return super.countWithNamedQuery("Product.countByCategory", "catId", categoryId);
	}

	public List<Product> listBestSellingProduct() {
		return super.findWithNamedQuery("OrderDetail.bestSelling", 0, 4);
	}

}
