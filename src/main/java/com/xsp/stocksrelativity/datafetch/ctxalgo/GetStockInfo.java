package com.xsp.stocksrelativity.datafetch.ctxalgo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Shaopeng.Xu on 2016-10-14.
 */
public class GetStockInfo {

    public static String getChineseAStocksFromCtxalgoOnline() {
        String s = "";
        try {

            URL url = new URL("http://ctxalgo.com/api/stocks");

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            for (String line; (line = reader.readLine()) != null; ) {
                s += line;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}
