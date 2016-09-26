package com.xsp.stocksrelativity;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shpng on 2016/9/25.
 */
public class StockDataDownload {

    public static void main(String[] args) throws IOException {

        //http://table.finance.yahoo.com/table.csv?s=000001.sz
//        IOUtils.copy(new URL("http://table.finance.yahoo.com/table.csv?s=000001.sz").openStream(),
//                new FileOutputStream("000001sz.csv"));
//        IOUtils.copy(new URL("https://www.baidu.com").openStream(),
//                new FileOutputStream("000001sz.html"));

        URL url = new URL("http://table.finance.yahoo.com/table.csv?s=000001.sz");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            for (String line; (line = reader.readLine()) != null; ) {
                System.out.println(line);
            }
        } catch (Exception e) {
        }


    }

    /**
     *
     * @param type 哪个交易所
     * @return
     */
    public List<Stock> getStocks(String type){
        return null;
    }


    public List<String> getStocksDataFromYahho(String urlString) {
        List<String> list = new ArrayList<String>();

        try {
            URL url = new URL(urlString);
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
