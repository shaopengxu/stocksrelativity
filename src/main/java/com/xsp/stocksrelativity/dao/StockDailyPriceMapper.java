package com.xsp.stocksrelativity.dao;

import com.xsp.stocksrelativity.entity.StockDailyPrice;

import java.util.List;

/**
 * Created by zhangxiong on 2016-10-12.
 */
public interface StockDailyPriceMapper {

    List<StockDailyPrice> selectAll();

    int insert(StockDailyPrice stockDailyPrice);

}
