package com.ligq.shoe.constants;

/**
 * Created by Jerry on 2015/6/3 0003.
 */
public enum OrderStatus {
	
    /**
     * 待确认
     */
    WAITING(1, "待确认"),
    /**
     * 已确认
     */
    WAITING_DISTRIBUTI(2,"待分配"),
    /**
     * 已分配
     */
    HAD_DISTRIBUTI(3,"已分配"),
    /**
     * 已接单（app员工确认接单）
     */
    HAD_RECEIVED(4, "员工已接单"),
    
    /**
     * 已拒绝（app员工）
     */
    HAD_REFUSE(5, "员工已拒绝"),
    
    /**
     * 上门中
     */
    COMEING(6, "上门中"),
    /**
     * 服务中
     */
    IN_SERVICE(7, "服务中"),
    
    /**
     * 服务中
     */
    SERVICE_COMPLETION(8, "服务已完成"),
    
    /**
     * 未回访(平台)、待评价(app)
     */
    NOT_FEEDBACK(9, "未回访"),
    /**
     * 已回访（平台）、已评价(app)
     */
    HAD_FEEDBACK(10, "已回访"),
    /**
     * 服务商取消
     */
    COMPANY_CANCEL(11, "服务商取消"),
    /**
     * 用户取消
     */
    CUSTOMER_CANCEL(12, "用户取消"),
    /**
     * 已删除 *
     */
    DELETED(-1,"已删除");

    private  int status;
    private  String statusName;

    private OrderStatus(int status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusName() {
        return statusName;
    }
    
    public static String getStatusName(int index){
    	for(OrderStatus orderStatus : OrderStatus.values()){
    		if(orderStatus.getStatus() == index){
    			return orderStatus.getStatusName();
    		}
    	}
    	return "";
    }
}
