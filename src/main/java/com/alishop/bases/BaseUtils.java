package com.alishop.bases;


import org.springframework.data.domain.Sort;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class BaseUtils {

    public static String encryptPassword(String password)
    {
        String sha1 = null;
        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(password.getBytes("UTF-8"), 0, password.length());
            sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sha1.toLowerCase();
    }


    public static HashMap sortByValues(HashMap map,boolean isSortAsc) {
        List list = new LinkedList(map.entrySet());
        if(isSortAsc){
            Collections.sort(list, (o1, o2) -> ((Comparable) ((Map.Entry) (o1)).getValue())
                    .compareTo(((Map.Entry) (o2)).getValue()));
        }else{
            Collections.sort(list, (o1, o2) -> ((Comparable) ((Map.Entry) (o2)).getValue())
                    .compareTo(((Map.Entry) (o1)).getValue()));
        }
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    public static boolean isNumber(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Sort getSort(String sortType,String... properties) {
        Sort sort;
        if (sortType.equals("asc")) {
            sort = new Sort(Sort.Direction.ASC,properties);
        } else if (sortType.equals("desc")) {
            sort = new Sort(Sort.Direction.DESC,properties);
        } else {
            sort = new Sort(Sort.DEFAULT_DIRECTION,properties);
        }
        return sort;
    }

    public static int countTotalPage(int total, int maxResult) {
        int page = total / maxResult;
        if (total % maxResult > 0) {
            page++;
        }
        return page;
    }

}
