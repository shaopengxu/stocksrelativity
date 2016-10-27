package com.xsp.stocksrelativity.u;

import java.util.*;

/**
 * Created by Shaopeng.Xu on 2016-10-27.
 */
public class SetOperator {

    public static Set<Long> getSetCombination(int all, int count) {
        Set<Long> set = new HashSet<Long>();

        for (int i = 0; i < all; i++) {
            set.add(1l << i);
        }
        if (count == 1) {
            return set;
        }
        return getSetCombination(set, all, count - 1, 2);

    }

    public static int getCount(int all, int count) {
        long result = 1;

        for (int i = 0; i < count; i++) {
            result = result * (all - i);
        }
        long temp = 1;
        for (int i = 1; i <= count; i++) {
            temp = temp * i;
        }
        return (int) (result / temp);
    }

    public static Set<Long> getSetCombination(Set<Long> set, int all, int count, int realCount) {
        int number = getCount(all, realCount);
        Set<Long> newSet = new HashSet<Long>();
        outer:
        for (Long l : set) {

            for (int i = 0; i < all; i++) {
                if ((l | 1l << i) == l) {
                    break;
                }

                newSet.add(l | 1l << i);
                if (newSet.size() == number) {
                    break outer;
                }
            }
        }

        if (count - 1 != 0) {
            return getSetCombination(newSet, all, count - 1, realCount+ 1);
        } else {
            return newSet;
        }
    }

    public static Set<Long> getSetCombinationAnotherWay(int all, int count) {
        Map m = new HashMap();
        getSetCombinationAnotherWay(m, all, count);
        List<Integer> list = new ArrayList<Integer>(m.keySet());
        Collections.sort(list);


        Map setMap = new HashMap();
        for (Integer i : list) {
            int a = i / 10;
            int b = i % 10;
            if (b == 1) {
                Set<Long> s = new HashSet();
                for (int index = 0; index < a; index++) {
                    s.add(1l << index);
                }
                setMap.put(i, s);
            } else if (a == b) {
                Set<Long> s = new HashSet();
                long l = 0l;
                for (int index = 0; index < a; index++) {
                    l = l | 1l << index;
                }
                s.add(l);
                setMap.put(i, s);
            } else{
                Set s = new HashSet();
                Set<Long> s0 = (Set) setMap.get((a-1) * 10 + b-1);
                Set<Long> s1 = (Set) setMap.get((a-1) * 10 + b);
                if (s0 == null) {
                    System.out.println("errors0");
                }else{
                    for(Long l : s0){
                        s.add(l|1l<<(a-1));
                    }
                }
                if (s1 == null) {
                    System.out.println("errors1");
                }else{
                    s.addAll(s1);
                }
                setMap.put(i, s);
            }
        }
        return (Set<Long>) setMap.get(all*10+count);
    }

    public static void getSetCombinationAnotherWay(Map<Integer, Object[]> map,  int all, int count) {

        int key = all * 10 + count;

        if (all == count) {

        } else if (count == 1) {

        } else {
            if (map.get((all - 1) * 10 + count) != null) {

            } else {
                 getSetCombinationAnotherWay(map, all - 1, count);
            }
            if (map.get((all - 1) * 10 + count - 1) != null) {
                map.get((all - 1) * 10 + count - 1);
            } else {
                getSetCombinationAnotherWay(map, all - 1, count - 1);
            }

        }
        map.put(key, null);
    }

    public static void main(String[] args) {
//        Set<Long> set = getSetCombination(5, 1);
//        for (Long l : set) {
//            System.out.print(Long.toBinaryString(l) + " ");
//        }
//        System.out.println();
//        set = getSetCombination(20, 5);
//        for (Long l : set) {
//            System.out.print(Long.toBinaryString(l) + " ");
//        }
        long start = System.currentTimeMillis();
        System.out.println(getSetCombination(47, 5).size()); //1533939
        System.out.println("cost : " + (System.currentTimeMillis() - start));
        System.out.println();
//
//        System.out.println(getCount(48,6) +getCount(48,5));
//        System.out.println(getCount(49,6));

        System.out.println(getCount(47,5));
        Map m = new HashMap();
        start = System.currentTimeMillis();
        Set<Long> set = getSetCombinationAnotherWay(47,7);
        System.out.println("cost : " + (System.currentTimeMillis() - start));
        System.out.println(set.size());
    }

}
