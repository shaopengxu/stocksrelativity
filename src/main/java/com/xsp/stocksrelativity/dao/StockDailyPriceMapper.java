package com.xsp.stocksrelativity.dao;

import com.xsp.stocksrelativity.StockDailyPrice;

import java.util.List;

/**
 * Created by zhangxiong on 2016-10-12.
 */
public interface StockDailyPriceMapper {

    List<StockDailyPrice> selectAll();
}
