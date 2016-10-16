package com.xsp.stocksrelativity.service;

import com.xsp.stocksrelativity.dao.GetSessionFactory;
import com.xsp.stocksrelativity.dao.StockDailyPriceMapper;
import com.xsp.stocksrelativity.dao.StockMapper;
import com.xsp.stocksrelativity.entity.Stock;
import com.xsp.stocksrelativity.entity.StockDailyPrice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Shaopeng.Xu on 2016-10-14.
 */
public class StockService {

    public static void insertStockInfos(Map<String, Stock> stockMap) {
        SqlSession session = GetSessionFactory.getSqlSessionFactory().openSession();

        try {

            StockMapper mapper = session.getMapper(StockMapper.class);
            for (String code : stockMap.keySet()) {
                Stock stock = stockMap.get(code);
                mapper.insert(stock);
                System.out.println(code);
            }
            session.commit();
        } finally {
            session.close();
        }
    }

    public static void insertStockDailyPrices(Map<String, List<StockDailyPrice>> stockDailyPriceMap) {

        SqlSession session = GetSessionFactory.getSqlSessionFactory().openSession();
        try {

            StockDailyPriceMapper mapper = session.getMapper(StockDailyPriceMapper.class);
            for (String code : stockDailyPriceMap.keySet()) {
                List<StockDailyPrice> list = stockDailyPriceMap.get(code);
                for (StockDailyPrice sdp : list) {
                    sdp.setCode(code);
                    sdp.setDate(sdp.getDate().replaceAll("\\-", ""));
                    int count = mapper.insert(sdp);

                }
                session.commit();
            }

        } finally {

            session.close();
        }
    }

    public static List<Stock> getStocks() {
        SqlSession session = GetSessionFactory.getSqlSessionFactory().openSession();
        try {

            StockMapper mapper = session.getMapper(StockMapper.class);
            return mapper.selectAll();

        } finally {
            session.close();
        }
    }

    public static List<StockDailyPrice> selectByCondition(String code, String date){
        SqlSession session = GetSessionFactory.getSqlSessionFactory().openSession();
        try {

            StockDailyPriceMapper mapper = session.getMapper(StockDailyPriceMapper.class);
            return mapper.selectByCondition(code, date);

        } finally {
            session.close();
        }
    }

}