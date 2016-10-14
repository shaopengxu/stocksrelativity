package com.xsp.stocksrelativity.dao;

import com.xsp.stocksrelativity.entity.Stock;

import java.util.List;

/**
 * Created by shpng on 2016/10/14.
 */
public interface StockMapper {

    List<Stock> selectAll();

    int insert(Stock stock);
}
