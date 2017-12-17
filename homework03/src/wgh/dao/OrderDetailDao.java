package wgh.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import wgh.model.OrderDetail;

@Repository
public class OrderDetailDao {
	// 自动注入
	@Autowired
	private JdbcTemplate jdbcTemplate = null;

	@Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// 返回所有的订单详情信息,返回接口是一个数组
	public OrderDetail[] findAllBookInfos() {
		String sql = "select * from OrderDetail";
		List<OrderDetail> objs = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(OrderDetail.class));
		return objs.toArray(new OrderDetail[0]);
	}

	public void insertOrderDetail(OrderDetail orderDetail) {
		// TODO Auto-generated method stub
//		jdbcTemplate = new JdbcTemplate();
		String sql = "INSERT INTO dbo.OrderDetail( orderId, bookId, orderQuantity ) values(?,?,?)";
		jdbcTemplate.update(sql, orderDetail.getOrderId(), orderDetail.getBookId(),
				orderDetail.getOrderQuantity());
		// if (idRow == null || idRow.next() == false)
		// return -1;
		// return idRow.getInt(1);
	}

}
