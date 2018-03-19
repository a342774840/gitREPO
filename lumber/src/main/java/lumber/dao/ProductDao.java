package lumber.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lumber.entity.Product;

/*
 * 用于处理产品的操作
 */
@Repository
@Transactional
public class ProductDao extends BaseDao {
	//数据库结果集的行映射器
	RowMapper<Product> rowMapper=new BeanPropertyRowMapper<Product>(Product.class);
	
	/*
	 * 查询产品的所有分类
	 */
	public List<String> productKinds()
	{
		String sql="select productKind from product group by productKind asc";
		List<String> kinds=jdbcTemplate.queryForList(sql, String.class);
		return kinds;
	}
	
	/*
	 * 查询所有 
	 */
	public List<Product> getAllProduct()
	{
		List<Product> products=null;
		String sql ="select * from product";
		products=jdbcTemplate.query(sql, rowMapper);
		return products;
	}
	
	
	/*
	 * 
	 */
	public List<Product> getAllProductWithName(String productName)
	{
		List<Product> products=null;
		String sql ="select * from product where productName like ? ";
		products=jdbcTemplate.query(sql, rowMapper,"%"+productName+"%");
		return products;
	}
	
	
	/*
	 * 查询具体的产品类别查询出所有的该类下的商品
	 */
	public List<Product> getProductsByKind(String productKind)
	{
		if(productKind==null)
			return null;
		
		List<Product> products=null;
		String sql ="select * from product where productKind like ?";
		products=jdbcTemplate.query(sql, rowMapper, "%"+productKind+"%");
		return products;
	}
	
	public List<Product> getProductsByKindAndProductName(String productKind,String productName)
	{
		if(productKind==null||productName==null)
			return null;
		
		List<Product> products=null;
		String sql ="select * from product where productKind=? and "
				+ "productName like ?";
		products=jdbcTemplate.query(sql, rowMapper, productKind,"%"+productName+"%");
		return products;
	}
	
	
	/*
	 * 通过商品名字模糊查询某些商品
	 */
	public List<Product> getProductsLikeName(String productName)
	{
		if(productName==null)
			return null;
		List<Product> products=null;
		String sql ="select * from product where productName like ?";
		products=jdbcTemplate.query(sql, rowMapper,"%"+productName+"%");
		return products;
	}
	
	
	/*
	 * 按照产品的销量来降排 
	 */
	public List<Product> getProductsBySalesDesc()
	{
		List<Product> products=null;
		String sql ="select * from product order by productSaledSum desc";
		products=jdbcTemplate.query(sql, rowMapper);
		return products;
	}
	
	/*
	 * 按照产品的销量来降排 加上名字
	 */
	public List<Product> getProductsBySalesDescWithproductKind(String productKind)
	{
		List<Product> products=null;
		String sql ="select * from product "
				+ "where productKind like ? order by productSaledSum desc";
		products=jdbcTemplate.query(sql, rowMapper,"%"+productKind+"%");
		return products;
	}
	
	
	/*
	 * 按照上线时间来查询
	 */
	public List<Product> getProductsByOnlineTime()
	{
		String sql="select * from product order by onlineTime desc";
		List<Product> products=jdbcTemplate.query(sql, rowMapper);
		return products;
	}
	
	
	/*
	 * 按照上线时间来查询  加上产品名字
	 */
	public List<Product> getProductsByOnlineTime(String  productKind)
	{
		String sql="select * from product  where productKind like ? order by onlineTime desc";
		List<Product> products=jdbcTemplate.query(sql, rowMapper,"%"+productKind+"%");
		return products;
	}
	
	
	
	/*
	 * 按照价格排序来查询产品
	 */
	public List<Product> getProductsByASC(boolean asc)
	{
		String sql="";
		if(asc)
		{
			sql="select * from product order by productPrice asc";
			
		}else{
			sql="select * from product order by productPrice desc";
		}
		List<Product> products=jdbcTemplate.query(sql, rowMapper);
		return products;
	}
	
	
	
	/*
	 * 按照价格排序来查询产品 加上名字
	 */
	public List<Product> getProductsByASC(String productKind,boolean asc)
	{
		String sql="";
		if(asc)
		{
			sql="select * from product where productKind like ? order by productPrice asc";
			
		}else{
			sql="select * from product where productKind like ? order by productPrice desc";
		}
		List<Product> products=jdbcTemplate.query(sql, rowMapper,"%"+productKind+"%");
		return products;
	}
	
	
	//查询出相关的图片路径
	public List<String> imageUrls(String productId,Integer up)
	{
		if(productId==null||up==null)
			return null;
		String sql="select imageUrl from proimage where productId=? and upImage=?";
		
		List<String> imageurls=jdbcTemplate.queryForList(sql, String.class,productId,up);
		
		return imageurls;
		
	}
	
	/*
	 * 根据某一产品的ID查询出该产品的相关信息
	 */
	public Product getProductById(String userId)
	{
		if(userId==null)
			return null;
		String sql="select * from product where productId=?";
		Product product=null;
		try {
			product = jdbcTemplate.queryForObject(sql, rowMapper, userId);
		} catch (DataAccessException e) {
			return null;
		}
		return product;
	}
	
	public boolean updateProduct(String productId,int productNum)
	{
		if(productId==null)
			return false;
		String updateProduct ="update product set "
				+ "productSaledSum=productSaledSum+? where productId=?";
		int flag=jdbcTemplate.update(updateProduct,productNum, productId);
		return flag!=0?true:false;
	}
	
	
	
}
