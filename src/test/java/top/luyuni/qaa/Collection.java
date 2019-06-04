package top.luyuni.qaa;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Collection {

    @Test
    public void test(){
//        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        HashSet set = new HashSet();
        set.add(1);
        set.add(4);
        set.add(3);
        set.add(5);
        set.add(2);
        System.out.println(set);
    }
}
