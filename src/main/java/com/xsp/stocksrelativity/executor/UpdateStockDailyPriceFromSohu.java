package com.xsp.stocksrelativity.executor;

import com.xsp.stocksrelativity.datafetch.sohu.SohuGetStockDailyPrice;
import com.xsp.stocksrelativity.entity.Stock;
import com.xsp.stocksrelativity.entity.StockDailyPrice;
import com.xsp.stocksrelativity.service.StockService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shaopeng.Xu on 2016-10-14.
 */
public class UpdateStockDailyPriceFromSohu {


    public static void insertStockDailyPriceFromSohu(String date, String code) {

        StockDailyPrice stockDailyPrice = SohuGetStockDailyPrice.getStockDataFromSohu(code, date);
        if (stockDailyPrice == null) {
            System.out.println("cannot get data: " + code);
            return;
        }
        Map<String, List<StockDailyPrice>> map = new HashMap<String, List<StockDailyPrice>>();
        List<StockDailyPrice> stockDailyPrices = new ArrayList<StockDailyPrice>();
        stockDailyPrices.add(stockDailyPrice);
        map.put(code, stockDailyPrices);
        StockService.insertStockDailyPrices(map);
    }

    public static void insertStockDailyPriceFromSohu(String date){

        List<Stock> stocks = StockService.getStocks();
        for (Stock stock : stocks) {
            try {
                List<StockDailyPrice> stockDailyPrices = StockService.selectByCondition(stock.getCode(), date);
                if (stockDailyPrices.size() > 0) {
                    continue;
                }
                insertStockDailyPriceFromSohu(date, stock.getCode());
            } catch (Exception e) {
                System.out.println("code : " + stock.getCode());
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        insertStockDailyPriceFromSohu("20161010");
    }

}
