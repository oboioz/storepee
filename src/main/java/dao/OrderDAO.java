package dao;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Order;

public class OrderDAO extends JpaDAO<Order> implements GenericDAO<Order>{
	
	@Override
	public Order create(Order order) {
		order.setOrderDate(LocalDate.now());		
		order.setStatus("Processing");
		
		return super.create(order);
	}

	@Override
	public Order update(Order order) {
		return super.update(order);
	}

	@Override
	public Order get(Object orderId) {
		return super.find(Order.class, orderId);
	}

	public Order get(Integer orderId, Integer customerId) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("orderId", orderId);
		parameters.put("customerId", customerId);
		
		List<Order> result = super.findWithNamedQuery("Order.findByIdAndCustomer", parameters );
		
		if (!result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}
	
	@Override
	public void delete(Object orderId) {
		super.delete(Order.class, orderId);		
	}

	@Override
	public List<Order> listAll() {
		return super.findWithNamedQuery("Order.findAll");
	}

	@Override
	public long count() {	
		return super.countWithNamedQuery("Order.countAll");
	}
	
	public List<Order> listByCustomer(Integer customerId) {
		return super.findWithNamedQuery("Order.findByCustomer", 
				"customerId", customerId);
	}
	
	public List<Order> listMostRecentSales() {
		return super.findWithNamedQuery("Order.findAll", 0, 3);
	}

}
