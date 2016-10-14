package com.xsp.stocksrelativity.datafetch.file;

import com.xsp.stocksrelativity.entity.Stock;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangxiong on 2016-10-14.
 */
public class GetStockInfo {


    public static String getStockInfo() {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("chinese_a_stocks_ctxalgo.json"));
            String s = null;
            while ((s = br.readLine()) != null) {
                result += s;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String, Stock> loadStockInfos() {
        Map<String, Stock> stockMap = new HashMap<String, Stock>();
        String s = getStockInfo();
        JSONObject jo = new JSONObject(s);
        Map<String, Object> map = jo.toMap();
        for (String key : map.keySet()) {
            Stock stock = new Stock();
            String szOrsh = key.substring(0, 2);
            String code = key.substring(2);
            String chinese = (String) map.get(key);
            stock.setName(chinese);
            stock.setCountry("zh");
            stock.setType(code.startsWith("60") ? "sh" : "sz");
            stock.setCode(code);
            stockMap.put(code, stock);
        }
        return stockMap;
    }


}
