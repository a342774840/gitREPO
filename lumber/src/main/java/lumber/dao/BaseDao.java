package lumber.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/*
 * 主要用于被继承 统一注入JdbcTemplate
 */
@Repository
public class BaseDao {
	@Autowired
	public  JdbcTemplate jdbcTemplate;
	
}
