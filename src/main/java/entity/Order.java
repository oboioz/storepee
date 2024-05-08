package entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

public class Order implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer orderId;
	private Customer customer;
	private LocalDate orderDate;
	private String address;
	private String name;
	private String phone;
	private String paymentMethod;
	
	private float total;
	private float subtotal;
	private float shippingFee;
	private float tax;
	
	private String status;
	
	private Set<OrderDetails> orderDetails = new HashSet<OrderDetails>(0);

	public Order(Integer orderId, Customer customer, LocalDate orderDate, String address, String name, String phone,
			String paymentMethod, float total, float subtotal, float shippingFee, float tax, String status) {
		super();
		this.orderId = orderId;
		this.customer = customer;
		this.orderDate = orderDate;
		this.address = address;
		this.name = name;
		this.phone = phone;
		this.paymentMethod = paymentMethod;
		this.total = total;
		this.subtotal = subtotal;
		this.shippingFee = shippingFee;
		this.tax = tax;
		this.status = status;
	}

	public Order(Integer orderId, Customer customer, LocalDate orderDate, String address, String name, String phone,
			String paymentMethod, float total, float subtotal, float shippingFee, float tax, String status,
			Set<OrderDetails> orderDetails) {
		super();
		this.orderId = orderId;
		this.customer = customer;
		this.orderDate = orderDate;
		this.address = address;
		this.name = name;
		this.phone = phone;
		this.paymentMethod = paymentMethod;
		this.total = total;
		this.subtotal = subtotal;
		this.shippingFee = shippingFee;
		this.tax = tax;
		this.status = status;
		this.orderDetails = orderDetails;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "order_id", unique = true, nullable = false)
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Column(name = "order_date", nullable = false, length = 19)
	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	@Column(name = "address", nullable = false, length = 256)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "name", nullable = false, length = 30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "phone", nullable = false, length = 15)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "payment_method", nullable = false, length = 20)
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Column(name = "total", nullable = false, precision = 12, scale = 0)
	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	@Column(name = "subtotal", nullable = false, precision = 12, scale = 0)
	public float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	@Column(name = "shipping_fee", nullable = false, precision = 12, scale = 0)	
	public float getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(float shippingFee) {
		this.shippingFee = shippingFee;
	}

	@Column(name = "tax", nullable = false, precision = 12, scale = 0)
	public float getTax() {
		return tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "bookOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<OrderDetails> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	@Transient
	public int getProductCopies() {
		int total = 0;
		
		for (OrderDetails orderDetail : orderDetails) {
			total += orderDetail.getQuantity();
		}
		
		return total;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
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
		Order other = (Order) obj;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		return true;
	}
	
	

}
