package com.xsp.stocksrelativity.execute;

import com.xsp.stocksrelativity.Stock;
import com.xsp.stocksrelativity.StockDataDownload;
import com.xsp.stocksrelativity.entity.StockDailyPrice;
import com.xsp.stocksrelativity.dao.StockDailyPriceMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by zhangxiong on 2016-10-12.
 */
public class TestDao {

    public static void insertStockDailyPrice(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        try {

            StockDailyPriceMapper mapper = session.getMapper(StockDailyPriceMapper.class);
            StocksTest.loadStocks();
            for (String code : StocksTest.map.keySet()) {
                List<StockDailyPrice> list = StocksTest.map.get(code);
                for (StockDailyPrice sdp : list) {
                    sdp.setCode(code);
                    sdp.setDate(sdp.getDate().replaceAll("\\-", ""));
                    int count = mapper.insert(sdp);

                }
                session.commit();
                System.out.println(code);
            }

        } finally {

            session.close();
        }
    }

    public static void insertStockInfos() {

    }


}
