package com.sinosoft.antifake.web.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

/**
 * 
 * @author chenshaoao
 */
public class BaseController {

	@Autowired
	protected MessageSource messageSource;
	
}
