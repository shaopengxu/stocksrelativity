package com.xsp.stocksrelativity.datafetch.yahoo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaopeng.Xu on 2016-10-14.
 */
public class GetStockDailyPrice {

    /**
     * @param codeAndType example 000001.sz
     * @return
     */
    public static List<String> getStocksDataFromYahoo(String codeAndType) {
        List<String> list = new ArrayList<String>();

        try {
            // http://table.finance.yahoo.com/table.csv?s=000001.sz
            String urlStr = "http://table.finance.yahoo.com/table.csv?s=" + codeAndType;
            URL url = new URL(urlStr);
            System.out.println(urlStr);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String line = reader.readLine();
            for (; (line = reader.readLine()) != null; ) {
                list.add(line);
            }
        } catch (Exception e) {
            //TODO error
            e.printStackTrace();
        }
        return list;
    }

}
