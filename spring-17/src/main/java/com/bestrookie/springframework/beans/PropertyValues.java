package com.bestrookie.springframework.beans;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author bestrookie
 * @Date 2023/11/29 16:11
 * @Desc 属性集合
 */
@Data
public class PropertyValues {
    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue pv){
        this.propertyValueList.add(pv);
    }

    public PropertyValue[] getPropertyValues(){
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName){
        for (PropertyValue pv : this.propertyValueList) {
            if (pv.getName().equals(propertyName)){
                return pv;
            }
        }
        return null;
    }
}
