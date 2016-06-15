package com.hsh24.mall.item.dao;

import java.util.List;

import com.hsh24.mall.api.item.bo.ItemFile;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemFileDao {

	/**
	 * 
	 * @param itemFile
	 * @return
	 */
	List<ItemFile> getItemFileList(ItemFile itemFile);

}
