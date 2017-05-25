package com.ligq.shoe.utils;

import java.util.UUID;

public class GenerateUUIDUtils {
	
	public static UUID getUUIDByCode(String code){
		return UUID.nameUUIDFromBytes((code).getBytes());
	}


}
