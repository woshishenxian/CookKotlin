package com.cook.kotlin.utils;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DE10035 on 2017/12/21.
 */

public class StringConverter implements PropertyConverter<List<Integer>,String> {

    @Override
    public List<Integer> convertToEntityProperty(String databaseValue) {
        if (databaseValue !=null){
           String[] s =  databaseValue.split(",");
            List<Integer> entityProperty = new ArrayList<>();
            for (String s1 : s) {
                entityProperty.add(Integer.valueOf(s1));
            }
            return entityProperty;
        }
        return null;
    }

    @Override
    public String convertToDatabaseValue(List<Integer> entityProperty) {
        if (entityProperty != null){
            StringBuilder sb= new StringBuilder();
            for(Integer link:entityProperty){
                sb.append(link);
                sb.append(",");
            }
            return sb.toString();
        }
        return null;
    }
}
