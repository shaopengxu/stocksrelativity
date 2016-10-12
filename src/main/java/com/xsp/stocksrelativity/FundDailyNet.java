package com.xsp.stocksrelativity;

/**
 * Created by zhangxiong on 2016-10-12.
 */
public class FundDailyNet {

    private String fundcode;
    private String date;

    /**
     * 净值
     */
    private double net;
    /**
     * 累计净值
     */
    private double accnet;

    public String getFundcode() {
        return fundcode;
    }

    public void setFundcode(String fundcode) {
        this.fundcode = fundcode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getNet() {
        return net;
    }

    public void setNet(double net) {
        this.net = net;
    }

    public double getAccnet() {
        return accnet;
    }

    public void setAccnet(double accnet) {
        this.accnet = accnet;
    }
}
