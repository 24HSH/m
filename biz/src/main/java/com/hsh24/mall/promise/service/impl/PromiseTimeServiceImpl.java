package com.hsh24.mall.promise.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hsh24.mall.api.promise.IPromiseTimeService;
import com.hsh24.mall.api.promise.bo.PromiseTime;
import com.hsh24.mall.api.promise.bo.PromiseTimeItem;
import com.hsh24.mall.framework.util.DateUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class PromiseTimeServiceImpl implements IPromiseTimeService {

	@Override
	public List<PromiseTime> getPromiseTimeList(Long shopId) {
		if (shopId == null) {
			return null;
		}

		List<PromiseTime> timeList = new ArrayList<PromiseTime>();

		Date today = new Date();

		for (int i = 0; i < 4; i++) {
			PromiseTime time = new PromiseTime();

			if (i == 0) {
				time.setDateText("今天");
				time.setDate(DateUtil.datetime(today, DateUtil.DEFAULT_DATE_FORMAT));
			} else if (i == 1) {
				time.setDateText("明天");
				time.setDate(DateUtil.datetime(DateUtil.addDays(today, 1), DateUtil.DEFAULT_DATE_FORMAT));
			} else {
				Date day = DateUtil.addDays(today, i);
				time.setDateText(DateUtil.datetime(day, "MM月dd日"));
				time.setDate(DateUtil.datetime(day, DateUtil.DEFAULT_DATE_FORMAT));
			}

			// settime
			int h = Integer.parseInt(DateUtil.datetime(today, "HH"));

			List<PromiseTimeItem> timeItemList = new ArrayList<PromiseTimeItem>();

			PromiseTimeItem item0 = new PromiseTimeItem();
			item0.setStartTime("08:00");
			item0.setEndTime("11:00");
			if (i > 0 || h < 11) {
				timeItemList.add(item0);
			}

			PromiseTimeItem item1 = new PromiseTimeItem();
			item1.setStartTime("11:00");
			item1.setEndTime("14:00");
			if (i > 0 || h < 14) {
				timeItemList.add(item1);
			}

			PromiseTimeItem item2 = new PromiseTimeItem();
			item2.setStartTime("14:00");
			item2.setEndTime("17:00");
			if (i > 0 || h < 17) {
				timeItemList.add(item2);
			}

			PromiseTimeItem item3 = new PromiseTimeItem();
			item3.setStartTime("17:00");
			item3.setEndTime("20:00");
			if (i > 0 || h < 20) {
				timeItemList.add(item3);
			}
			// settime

			time.setPromiseTimeItemList(timeItemList);

			timeList.add(time);
		}

		return timeList;
	}
}
