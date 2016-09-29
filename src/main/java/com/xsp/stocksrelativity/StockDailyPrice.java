package com.xsp.stocksrelativity;

import org.apache.commons.beanutils.BeanMap;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangxiong on 2016-09-27.
 */
public class StockDailyPrice {

    private String code;
    private String date;
    private double open;
    private double high;
    private double low;
    private double close;
    private long volumn;
    private double adjclose;
    /**
     * 涨跌幅
     */
    private double change;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public long getVolumn() {
        return volumn;
    }

    public void setVolumn(long volumn) {
        this.volumn = volumn;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getAdjclose() {
        return adjclose;
    }

    public void setAdjclose(double adjclose) {
        this.adjclose = adjclose;
    }

    @Override
    public String toString() {
        return new HashMap(new BeanMap(this)).toString();
    }

    public static void main(String args[]) {
        StockDailyPrice s = new StockDailyPrice();
        s.setChange(1);
        s.setCode("1222");
        System.out.println(s);
    }
}
