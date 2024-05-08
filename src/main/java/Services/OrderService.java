package Services;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import dao.OrderDAO;
import entity.Customer;
import entity.Order;
import entity.OrderDetails;
import entity.Product;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class OrderService {
	
	private OrderDAO orderDao;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public OrderService(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.orderDao = new OrderDAO();
	}

	public void listAllOrder() throws ServletException, IOException {
		listAllOrder(null);
	}
	
	public void listAllOrder(String message) throws ServletException, IOException {
		List<Order> listOrder = orderDao.listAll();
		
		if (message != null) {
			request.setAttribute("message", message);
		}
		
		request.setAttribute("listOrder", listOrder);
		
		String listPage = "order_list.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(listPage);
		dispatcher.forward(request, response);
		
	}

	public void viewOrderDetailForAdmin() throws ServletException, IOException {
		int orderId = Integer.parseInt(request.getParameter("id"));
		
		Order order = orderDao.get(orderId);
		request.setAttribute("order", order);
		
		String detailPage = "order_detail.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(detailPage);
		dispatcher.forward(request, response);		
	}

//	public void showCheckoutForm() throws ServletException, IOException {
//		HttpSession session = request.getSession();
//		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");
//		
//		// tax is 10% of subtotal
//		float tax = shoppingCart.getTotalAmount() * 0.1f;
//		
//		// shipping fee is 1.0 USD per copy
//		float shippingFee = shoppingCart.getTotalQuantity() * 1.0f;
//		
//		float total = shoppingCart.getTotalAmount() + tax + shippingFee;
//		
//		session.setAttribute("tax", tax);
//		session.setAttribute("shippingFee", shippingFee);
//		session.setAttribute("total", total);
//		
//		
//		String checkOutPage = "frontend/checkout.jsp";
//		RequestDispatcher dispatcher = request.getRequestDispatcher(checkOutPage);
//		dispatcher.forward(request, response);		
//	}

//	public void placeOrder() throws ServletException, IOException {
//		String paymentMethod = request.getParameter("paymentMethod");
//		Order order = readOrderInfo();
//		
//		if (paymentMethod.equals("paypal")) {
//			PaymentServices paymentServices = new PaymentServices(request, response);
//			request.getSession().setAttribute("order4Paypal", order);
//			paymentServices.authorizePayment(order);
//		} else {	// Cash on Delivery
//			placeOrderCOD(order);
//		}			
//	}
	
//	public Integer placeOrderPaypal(Payment payment) {
//		BookOrder order = (BookOrder) request.getSession().getAttribute("order4Paypal");
//		ItemList itemList = payment.getTransactions().get(0).getItemList();
//		ShippingAddress shippingAddress = itemList.getShippingAddress();
//		String shippingPhoneNumber = itemList.getShippingPhoneNumber();
//		
//		String recipientName = shippingAddress.getRecipientName();
//		String[] names = recipientName.split(" ");
//		
//		order.setFirstname(names[0]);
//		order.setLastname(names[1]);
//		order.setAddressLine1(shippingAddress.getLine1());
//		order.setAddressLine2(shippingAddress.getLine2());
//		order.setCity(shippingAddress.getCity());
//		order.setState(shippingAddress.getState());
//		order.setCountry(shippingAddress.getCountryCode());
//		order.setPhone(shippingPhoneNumber);
//		
//		return saveOrder(order);
//		
//	}
	
//	private Integer saveOrder(Order order) {
//		BookOrder savedOrder = orderDao.create(order);
//		
//		ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute("cart");
//		shoppingCart.clear();	
//		
//		return savedOrder.getOrderId();
//	}
	

//	private Order readOrderInfo() {
//		String paymentMethod = request.getParameter("paymentMethod");
//		String firstname = request.getParameter("firstname");
//		String lastname = request.getParameter("lastname");
//		String phone = request.getParameter("phone");
//		String address1 = request.getParameter("address1");
//		String address2 = request.getParameter("address2");
//		String city = request.getParameter("city");
//		String state = request.getParameter("state");
//		String zipcode = request.getParameter("zipcode");
//		String country = request.getParameter("country");		
//		
//		Order order = new Order();
//		order.setFirstname(firstname);
//		order.setLastname(lastname);
//		order.setPhone(phone);
//		order.setAddressLine1(address1);
//		order.setAddressLine2(address2);
//		order.setCity(city);
//		order.setState(state);
//		order.setCountry(country);
//		order.setZipcode(zipcode);
//		order.setPaymentMethod(paymentMethod);
//		
//		HttpSession session = request.getSession();
//		Customer customer = (Customer) session.getAttribute("loggedCustomer");
//		order.setCustomer(customer);
//		
//		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");
//		Map<Book, Integer> items = shoppingCart.getItems();
//		
//		Iterator<Book> iterator = items.keySet().iterator();
//		
//		Set<OrderDetail> orderDetails = new HashSet<>();
//		
//		while (iterator.hasNext()) {
//			Book book = iterator.next();
//			Integer quantity = items.get(book);
//			float subtotal = quantity * book.getPrice();
//			
//			OrderDetail orderDetail = new OrderDetail();
//			orderDetail.setBook(book);
//			orderDetail.setBookOrder(order);
//			orderDetail.setQuantity(quantity);
//			orderDetail.setSubtotal(subtotal);
//			
//			orderDetails.add(orderDetail);
//		}
//		
//		order.setOrderDetails(orderDetails);
//		
//		float tax = (Float) session.getAttribute("tax");
//		float shippingFee = (Float) session.getAttribute("shippingFee");
//		float total = (Float) session.getAttribute("total");
//		
//		order.setSubtotal(shoppingCart.getTotalAmount());
//		order.setTax(tax);
//		order.setShippingFee(shippingFee);
//		order.setTotal(total);		
//		
//		return order;
//	}
	
//	private void placeOrderCOD(Order order) throws ServletException, IOException {
//		saveOrder(order);
//		
//		String message = "Thank you. Your order has been received. "
//				+ "We will deliver your books within a few days.";
//		request.setAttribute("message", message);
//		request.setAttribute("pageTitle", "Order Completed");
//		
//		String messagePage = "frontend/message.jsp";
//		RequestDispatcher dispatcher = request.getRequestDispatcher(messagePage);
//		dispatcher.forward(request, response);	
//	}

	public void listOrderByCustomer() throws ServletException, IOException {
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");
		List<Order> listOrders = orderDao.listByCustomer(customer.getCustomerId());
		
		request.setAttribute("listOrders", listOrders);
		
		String historyPage = "frontend/order_list.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(historyPage);
		dispatcher.forward(request, response);		
	}

	public void showOrderDetailForCustomer() throws ServletException, IOException {
		int orderId = Integer.parseInt(request.getParameter("id"));
		
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");
		
		Order order = orderDao.get(orderId, customer.getCustomerId());
		request.setAttribute("order", order);
		
		String detailPage = "frontend/order_detail.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(detailPage);
		dispatcher.forward(request, response);			
	}

	public void showEditOrderForm() throws ServletException, IOException {
		Integer orderId = Integer.parseInt(request.getParameter("id"));
		
	
		HttpSession session = request.getSession();
		Object isPendingProduct = session.getAttribute("NewBookPendingToAddToOrder");
		
		if (isPendingProduct == null) {
			Order order = orderDao.get(orderId);
			session.setAttribute("order", order);
		} else {
			session.removeAttribute("NewProductPendingToAddToOrder");
		}
		
		
		String editPage = "order_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(editPage);
		dispatcher.forward(request, response);		
		
	}

	public void updateOrder() throws ServletException, IOException {
		HttpSession session = request.getSession();
		Order order = (Order) session.getAttribute("order");
		
		String name = request.getParameter("name");
		
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		
		float shippingFee = Float.parseFloat(request.getParameter("shippingFee"));
		float tax = Float.parseFloat(request.getParameter("tax"));
		
		String paymentMethod = request.getParameter("paymentMethod");
		String orderStatus = request.getParameter("orderStatus");
		
		order.setName(name);

		order.setPhone(phone);
		order.setAddress(address);
		order.setShippingFee(shippingFee);
		order.setTax(tax);
		order.setPaymentMethod(paymentMethod);
		order.setStatus(orderStatus);
		
		String[] arrayProductId = request.getParameterValues("productId");
		String[] arrayPrice = request.getParameterValues("price");
		String[] arrayQuantity = new String[arrayProductId.length];
		
		for (int i = 1; i <= arrayQuantity.length; i++) {
			arrayQuantity[i - 1] = request.getParameter("quantity" + i);
		}
		
		Set<OrderDetails> orderDetails = order.getOrderDetails();
		orderDetails.clear();
		
		float totalAmount = 0.0f;
		
		for (int i = 0; i < arrayProductId.length; i++) {
			int productId = Integer.parseInt(arrayProductId[i]);
			int quantity = Integer.parseInt(arrayQuantity[i]);
			float price = Float.parseFloat(arrayPrice[i]);
			
			float subtotal = price * quantity;
			
			OrderDetails orderDetail = new OrderDetails();
			orderDetail.setProduct(new Product(productId));
			orderDetail.setQuantity(quantity);
			orderDetail.setSubtotal(subtotal);
			orderDetail.setBookOrder(order);
			
			orderDetails.add(orderDetail);
			
			totalAmount += subtotal;
		}
		
		order.setSubtotal(totalAmount);
		totalAmount += shippingFee;
		totalAmount += tax;
		
		order.setTotal(totalAmount);
		
		orderDao.update(order);
		
		String message = "The order " + order.getOrderId() + " has been updated successfully";
		
		listAllOrder(message);
		
		
	}

	public void deleteOrder() throws ServletException, IOException {
		Integer orderId = Integer.parseInt(request.getParameter("id"));
		orderDao.delete(orderId);
		
		String message = "The order ID " + orderId + " has been deleted.";
		listAllOrder(message);
	}
}
