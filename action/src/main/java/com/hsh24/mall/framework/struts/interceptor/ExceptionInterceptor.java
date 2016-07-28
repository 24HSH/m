package com.hsh24.mall.framework.struts.interceptor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.Logger;

import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor;

/**
 * 
 * @author
 * 
 */
public class ExceptionInterceptor extends ExceptionMappingInterceptor {

	private static final long serialVersionUID = -3070785537755358876L;

	private static Logger4jExtend log = Logger4jCollection.getLogger(ExceptionInterceptor.class);

	@SuppressWarnings("rawtypes")
	private final Set<Class> ignoreExceptions = new HashSet<Class>();

	public void setIgnoreExceptions(String exceptions) {
		if (exceptions == null) {
			return;
		}

		String[] exClazz = exceptions.split("\\s*,\\s*");
		for (int i = 0; i < exClazz.length; i++) {
			try {
				ignoreExceptions.add(Class.forName(exClazz[i]));
			} catch (ClassNotFoundException e) {
				log.error("failed to set ignore exception", e);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	protected void doLog(Logger logger, Exception e) {
		for (Iterator<Class> i = ignoreExceptions.iterator(); i.hasNext();) {
			Class clazz = i.next();
			if (clazz.isInstance(e)) {
				return;
			}
		}

		super.doLog(logger, e);
	}

}
