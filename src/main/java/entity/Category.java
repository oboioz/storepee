package entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

public class Category implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer categoryId;
	private String name;
	private Set<Product> products = new HashSet<Product>(0);
	
	public Category() {
	}

	public Category(String name) {
		this.name = name;
	}

	public Category(String name, Set<Product> products) {
		this.name = name;
		this.products = products;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "category_id", unique = true, nullable = false)
	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "name", nullable = false, length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	public Set<Product> getProducts() {
		return this.products;
	}

	public void setBooks(Set<Product> products) {
		this.products = products;
	}

}
