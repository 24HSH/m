package com.hsh24.mall.item.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.hsh24.mall.api.item.bo.Item;
import com.hsh24.mall.framework.dao.impl.BaseDaoImpl;
import com.hsh24.mall.item.dao.IItemDao;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemDaoImpl extends BaseDaoImpl implements IItemDao {

	@Override
	public int getItemCount(Item item) {
		return (Integer) getSqlMapClientTemplate().queryForObject("item.getItemCount", item);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> getItemList(Item item) {
		return (List<Item>) getSqlMapClientTemplate().queryForList("item.getItemList", item);
	}

	@Override
	public Item getItem(Item item) {
		return (Item) getSqlMapClientTemplate().queryForObject("item.getItem", item);
	}

	@Override
	public int updateItem(Long shopId, String[] itemId, String modifyUser) {
		Item item = new Item();
		item.setShopId(shopId);
		item.setCodes(itemId);
		item.setModifyUser(modifyUser);

		return getSqlMapClientTemplate().update("item.updateItem1", item);
	}

	@Override
	public int updateItem(final Long shopId, final List<Item> itemList, final String modifyUser) {
		return getSqlMapClientTemplate().execute(new SqlMapClientCallback<Integer>() {
			public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				int count = 0;
				executor.startBatch();
				for (Item item : itemList) {
					item.setShopId(shopId);
					item.setModifyUser(modifyUser);
					executor.insert("item.updateItem2", item);
					count++;
				}
				executor.executeBatch();

				return count;
			}
		});
	}

}
