package com.xsp.stocksrelativity;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shpng on 2016/9/25.
 */
public class StockDataDownload {

    public static void main(String[] args) throws IOException {

        new StockDataDownload().writeChineseAStocksDataFromCtxalgoToFile();


    }

    /**
     *
     * @param type 哪个交易所
     * @return
     */
    public List<Stock> getStocks(String type){
        return null;
    }

    public String getChineseAStocksFromCtxalgoOnline() {
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

    public String getChineseAStocksFromCtxalgoFile() {
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

    public void writeChineseAStocksDataFromCtxalgoToFile(){
        String s = getChineseAStocksFromCtxalgoOnline();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("chinese_a_stocks_ctxalgo.json"));
            bw.write(s);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param code example 000001.sz
     * @param type
     * @return
     */
    public List<String> getStocksDataFromYahho(String code, String type) {
        List<String> list = new ArrayList<String>();

        try {
            // http://table.finance.yahoo.com/table.csv?s=000001.sz
            URL url = new URL("http://table.finance.yahoo.com/table.csv?s=" + code);
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
