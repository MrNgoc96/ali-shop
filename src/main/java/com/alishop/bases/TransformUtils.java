package com.alishop.bases;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Tranform a class to another
 * @author: longoc
 * @desciption Generic type <S> : class source ; Generic type <T>  class target
 */
@Component
public class TransformUtils<S,T> {

    public List<T> transformList(List<S> sourceList,Class<T> tClass) {
        List<T> bookDTOList = new ArrayList<>();
        for (S source : sourceList) {
            bookDTOList.add(transform(source,tClass));
        }

        return bookDTOList;
    }


    public S transformReverse (T target,Class<S> sClass) {
        S source = null;
        try {
            source = sClass.newInstance();
            BeanUtils.copyProperties(target, source);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return source;
    }

    public T transform(S source, Class<T> tClass) {
        T target = null;
        try {
            target = tClass.newInstance();
            BeanUtils.copyProperties(source, target);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return target;
    }
}
