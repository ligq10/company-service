package com.ligq.shoe.mysql.dynamic;

import java.util.Collection;

import org.hibernate.criterion.MatchMode;
import org.springframework.util.StringUtils;

import com.ligq.shoe.mysql.dynamic.Criterion.Operator;
import com.ligq.shoe.mysql.dynamic.imp.CriterionImpl;
import com.ligq.shoe.mysql.dynamic.imp.MultiCriterionImpl;

public class Restrictions {

    /** 
     * 等于 
     * @param fieldName 
     * @param value 
     * @param ignoreNull 
     * @return 
     */  
    public static CriterionImpl eq(String fieldName, Object value, boolean ignoreNull) {  
        if(StringUtils.isEmpty(value))return null;  
        return new CriterionImpl (fieldName, value, Operator.EQ);  
    }  
      
    /** 
     * 不等于 
     * @param fieldName 
     * @param value 
     * @param ignoreNull 
     * @return 
     */  
    public static CriterionImpl ne(String fieldName, Object value, boolean ignoreNull) {  
        if(StringUtils.isEmpty(value))return null;  
        return new CriterionImpl (fieldName, value, Operator.NE);  
    }  
  
    /** 
     * 模糊匹配 
     * @param fieldName 
     * @param value 
     * @param ignoreNull 
     * @return 
     */  
    public static CriterionImpl like(String fieldName, String value, boolean ignoreNull) {  
        if(StringUtils.isEmpty(value))return null;  
        return new CriterionImpl (fieldName, value, Operator.LIKE);  
    }  
  
    /** 
     *  
     * @param fieldName 
     * @param value 
     * @param matchMode 
     * @param ignoreNull 
     * @return 
     */  
    public static CriterionImpl like(String fieldName, String value,  
            MatchMode matchMode, boolean ignoreNull) {  
        if(StringUtils.isEmpty(value))return null;  
        return new CriterionImpl(fieldName,value,Operator.LIKE);  
    }  
  
    /** 
     * 大于 
     * @param fieldName 
     * @param value 
     * @param ignoreNull 
     * @return 
     */  
    public static CriterionImpl gt(String fieldName, Object value, boolean ignoreNull) {  
        if(StringUtils.isEmpty(value))return null;  
        return new CriterionImpl (fieldName, value, Operator.GT);  
    }  
  
    /** 
     * 小于 
     * @param fieldName 
     * @param value 
     * @param ignoreNull 
     * @return 
     */  
    public static CriterionImpl lt(String fieldName, Object value, boolean ignoreNull) {  
        if(StringUtils.isEmpty(value))return null;  
        return new CriterionImpl (fieldName, value, Operator.LT);  
    }  
  
    /** 
     * 大于等于 
     * @param fieldName 
     * @param value 
     * @param ignoreNull 
     * @return 
     */  
    public static CriterionImpl lte(String fieldName, Object value, boolean ignoreNull) {  
        if(StringUtils.isEmpty(value))return null;  
        return new CriterionImpl (fieldName, value, Operator.GTE);  
    }  
  
    /** 
     * 小于等于 
     * @param fieldName 
     * @param value 
     * @param ignoreNull 
     * @return 
     */  
    public static CriterionImpl gte(String fieldName, Object value, boolean ignoreNull) {  
        if(StringUtils.isEmpty(value))return null;  
        return new CriterionImpl (fieldName, value, Operator.LTE);  
    }  
  
    /** 
     * 并且 
     * @param criterions 
     * @return 
     */  
    public static MultiCriterionImpl and(Criterion... criterions){  
        return new MultiCriterionImpl(criterions, Operator.AND);  
    }  
    /** 
     * 或者 
     * @param criterions 
     * @return 
     */  
    public static MultiCriterionImpl or(Criterion... criterions){  
        return new MultiCriterionImpl(criterions, Operator.OR);  
    }  
    /** 
     * 包含于 
     * @param fieldName 
     * @param value 
     * @return 
     */  
    @SuppressWarnings("rawtypes")  
    public static MultiCriterionImpl in(String fieldName, Collection value, boolean ignoreNull) {  
        if(ignoreNull&&(value==null||value.isEmpty())){  
            return null;  
        }  
        CriterionImpl[] ses = new CriterionImpl[value.size()];  
        int i=0;  
        for(Object obj : value){  
            ses[i]=new CriterionImpl(fieldName,obj,Operator.EQ);  
            i++;  
        }  
        return new MultiCriterionImpl(ses,Operator.OR);  
    }  
}
