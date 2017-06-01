package com.ligq.shoe.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("all")
public class OrderNoService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final String ORDER_NAME = "ORDER_NAME";

	public String getOrderNo() {
		long orderNoCount=(int)(Math.random()*1000000);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
		String formatStr = formatter.format(new Date());
		String pattern = "000000";
		java.text.DecimalFormat df = new java.text.DecimalFormat(pattern);
		return formatStr + df.format(orderNoCount);
	}

}
