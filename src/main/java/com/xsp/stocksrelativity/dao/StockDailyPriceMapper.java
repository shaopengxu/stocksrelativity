package com.xsp.stocksrelativity.dao;

import com.xsp.stocksrelativity.entity.StockDailyPrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Shaopeng.Xu on 2016-10-12.
 */
public interface StockDailyPriceMapper {

    List<StockDailyPrice> selectAll();

    List<StockDailyPrice> selectByCondition(@Param("code") String code);

    int insert(StockDailyPrice stockDailyPrice);

}
