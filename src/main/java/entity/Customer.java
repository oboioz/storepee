package entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

public class Customer implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer customerId;
	private String email;
	private String name;
	private String address;
	private String phone;
	private String password;
	private Set<Order> Orders = new HashSet<Order>(0);
	
	
	public Customer() {
	}

	public Customer(String email, String name, String address, String phone, String password) {
		this.email = email;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.password = password;
	}

	public Customer(String email, String name, String address, String phone, String password, Set<Order> Orders) {
		this(email, name, address, phone, password);
		this.Orders = Orders;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "customer_id", unique = true, nullable = false)
	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Column(name = "email", unique = true, nullable = false, length = 64)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "name", nullable = false, length = 60)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	@Column(name = "address", nullable = false, length = 128)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "phone", nullable = false, length = 15)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "password", nullable = false, length = 16)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	public Set<Order> getOrders() {
		return this.Orders;
	}

	public void setBookOrders(Set<Order> Orders) {
		this.Orders = Orders;
	}
}
