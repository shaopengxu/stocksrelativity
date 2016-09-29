package com.xsp.stocksrelativity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zhangxiong on 2016-09-27.
 */
public class StockRelativityCalculate {

    public double calculateStockRelativity(List<StockDailyPrice> stock1, List<StockDailyPrice> stock2) {
        double sum = 0.;
        Comparator<StockDailyPrice> c = new Comparator<StockDailyPrice>() {
            public int compare(StockDailyPrice o1, StockDailyPrice o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        };
        Collections.sort(stock1, c);
        Collections.sort(stock2, c);
        int index1 = 0;
        int index2 = 0;
        int count = 0;
        for (; index1 >= stock1.size() || index2 >= stock2.size(); ) {
            int comp = stock1.get(index1).getDate().compareTo(stock2.get(index2).getDate());
            if (comp == 0) {
                sum += (stock1.get(index1).getChange() - stock2.get(index2).getChange()) * (stock1.get(index1).getChange() - stock2.get(index2).getChange());
                index1++;
                index2++;
                count++;
            } else if (comp < 0) {
                index1++;
            } else {
                index2++;
            }
        }
        return sum / count;
    }

    public static void main(String[] args) {
        System.out.println("1".compareTo("2"));
    }
}
