package entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class OrderDetails implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OrderDetailId id = new OrderDetailId();
	private Product product;
	private Order order;
	private int quantity;
	private float subtotal;
	
	public OrderDetails() {
	}

	public OrderDetails(OrderDetailId id) {
		this.id = id;
	}

	public OrderDetails(OrderDetailId id, Product product, Order order, int quantity, float subtotal) {
		this.id = id;
		this.product = product;
		this.order = order;
		this.quantity = quantity;
		this.subtotal = subtotal;
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "orderId", column = @Column(name = "order_id", nullable = false)),
			@AttributeOverride(name = "productId", column = @Column(name = "product_id", nullable = false))})
	public OrderDetailId getId() {
		return this.id;
	}

	public void setId(OrderDetailId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", insertable = false, updatable = false, nullable = false)
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
		this.id.setProduct(product);
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", insertable = false, updatable = false, nullable = false)
	public Order getOrder() {
		return this.order;
	}

	public void setBookOrder(Order order) {
		this.order = order;
		this.id.setOrder(order);
	}
	
	@Column(name = "quantity", nullable = false)
	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Column(name = "subtotal", nullable = false, precision = 12, scale = 0)
	public float getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}
	
	
	
}
