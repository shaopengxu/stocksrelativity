package com.xsp.stocksrelativity.executor;

import com.xsp.stocksrelativity.datafetch.file.FileGetStockInfo;
import com.xsp.stocksrelativity.entity.Stock;
import com.xsp.stocksrelativity.service.StockService;

import java.util.Map;

/**
 * Created by shpng on 2016/10/14.
 */
public class InsertStocks {


    public static void insertStocks() {

        Map<String, Stock> stockMap = FileGetStockInfo.loadStockInfos();
        StockService.insertStockInfos(stockMap);
    }

    public static void main(String[] args) {
        insertStocks();
    }
}
