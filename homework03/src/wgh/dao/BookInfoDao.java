package wgh.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import wgh.util.DbUtils;
import wgh.model.BookInfo;

@Repository
public class BookInfoDao {
	// 自动注入
	@Autowired
	private JdbcTemplate jdbcTemplate = null;

	@Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// 返回所有的图书信息,返回接口是一个数组
	public BookInfo[] findAllBooksInfos() {
		String sql = "select * from BookInfo";
		List<BookInfo> objs = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(BookInfo.class));
		return objs.toArray(new BookInfo[0]);
	}

	// 根据bookId查询特定某一行的数据信息
	public BookInfo findBookById(int bookId) {
		String sql = "select * from BookInfo where bookId=?";
		List<BookInfo> objs = jdbcTemplate.query(sql, new Object[] { bookId },
				BeanPropertyRowMapper.newInstance(BookInfo.class));
		if (objs.size() >= 1)
			return objs.get(0);
		else
			return null;
	}

	public Integer countRows(){
		String sql = "select count(*) as countRows from BookInfo ";
		int rs = jdbcTemplate.queryForObject(sql,Integer.class);
		return rs;
	}
	
	public BookInfo[] findPagedBookinfosByPageindex(int pageIndex){
		String sql = "select top " + DbUtils.PAGE_SIZE + " * from BookInfo where bookId not in";
		String subSql = " (select top " + pageIndex * DbUtils.PAGE_SIZE + " bookId from BookInfo order by bookId)";
		sql = sql + subSql + " order by bookId ";
		List<BookInfo> objs = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(BookInfo.class));
		return objs.toArray(new BookInfo[0]);
	}
	
}
