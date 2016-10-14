package com.xsp.stocksrelativity.datafetch.eastmoney;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Shaopeng.Xu on 2016-10-14.
 */
public class GetFundInfo {

    /**
     * @return
     */
    public static void getFundInfo() {

        try {
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
}
