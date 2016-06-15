package com.hsh24.mall.item.dao.impl;

import java.util.List;

import com.hsh24.mall.api.item.bo.ItemFile;
import com.hsh24.mall.framework.dao.impl.BaseDaoImpl;
import com.hsh24.mall.item.dao.IItemFileDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemFileDaoImpl extends BaseDaoImpl implements IItemFileDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemFile> getItemFileList(ItemFile itemFile) {
		return (List<ItemFile>) getSqlMapClientTemplate().queryForList("item.file.getItemFileList", itemFile);
	}

}
