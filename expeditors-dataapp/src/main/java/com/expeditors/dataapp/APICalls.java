package com.expeditors.dataapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * API Calls
 * <p>
 * Created by Mainak Basu.
 * 
 */
@RestController
public class APICalls {
	@Autowired
	Environment environment;

	private static final Logger logger = LoggerFactory.getLogger(APICalls.class);

	/**
	 * API : Version 1, returns the response
	 *
	 */
	@RequestMapping(value = "/application/data", method = RequestMethod.GET, produces = { "application/json" })
	public String getData(
			@RequestParam(value = "sortType", required=false, defaultValue="ASCD") String sortTypeVal,
			@RequestParam(value="name", required=false, defaultValue="First Name") String name
			) throws Exception {
		logger.info("Starting app at port : " +environment.getProperty("local.server.port") );
		
		return new APIResponse().getData(name, sortTypeVal);
	}


}
