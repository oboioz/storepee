package Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import entity.Customer;
import entity.Order;
import entity.OrderDetails;
import entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PaymentService {
	private static final String CLIENT_ID = "AYyvdakaZnuzu1QYQA3w7CbwOcABW13Bh8YBZD7Kq6dz_juwpSk7sPkRB8VWiTlUD9ITUzkmsYuSS-z5";
	private static final String CLIENT_SECRET = "EFsHteOJowYuKVNobC2B38KE7sUuDqDJc1JcrcL11p_CBqGqV8nFM1a8G9M4cbEMtcbHBDlkKIEDEuJu";
	
	private String mode = "live";
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public PaymentService(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}
	
	public void authorizePayment(Order order) throws ServletException, IOException {
		Payer payer = getPayerInformation(order);
		RedirectUrls redirectUrls = getRedirectURLs();		
			
		List<Transaction> transactions = getTransactionInformation(order);
		
		Payment requestPayment = new Payment();
		requestPayment.setPayer(payer)
					  .setRedirectUrls(redirectUrls)
					  .setIntent("authorize")
					  .setTransactions(transactions);
		
		System.out.println("====== REQUEST PAYMENT: ======");
		System.out.println(requestPayment);		
		
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, mode);
		
		try {
			Payment authorizedPayment = requestPayment.create(apiContext);
			System.out.println("====== AUTHORIZED PAYMENT: ======");
			System.out.println(authorizedPayment);
			
			String approvalURL = getApprovalURL(authorizedPayment);
			
			response.sendRedirect(approvalURL);
			
		} catch (PayPalRESTException e) {
			e.printStackTrace();
			throw new ServletException("Error in authorizing payment.");
		}
		
		
		// get approval link
		
		// redirect to Paypal's payment page
	}
	
	private String getApprovalURL(Payment authorizedPayment) {
		String approvalURL = null;
		
		List<Links> links = authorizedPayment.getLinks();
		
		for (Links link : links) {
			if (link.getRel().equalsIgnoreCase("approval_url")) {
				approvalURL = link.getHref();
				break;
			}
		}
		
		return approvalURL;
	}

	private List<Transaction> getTransactionInformation(Order order) {
		Transaction transaction = new Transaction();
		transaction.setDescription("Books ordered on Evergreen Books");
		Amount amount = getAmountDetails(order);
		transaction.setAmount(amount);
		
		ItemList itemList = new ItemList();
		ShippingAddress shippingAddress = getRecipientInformation(order);
		itemList.setShippingAddress(shippingAddress);
		
		List<Item> paypalItems = new ArrayList<>();
		Iterator<OrderDetails> iterator = order.getOrderDetails().iterator();
		
		while (iterator.hasNext()) {
			OrderDetails orderDetail = iterator.next();
			Product product = orderDetail.getProduct();
			Integer quantity = orderDetail.getQuantity();
			
			Item paypalItem = new Item();
			paypalItem.setCurrency("USD")
					  .setName(product.getName())
					  .setQuantity(String.valueOf(quantity))
					  .setPrice(String.format("%.2f", product.getPrice()));
			
			paypalItems.add(paypalItem);			
		}
		
		itemList.setItems(paypalItems);
		transaction.setItemList(itemList);
		
		List<Transaction> listTransaction = new ArrayList<>();
		listTransaction.add(transaction);
		
		return listTransaction;
	}
	
	
	private ShippingAddress getRecipientInformation(Order order) {
		ShippingAddress shippingAddress = new ShippingAddress();
		String recipientName = order.getName();
		shippingAddress.setRecipientName(recipientName)
					   .setPhone(order.getPhone())
					   .setLine1(order.getAddress());
		
		return shippingAddress;
	}
	
	private Payer getPayerInformation(Order order) {
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
				
		Customer customer = order.getCustomer();
		
		PayerInfo payerInfo = new PayerInfo();
		payerInfo.setFirstName(customer.getName());
		payerInfo.setEmail(customer.getEmail());
		payer.setPayerInfo(payerInfo);
		
		return payer;
	}
	
	private RedirectUrls getRedirectURLs() {
		String requestURL = request.getRequestURL().toString();
		String requestURI = request.getRequestURI();
		String baseURL = requestURL.replace(requestURI, "").concat(request.getContextPath());
		
		RedirectUrls redirectUrls = new RedirectUrls();
		String cancelUrl = baseURL.concat("/view_cart");
		String returnUrl = baseURL.concat("/review_payment");
		
		System.out.println("Return URL: " + returnUrl);
		System.out.println("Cancel URL: " + cancelUrl);
		
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(returnUrl);
		
		return redirectUrls;
	}
	
	private Amount getAmountDetails(Order order) {
		Details details = new Details();
		details.setShipping(String.format("%.2f", order.getShippingFee()));
		details.setTax(String.format("%.2f", order.getTax()));
		details.setSubtotal(String.format("%.2f", order.getSubtotal()));
		
		Amount amount =  new Amount();
		amount.setCurrency("USD");
		amount.setDetails(details);
		amount.setTotal(String.format("%.2f", order.getTotal()));
		
		return amount;
	}

	public void reviewPayment() throws ServletException {
		String paymentId = request.getParameter("paymentId");
		String payerId = request.getParameter("PayerID");
		
		if (paymentId == null || payerId == null) {
			throw new ServletException("Error in displaying payment review");
		}
		
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, mode);
		
		try {
			Payment payment = Payment.get(apiContext, paymentId);
			
			PayerInfo payerInfo = payment.getPayer().getPayerInfo();
			Transaction transaction = payment.getTransactions().get(0);
			ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();
			
			request.setAttribute("payer", payerInfo);
			request.setAttribute("recipient", shippingAddress);
			request.setAttribute("transaction", transaction);
			
			String reviewPage = "frontend/review_payment.jsp?paymentId=" + paymentId + "&PayerID=" + payerId;
			request.getRequestDispatcher(reviewPage).forward(request, response);
			
		} catch (PayPalRESTException | IOException e) {
			e.printStackTrace();
			throw new ServletException("Error in getting payment details from PayPal.");
		}
	}

	public Payment executePayment() throws PayPalRESTException {
		String paymentId = request.getParameter("paymentId");
		String payerId = request.getParameter("PayerID");
		
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);
		
		Payment payment = new Payment().setId(paymentId);
		
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, mode);
		
		return payment.execute(apiContext, paymentExecution);
		
	}

}
