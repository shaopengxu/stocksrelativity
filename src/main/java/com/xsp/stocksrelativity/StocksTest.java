package com.xsp.stocksrelativity;

import javax.xml.transform.Templates;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangxiong on 2016-09-29.
 */
public class StocksTest {

    static Map<String, List<StockDailyPrice>> map = new HashMap<String, List<StockDailyPrice>>();

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
        for (String code1 : map.keySet()) {
            double temp = StockRelativityCalculate.calculateStockRelativity(map.get(code), map.get(code1));
            if (temp < least) {
                least = temp;
                targetCode1 = code1;
            }
        }
        System.out.println("least" + least);
        System.out.println(targetCode1);
        compareStockChange(map.get(targetCode1), map.get(code));
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

    public static void main(String[] args) {
        loadStocks();
        calculateRelativity();
    }
}
