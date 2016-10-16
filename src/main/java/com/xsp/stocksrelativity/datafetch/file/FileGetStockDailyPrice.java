package com.xsp.stocksrelativity.datafetch.file;

import com.xsp.stocksrelativity.entity.StockDailyPrice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shaopeng.Xu on 2016-10-14.
 */
public class FileGetStockDailyPrice {

    public static List<StockDailyPrice> getStockDailyPrice(String code, String szorsh) {
        List<StockDailyPrice> list = new ArrayList<StockDailyPrice>();
        try {

            BufferedReader reader = new BufferedReader(new FileReader("stocks_data/" + szorsh + code + ".txt"));
            for (String line; (line = reader.readLine()) != null; ) {
                StockDailyPrice sdp = new StockDailyPrice();
                String[] ss = line.split(",");
                //Date	Open	High	Low	Close	Volume	Adj Close
                sdp.setDate(ss[0]);
                sdp.setOpen(new BigDecimal(ss[1]));
                sdp.setHigh(new BigDecimal(ss[2]));
                sdp.setLow(new BigDecimal(ss[3]));
                sdp.setClose(new BigDecimal(ss[4]));
                sdp.setVolumn(Long.valueOf(ss[5]));
                sdp.setAdjclose(new BigDecimal(ss[6]));
                list.add(sdp);
                //System.out.println(sdp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Map<String, List<StockDailyPrice>> loadStockDailyPrices() {
        Map<String, List<StockDailyPrice>> map = new HashMap<String, List<StockDailyPrice>>();
        File file = new File("stocks_data");
        String files[] = file.list();
        for (String filename : files) {

            if (!((filename.startsWith("sh") || filename.startsWith("sz")) && filename.endsWith(".txt"))) {
                continue;
            }
            String szorsh = filename.substring(0, 2);
            String code = filename.substring(2, 8);
            List<StockDailyPrice> list = getStockDailyPrice(code, szorsh);
            if (list.size() > 0) {
                map.put(code, list);
            }
        }
        return map;
    }

}
