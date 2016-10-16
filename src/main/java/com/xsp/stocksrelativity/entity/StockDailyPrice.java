package com.xsp.stocksrelativity.entity;

import org.apache.commons.beanutils.BeanMap;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Created by Shaopeng.Xu on 2016-09-27.
 */
public class StockDailyPrice {

    private String code;
    private String date;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private long volumn;
    private BigDecimal adjclose;
    /**
     * 涨跌幅
     */
    private double change;

    public static void main(String args[]) {
        StockDailyPrice s = new StockDailyPrice();
        s.setCode("1222");
        System.out.println(s);
    }

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

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public long getVolumn() {
        return volumn;
    }

    public void setVolumn(long volumn) {
        this.volumn = volumn;
    }

    public double getChange() {
        double open_ = open.doubleValue();
        double close_ = close.doubleValue();
        return (close_ - open_) / open_;
    }


    public BigDecimal getAdjclose() {
        return adjclose;
    }

    public void setAdjclose(BigDecimal adjclose) {
        this.adjclose = adjclose;
    }

    @Override
    public String toString() {
        return new HashMap(new BeanMap(this)).toString();
    }
}
