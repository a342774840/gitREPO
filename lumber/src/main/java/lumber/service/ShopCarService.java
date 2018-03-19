package lumber.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * 主要用于服务产品添加到购物车的相关操作，当涉及需要更新数据表时，保证操作的原子化
 */

@Service
@Transactional
public class ShopCarService {
	/*
	 * 产品购买时需要更新
	 */
}
