package com.zjluoyue.typeinfo;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjluoyue on 2016/10/14.
 */
public class FilledList<T> {
    private Class<T> type;

    public FilledList(Class<T> type) {
        this.type = type;
    }

    public List<T> creat(int nElements) {
        List<T> result = new ArrayList<T>();
        try {
            for (int i = 0; i < nElements; i++) {
                result.add(type.newInstance());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void main(String[] args) {
        FilledList<ClassInitialization.CountedInteger> fl =
                new FilledList<ClassInitialization.CountedInteger>(ClassInitialization.CountedInteger.class);
        System.out.println(fl.creat(15));


    }


}
