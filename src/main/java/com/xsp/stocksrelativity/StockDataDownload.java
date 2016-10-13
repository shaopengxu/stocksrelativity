package com.xsp.stocksrelativity;

import com.xsp.stocksrelativity.entity.StockDailyPrice;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shpng on 2016/9/25.
 */
public class StockDataDownload {

    public static void main(String[] args) throws IOException {
//        StockDataDownload sdd = new StockDataDownload();
//        String s = sdd.getChineseAStocksFromCtxalgoFile();
//        JSONObject jo = new JSONObject(s);
//        Map map = jo.toMap();
//        System.out.println(map.size());
//        System.out.println(sdd.readStockDailyPriceFromTxt("600200", "sh"));
        getStockDataFromSohu();
    }

    public static void downStocksDataFromYahoo() {
        ExecutorService es = Executors.newFixedThreadPool(10);
        StockDataDownload sdd = new StockDataDownload();
        String s = sdd.getChineseAStocksFromCtxalgoFile();
        JSONObject jo = new JSONObject(s);
        Map<String, Object> map = jo.toMap();
        for (String key : map.keySet()) {
            final String kk = new String(key);
            es.execute(new Runnable() {
                public void run() {

                    String szOrsh = kk.substring(0, 2);
                    String code = kk.substring(2);
                    String newcode = code + "." + szOrsh;
                    if ("sh".compareTo(szOrsh) == 0) {
                        newcode = code + "." + "ss";
                    }
                    List<String> data = getStocksDataFromYahoo(newcode, null);
                    if (data.size() > 0) {
                        writeYahooStocksDataToFile(kk, data);
                    }

                }
            });


        }
    }

    public static List<StockDailyPrice> readStockDailyPriceFromTxt(String code, String szorsh) {
        List<StockDailyPrice> list = new ArrayList<StockDailyPrice>();
        try {

            BufferedReader reader = new BufferedReader(new FileReader("stocks_data/"+szorsh+code+".txt"));
            for (String line; (line = reader.readLine()) != null; ) {
                StockDailyPrice sdp = new StockDailyPrice();
                String []ss = line.split(",");
                //Date	Open	High	Low	Close	Volume	Adj Close
                sdp.setDate(ss[0]);
                sdp.setOpen(Double.valueOf(ss[1]));
                sdp.setHigh(Double.valueOf(ss[2]));
                sdp.setLow(Double.valueOf(ss[3]));
                sdp.setClose(Double.valueOf(ss[4]));
                sdp.setVolumn(Long.valueOf(ss[5]));
                sdp.setAdjclose(Double.valueOf(ss[6]));
                sdp.setChange(sdp.getClose()/sdp.getOpen() - 1);
                list.add(sdp);
                //System.out.println(sdp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @param type 哪个交易所
     * @return
     */
    public List<Stock> getStocks(String type) {
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

    public static String getChineseAStocksFromCtxalgoFile() {
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


    public void writeChineseAStocksDataFromCtxalgoToFile() {
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
     * @param code example 000001.sz
     * @param type
     * @return
     */
    public static List<String> getStocksDataFromYahoo(String code, String type) {
        List<String> list = new ArrayList<String>();

        try {
            // http://table.finance.yahoo.com/table.csv?s=000001.sz
            String urlStr = "http://table.finance.yahoo.com/table.csv?s=" + code;
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

    public static void writeYahooStocksDataToFile(String code, List<String> data) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(code + ".txt"));
            for (String s : data) {
                bw.write(s + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    public static void getFundData() {


        try {
            // http://table.finance.yahoo.com/table.csv?s=000001.sz
            String urlStr = "http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code=519983&page=3&per=20&sdate=&edate=&rt=0.7161785762886255";
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
