package entity;


import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

public class Product implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer productId;
	private Category category;
	private String name;
	private String description;
	private byte[] image;
	private String base64Image;
	private float price;
	private Set<OrderDetails> orderDetails = new HashSet<OrderDetails>(0);
	
	
	
	
	public Product() {
		super();
	}
	public Product(Integer productId) {
		super();
		this.productId = productId;
	}
	public Product(Integer productId, Category category, String name, String description, byte[] image,
			String base64Image, float price) {
		super();
		this.productId = productId;
		this.category = category;
		this.name = name;
		this.description = description;
		this.image = image;
		this.base64Image = base64Image;
		this.price = price;
	}
	public Product(Integer productId, Category category, String name, String description, byte[] image,
			String base64Image, float price, Set<OrderDetails> orderDetails) {
		super();
		this.productId = productId;
		this.category = category;
		this.name = name;
		this.description = description;
		this.image = image;
		this.base64Image = base64Image;
		this.price = price;
		this.orderDetails = orderDetails;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id", unique = true, nullable = false)
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", nullable = false)
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	@Column(name = "name", unique = true, nullable = false, length = 128)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "description", nullable = false, length = 16777215)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "image", nullable = false)
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	@Transient
	public String getBase64Image() {
		this.base64Image = Base64.getEncoder().encodeToString(this.image);
		return this.base64Image;
	}
	
	@Transient
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	
	@Column(name = "price", nullable = false, precision = 12, scale = 0)
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	public Set<OrderDetails> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(Set<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}
}
