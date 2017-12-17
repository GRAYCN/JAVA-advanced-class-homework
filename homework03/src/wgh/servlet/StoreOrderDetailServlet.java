package wgh.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import wgh.dao.OrderDetailDao;
import wgh.model.OrderDetail;

@WebServlet("/storeOrderDetailServlet")
public class StoreOrderDetailServlet extends HttpServlet {

	@Autowired
	private OrderDetailDao dao;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		dao = new OrderDetailDao();
		String orderId = request.getParameter("orderId");
		String bookId = request.getParameter("bookId");
		String orderQuantity = request.getParameter("orderQuantity");

		OrderDetail orderDetail = new OrderDetail(Integer.parseInt(orderId), Integer.parseInt(bookId),
				Integer.parseInt(orderQuantity));
		
		dao.insertOrderDetail(orderDetail);
	}

}
