package com.excilys.formation.logger;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

public class CDBLogger {
	private Logger logger;
	
	public <T> CDBLogger(Class<T> c) {
		logger = (Logger) LoggerFactory.getLogger(c);
	}
	
	public void info(String string) {
		logger.info(string);
	}
	
	public void error(String string) {
		logger.error(string);
	}
}
