package com.xsp.stocksrelativity.datafetch.sohu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by zhangxiong on 2016-10-14.
 */
public class GetStockDailyPrice {


    //http://q.stock.sohu.com/hisHq?code=cn_000001&start=20160602&end=20161011&stat=1&order=D&period=d&callback=historySearchHandler&rt=jsonp&r=0.2570124812116552&0.6611461189293653
    public static void getStockDataFromSohu() {
        try {
            // http://table.finance.yahoo.com/table.csv?s=000001.sz
            String urlStr = "http://q.stock.sohu.com/hisHq?code=cn_000001&start=20160602&end=20161011&stat=1&order=D&period=d&callback=historySearchHandler&rt=jsonp&r=0.2570124812116552&0.6611461189293653";
            URL url = new URL(urlStr);
            System.out.println(urlStr);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "GBK"));
            String line = reader.readLine();
            System.out.println(line);
            for (; (line = reader.readLine()) != null; ) {
                System.out.println(line);
            }
        } catch (Exception e) {
            //TODO error
            e.printStackTrace();
        }

    }

}
