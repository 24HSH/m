package com.hsh24.mall.api.item;

import java.util.List;

import com.hsh24.mall.api.item.bo.ItemFile;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemFileService {

	/**
	 * 获取商品文件.
	 * 
	 * @param shopId
	 * @param itemId
	 * @return
	 */
	List<ItemFile> getItemFileList(Long shopId, String itemId);

}
