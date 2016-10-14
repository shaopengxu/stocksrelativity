package com.xsp.stocksrelativity.calculate;

import com.xsp.stocksrelativity.entity.Stock;
import com.xsp.stocksrelativity.entity.StockDailyPrice;

import java.util.*;

/**
 * Created by zhangxiong on 2016-10-14.
 */
public class CalculateStockRelativity {

    public static void calculateRelativityOfSomeCode(String code, Map<String, List<StockDailyPrice>> stockDailyPriceMap,
                                                     Map<String, Stock> stockMap) {
        double least = Double.MAX_VALUE;
        String targetCode1 = "";
        int count = 0;
        List<Object[]> list = new ArrayList<Object[]>();
        for (String code1 : stockDailyPriceMap.keySet()) {
            if (code1.equals(code)) {
                continue;
            }
            double temp = calculateStockRelativity(stockDailyPriceMap.get(code), stockDailyPriceMap.get(code1));
            Object[] cal = new Object[2];
            cal[0] = code1;
            cal[1] = temp;
            list.add(cal);
        }
        Collections.sort(list, new Comparator<Object[]>() {
            public int compare(Object[] o1, Object[] o2) {
                Double d1 = (Double) o1[1];
                Double d2 = (Double) o2[1];
                return d1 > d2 ? 1 : (d1 < d2 ? -1 : 0);
            }
        });
        for (int index = 0; index < 10; index++) {
            System.out.printf("%s  %s  %.5f\n", list.get(index)[0], stockMap.get(list.get(index)[0]).getName(), list.get(index)[1]);
        }
    }

    public static void calculateRelativity(Map<String, List<StockDailyPrice>> stockDailyPriceMap) {
        Map<String, List<StockDailyPrice>> map2 = new HashMap(stockDailyPriceMap);
        double least = Double.MAX_VALUE;
        String targetCode1 = "";
        String targetCode2 = "";
        int count = 0;
        for (String code1 : stockDailyPriceMap.keySet()) {
            for (String code2 : map2.keySet()) {
                if (code1.equals(code2)) {
                    continue;
                }
                double temp = calculateStockRelativity(stockDailyPriceMap.get(code1), map2.get(code2));
                if (temp < least) {
                    least = temp;
                    targetCode1 = code1;
                    targetCode2 = code2;
                }

            }
        }

        System.out.println("least" + least);
        System.out.println(targetCode1);
        System.out.println(targetCode2);
        compareStockChange(stockDailyPriceMap.get(targetCode1), stockDailyPriceMap.get(targetCode2));
    }

    public static void compareStockChange(List<StockDailyPrice> stock1, List<StockDailyPrice> stock2) {
        int index1 = 0;
        int index2 = 0;
        for (; index1 < stock1.size() && index2 < stock2.size(); ) {
            if (stock1.get(index1).getDate().compareTo(stock2.get(index2).getDate()) < 0) {
                index1++;
            } else if (stock1.get(index1).getDate().compareTo(stock2.get(index2).getDate()) > 0) {
                index2++;
            } else {
                System.out.printf("%s  %.2f  %.2f\n", stock1.get(index1).getDate(), stock1.get(index1).getChange(),
                        stock2.get(index2).getChange());

                index1++;
                index2++;
            }
        }
    }

    /**
     * 如果连续3天价格一样，说明在停牌阶段，将停牌阶段的数据删除(停牌当天不删)
     *
     * @param list
     */
    public static void filterSamePriceStockData(List<StockDailyPrice> list) {
        Comparator<StockDailyPrice> c = new Comparator<StockDailyPrice>() {
            public int compare(StockDailyPrice o1, StockDailyPrice o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        };
        Collections.sort(list, c);
        boolean flag = false;
        int index = 0;
        double currentClose = 0;
        for (; ; ) {
            if (index >= list.size()) {
                break;
            }
            if (flag) {
                if (list.get(index).getOpen() == currentClose && list.get(index).getClose() == currentClose) {
                    list.remove(index);
                    continue;
                } else {
                    flag = false;
                    index++;
                }
            } else {
                if (index + 3 < list.size()) {
                    if (list.get(index).getOpen() == list.get(index).getClose() &&
                            list.get(index + 1).getOpen() == list.get(index + 1).getClose() &&
                            list.get(index + 2).getOpen() == list.get(index + 2).getClose() &&
                            list.get(index).getOpen() == list.get(index + 1).getOpen() &&
                            list.get(index).getOpen() == list.get(index + 2).getOpen()) {
                        currentClose = list.get(index).getClose();
                        flag = true;
                        index++;

                    } else {
                        index++;
                        continue;
                    }
                } else {
                    break;
                }
            }

        }
    }

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


}
