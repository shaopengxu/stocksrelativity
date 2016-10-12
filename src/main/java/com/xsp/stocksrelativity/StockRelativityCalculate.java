package com.xsp.stocksrelativity;

import com.xsp.stocksrelativity.entity.StockDailyPrice;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zhangxiong on 2016-09-27.
 */
public class StockRelativityCalculate {

    public static double calculateStockRelativity(List<StockDailyPrice> stock1, List<StockDailyPrice> stock2) {
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
        double stockSum = 0;
        for (int i = 0; i < stock1.size(); i++) {
            stockSum += Math.abs(stock1.get(i).getChange());
        }
        if (stockSum < 0.1) {
            return Double.MAX_VALUE;
        }
        stockSum = 0;
        for (int i = 0; i < stock2.size(); i++) {
            stockSum += Math.abs(stock2.get(i).getChange());
        }
        if (stockSum < 0.1) {
            return Double.MAX_VALUE;
        }
        for (; index1 < stock1.size() && index2 < stock2.size(); ) {
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
        if (count <= 10 || sum <= 0) {
            return Double.MAX_VALUE;
        }
        return sum / count;
    }

    public static void main(String[] args) {
        System.out.println("1".compareTo("2"));
    }
}