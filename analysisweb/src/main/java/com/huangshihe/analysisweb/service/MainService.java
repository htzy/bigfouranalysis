package com.huangshihe.analysisweb.service;

import com.huangshihe.analysisweb.model.UserRecordPer;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * Created by huangshihe on 3/22/17.
 */
public class MainService {

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

    /**
     * 取得个人/总 的百分比
     *
     * @param username
     * @return
     */
    public UserRecordPer getUserRecordsPer(String username) {
        String userSql = "select * from user_records where username=? order by create_time desc";
        Record user = Db.findFirst(userSql, username);
        String totalSql = "select * from user_records_total order by create_time desc";
        Record total = Db.findFirst(totalSql);
        return new UserRecordPer(
                (user.getLong("agent").intValue() + 0.0) / total.getLong("agent").intValue(),
                (user.getLong("ip").intValue() + 0.0) / total.getLong("ip").intValue(),
                (user.getLong("event").intValue() + 0.0) / total.getLong("event").intValue(),
                (user.getLong("event_type").intValue() + 0.0) / total.getLong("event_type").intValue(),
                (user.getLong("count").intValue() + 0.0) / total.getLong("count").intValue(),
                username
        );
    }

    public String getTopUserRecords() {
        String userSql = " select username, count from user_records, "
                + " (select create_time from user_records order by create_time desc limit 1) as t"
                + " where user_records.create_time=t.create_time order by count desc limit 10 ";
        List<Record> recordList = Db.find(userSql);
        StringBuilder res = new StringBuilder("[");
        for (Record record : recordList) {
            res.append("['");
            res.append(record.getStr("username"));
            res.append("',");
            res.append(record.getLong("count"));
            res.append("],");
        }
        if (res.lastIndexOf(",") > 0) {
            res = res.replace(res.length() - 1, res.length(), "]");
        } else {
            res.append("]");
        }
        System.out.println(res.toString());
        return res.toString();
    }
}
