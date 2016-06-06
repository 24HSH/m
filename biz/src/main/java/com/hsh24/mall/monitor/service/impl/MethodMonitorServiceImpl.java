package com.hsh24.mall.monitor.service.impl;

import java.util.List;

import com.hsh24.mall.api.monitor.IMethodMonitorService;
import com.hsh24.mall.api.monitor.bo.MethodMonitor;
import com.hsh24.mall.framework.bo.BooleanResult;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.hsh24.mall.framework.util.LogUtil;
import com.hsh24.mall.monitor.dao.IMethodMonitorDao;

/**
 * 
 * @author xujiakun
 * 
 */
public class MethodMonitorServiceImpl implements IMethodMonitorService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(MethodMonitorServiceImpl.class);

	private IMethodMonitorDao methodMonitorDao;

	@Override
	public int getMethodMonitorCount(MethodMonitor methodMonitor) {
		if (methodMonitor == null) {
			return 0;
		}

		try {
			return methodMonitorDao.getMethodMonitorCount(methodMonitor);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(methodMonitor), e);
		}

		return 0;
	}

	@Override
	public List<MethodMonitor> getMethodMonitorList(MethodMonitor methodMonitor) {
		if (methodMonitor == null) {
			return null;
		}

		try {
			return methodMonitorDao.getMethodMonitorList(methodMonitor);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(methodMonitor), e);
		}

		return null;
	}

	@Override
	public BooleanResult createMethodMonitor(MethodMonitor methodMonitor) {
		BooleanResult res = new BooleanResult();
		res.setResult(false);

		if (methodMonitor == null) {
			res.setCode("method 信息不能为空！");
			return res;
		}

		try {
			String result = methodMonitorDao.createMethodMonitor(methodMonitor);
			res.setCode(result);
			res.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(methodMonitor), e);

			res.setCode(IMethodMonitorService.ERROR_MESSAGE);
		}

		return res;
	}

	public IMethodMonitorDao getMethodMonitorDao() {
		return methodMonitorDao;
	}

	public void setMethodMonitorDao(IMethodMonitorDao methodMonitorDao) {
		this.methodMonitorDao = methodMonitorDao;
	}

}
