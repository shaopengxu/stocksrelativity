package com.xsp.stocksrelativity;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangxiong on 2016-09-29.
 */
public class StocksTest {

    Map<String, List<StockDailyPrice>> map = new HashMap<String, List<StockDailyPrice>>();

    public static void loadStocks() {
        File file  = new File(".");
        String files[] = file.list();
        for (String filename : files) {
            if (!((filename.startsWith("sh") || filename.startsWith("sz")) && filename.endsWith(".txt"))) {
                continue;
            }
            String szorsh = filename.substring(0, 2);
            String code = filename.substring(2, 8);

        }
    }

    public static void main(String[] args) {
        loadStocks();
    }
}
