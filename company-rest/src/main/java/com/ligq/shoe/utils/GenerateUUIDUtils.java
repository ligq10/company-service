package com.ligq.shoe.utils;

import java.util.UUID;

public class GenerateUUIDUtils {
	
	public static UUID getUUIDByCode(String code){
		return UUID.nameUUIDFromBytes((code).getBytes());
	}

	public static UUID getImageUUIDByfileName(String fileName){
		return UUID.nameUUIDFromBytes((fileName).getBytes());
	}
	
	public static UUID getProductImageUUIDByProductIdAndImageId(String productId,String imageId){
		return UUID.nameUUIDFromBytes((productId+imageId).getBytes());
	}

	public static UUID getOrderProductItemUUIDByOrderIdAndProductId(String orderId,String productId){
		return UUID.nameUUIDFromBytes((orderId+productId).getBytes());
	}

}
