package com.huangshihe.analysisweb.service;

import com.huangshihe.analysisweb.model.UserRecordPer;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.Date;
import java.util.List;

/**
 * Created by root on 3/22/17.
 */
public class MainService {

//    public Record getUserRecordsPer(String username) {
//
//        try {
//            Record record = Db.findFirst("select * from user_records where username=? order by create_time desc", username);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return record;
//    }

    public UserRecordPer getTotalAvgPer() {
        String avgSql =
                "select avg(agent) as agent, avg(count) as count, avg(ip) as ip, avg(event) as event, avg(event_type) as event_type"
                        + " from "
                        + " (select agent, count, ip, event, event_type "
                        + " from user_records, "
                        + " (select create_time from user_records order by create_time desc limit 1) as t"
                        + " where user_records.create_time=t.create_time) as total ";

        Record avg = Db.findFirst(avgSql);
        String totalSql = "select * from user_records_total order by create_time desc";
        Record total = Db.findFirst(totalSql);

        return new UserRecordPer(
                avg.getBigDecimal("agent").doubleValue() / total.getLong("agent").intValue(),
                avg.getBigDecimal("ip").doubleValue() / total.getLong("ip").intValue(),
                avg.getBigDecimal("event").doubleValue() / total.getLong("event").intValue(),
                avg.getBigDecimal("event_type").doubleValue() / total.getLong("event_type").intValue(),
                avg.getBigDecimal("count").doubleValue() / total.getLong("count").intValue()
        );
    }

}