package wgh.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import wgh.model.OrderInfo;
@Repository
public class OrderInfoDao {
	//自动注入
	@Autowired
	private JdbcTemplate jdbcTemplate = null;
	
	@Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate=jdbcTemplate;
	}
	
	//返回所有的订单信息,返回接口是一个数组
	public OrderInfo[] findAllOrderInfos(){
		String sql = "select * from OrderInfo";
		List<OrderInfo> objs = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(OrderInfo.class));
		return objs.toArray(new OrderInfo[0]);
	}
	
	//插入一条订单信息,返回接口是插入的主键
	public Integer insertOrderInfo(OrderInfo orderInfo){
		String sql = "INSERT INTO dbo.OrderInfo ( orderTime, orderDiscount ) OUTPUT INSERTED.orderId values(?,?)";
		SqlRowSet idRow = jdbcTemplate.queryForRowSet(sql, orderInfo.getOrderTime(),orderInfo.getOrderDiscount());
		if (idRow == null || idRow.next() == false)
		     return -1;
		return idRow.getInt(1);

		
	}
	
}
