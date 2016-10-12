package com.xsp.stocksrelativity;

import org.json.JSONObject;

import javax.xml.transform.Templates;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by zhangxiong on 2016-09-29.
 */
public class StocksTest {

    static Map<String, List<StockDailyPrice>> map = new HashMap<String, List<StockDailyPrice>>();

    static Map<String, Stock> stockMap = new HashMap<String, Stock>();

    public static void loadStocks() {
        File file = new File(".");
        String files[] = file.list();
        for (String filename : files) {

            if (!((filename.startsWith("sh") || filename.startsWith("sz")) && filename.endsWith(".txt"))) {
                continue;
            }
            String szorsh = filename.substring(0, 2);
            String code = filename.substring(2, 8);
            List<StockDailyPrice> list = StockDataDownload.readStockDailyPriceFromTxt(code, szorsh);
//            System.out.println(filename + " " + list.size());
            if (list.size() > 0) {
                map.put(code, list);
            }
        }
    }

    public static void calculateRelativityOfSomeCode(String code) {
        double least = Double.MAX_VALUE;
        String targetCode1 = "";
        int count = 0;
        List<Object[]> list = new ArrayList<Object[]>();
        for (String code1 : map.keySet()) {
            if (code1.equals(code)) {
                continue;
            }
            double temp = StockRelativityCalculate.calculateStockRelativity(map.get(code), map.get(code1));
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
        for(int index = 0;index<10;index++) {
            System.out.printf("%s  %s  %.5f\n", list.get(index)[0], stockMap.get(list.get(index)[0]).getName(), list.get(index)[1]);
        }
//        System.out.println("least" + least);
//        System.out.println(targetCode1);
//        compareStockChange(map.get(targetCode1), map.get(code));
    }

    public static void calculateRelativity() {
        Map<String, List<StockDailyPrice>> map2 = new HashMap(map);
        double least = Double.MAX_VALUE;
        String targetCode1 = "";
        String targetCode2 = "";
        int count = 0;
        for (String code1 : map.keySet()) {
            for (String code2 : map2.keySet()) {
                if (code1.equals(code2)) {
                    continue;
                }
                double temp = StockRelativityCalculate.calculateStockRelativity(map.get(code1), map2.get(code2));
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
        compareStockChange(map.get(targetCode1), map.get(targetCode2));
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
                System.out.printf("%s  %.2f  %.2f\n" ,stock1.get(index1).getDate(),stock1.get(index1).getChange(),stock2.get(index2).getChange());

                index1++;
                index2++;
            }
        }
    }

    public static void loadStockInfo() {
        String s = StockDataDownload.getChineseAStocksFromCtxalgoFile();
        JSONObject jo = new JSONObject(s);
        Map<String, Object> map = jo.toMap();
        for (String key : map.keySet()) {
            Stock stock = new Stock();
            String szOrsh = key.substring(0, 2);
            String code = key.substring(2);
            String chinese = (String) map.get(key);
            stock.setName(chinese);
            stockMap.put(code, stock);
        }
    }

    /**
     * 如果连续3天价格一样，说明在停牌阶段，将停牌阶段的数值删掉(停牌当天不删)
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
                        index ++;

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

    public static void main(String[] args) {
        loadStockInfo();
        loadStocks();
        calculateRelativityOfSomeCode("600170");
    }


}
