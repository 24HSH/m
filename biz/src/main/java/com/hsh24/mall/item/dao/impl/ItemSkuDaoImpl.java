package com.hsh24.mall.item.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.hsh24.mall.api.item.bo.ItemSku;
import com.hsh24.mall.framework.dao.impl.BaseDaoImpl;
import com.hsh24.mall.item.dao.IItemSkuDao;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemSkuDaoImpl extends BaseDaoImpl implements IItemSkuDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemSku> getItemSkuList(ItemSku sku) {
		return (List<ItemSku>) getSqlMapClientTemplate().queryForList("item.sku.getItemSkuList", sku);
	}

	@Override
	public int updateItemSku(final List<ItemSku> skuList, final String modifyUser) {
		return getSqlMapClientTemplate().execute(new SqlMapClientCallback<Integer>() {
			public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				int count = 0;
				executor.startBatch();
				for (ItemSku sku : skuList) {
					sku.setModifyUser(modifyUser);
					executor.update("item.sku.updateItemSku", sku);
					count++;
				}
				executor.executeBatch();

				return count;
			}
		});
	}

}
