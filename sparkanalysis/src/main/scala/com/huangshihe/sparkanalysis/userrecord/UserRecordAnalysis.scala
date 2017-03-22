package com.huangshihe.sparkanalysis.userrecord

import java.util.Properties

import org.apache.spark.sql._
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}

/**
  * Created by root on 3/21/17.
  */
object UserRecordAnalysis {

  val spark: SparkSession = SparkSession.builder().appName("UserRecordAnalysis").master("spark://hadoopcloud1:7077")
    .config("park.submit.deployMode", "cluster").getOrCreate()

  import spark.implicits._

  spark.sparkContext.addJar("/home/bigfour/mysql-connector-java-5.1.41/mysql-connector-java-5.1.41-bin.jar")

  spark.sparkContext.addJar("/home/bigfour/workspaces/bigfouranalysis/sparkanalysis/target/bigfour-analysis.jar")

  val df: DataFrame = spark.read.json("hdfs://hadoopcloud1:9000/tmp/tracking-1.log")
  //    [event_source, name, host, ip, session, context, page, time, event_type, referer, agent, event, _corrupt_record, accept_language, username]

  // mysql config, this ip(mysql server address) and port can ping or telnet to spark cluster
  val jdbcUrl = "jdbc:mysql://192.168.4.4:3306/"

  val connectProperties = new Properties()
  connectProperties.put("user", "root")
  connectProperties.put("password", "root")
  connectProperties.put("driver", "com.mysql.jdbc.Driver")

  def main(args: Array[String]): Unit = {
    analysis()
    analysisTotal()
  }

  /**
    * 分析统计内容：对于每个人出现的agent,ip,event,event_type出现的类别进行统计
    */
  def analysis(): Unit = {
    val validRecords = getValidUserRecords
    val agents = validRecords.select("username", "agent").groupBy("username", "agent").count().createOrReplaceTempView("b")

    spark.sql("select username, count(agent) as agent, sum(count) as count from b group by username").createOrReplaceTempView("c")

    val ips = validRecords.select("username", "ip").groupBy("username", "ip").count().createOrReplaceTempView("d")

    spark.sql("select c.*, count(d.ip) as ip from c, d where c.username=d.username group by c.username, c.agent, c.count").createOrReplaceTempView("e")

    val events = validRecords.select("username", "event").groupBy("username", "event").count().createOrReplaceTempView("f")

    spark.sql("select e.*, count(f.event) as event from e, f where e.username=f.username group by e.username, e.agent, e.count, e.ip").createOrReplaceTempView("g")

    val event_types = validRecords.select("username", "event_type").groupBy("username", "event_type").count().createOrReplaceTempView("h")

    val finalTable = spark.sql("select g.*, count(h.event_type) as event_type, now() as create_time from g,h where g.username=h.username group by g.username,g.agent,g.count,g.ip,g.event")

    finalTable.write.mode(SaveMode.Append).jdbc(jdbcUrl, "bigfouranalysis.user_records", connectProperties)


    //    val counts = validRecords.groupBy("username").count().createOrReplaceTempView("a")
    //    val agents = validRecords.select("username", "agent").groupBy("username", "agent").count().createOrReplaceTempView("b")
    //    val ips = validRecords.select("username", "ip").groupBy("username", "ip").count().createOrReplaceTempView("c")
    //
    //    spark.sqlContext.sql("select a.username as username, count(b.agent) as agent, count(c.ip) as ip, a.count as count " +
    //      "from a,b where a.username=b.username and a.username=c.username " +
    //      "GROUP BY a.username, a.count").show()

    //    val table=validRecords.select("username","agent","ip","event","event_type").createOrReplaceTempView("a")
    //    spark.sqlContext.sql("select username, count(agent), count(ip), count(event), count(event_type) from a group by username").show()

    //    select username, count(agent), sum(count) from b GROUP BY username;
    //    val counts = validRecords.groupBy("username").count()
    //    val agents = validRecords.select("username", "agent").groupBy("username", "agent").count()
    //    counts.join(agents, counts("username") === agents("username"), "left").select("")


    /////////////////////////
    //    val list = users.count().collectAsList()

    //    users.count().collectAsList.(row=>{
    //      var username = row.getString(row.fieldIndex("username"))
    //      analysis(username)
    //    })
    //    users.count().foreach(row => {
    //      var username = row.getString(row.fieldIndex("username"))
    //      analysis(username)
    //    })
  }

  /**
    * 分析统计agent,ip,event,event_type出现的种类数
    */
  def analysisTotal(): Unit = {
    val validRecords = getValidUserRecords
    val records = validRecords.select("agent", "ip", "event", "event_type")
    val agents = records.groupBy("agent").count().selectExpr("count(1) as agent")
    val events = records.groupBy("event").count().selectExpr("count(1) as event")
    val event_types = records.groupBy("event_type").count().selectExpr("count(1) as event_type")
    val ips = records.groupBy("ip").count().selectExpr("count(1) as ip")
    val counts = records.selectExpr("count(1) as count")
    val finalTable = agents.crossJoin(events).crossJoin(event_types).crossJoin(ips).crossJoin(counts).createOrReplaceTempView("i")

    spark.sql("select i.*, now() as create_time from i")
      .write.mode(SaveMode.Append).jdbc(jdbcUrl, "bigfouranalysis.user_records_total", connectProperties)




//    records.groupBy("agent").count().selectExpr("count(1) as agent").union(records.groupBy("event").count().selectExpr("count(1) as event")).show()
//    println("agent:" + validRecords.groupBy("agent").count().count())
//    println("count:" + validRecords.count())
//    println("ip" + validRecords.groupBy("ip").count().count())
//    println("event:" + validRecords.groupBy("event").count().count())
//    println("event_type:" + validRecords.groupBy("event_type").count().count())
//val row = Row("agent_count", "ip_count")

  }


  //  def analysis(username: String): Unit = {
  //    val validRecords = getValidUserRecords
  //    println("username:" + username + ", agent:" + getTypeCounts(validRecords, username, "agent") + ", total:" + getTypeCounts(validRecords, "agent"))
  //    println("username:" + username + ", course_id:" + getTypeCounts(validRecords, username, "course_id") + ", total:" + getTypeCounts(validRecords, "course_id"))
  //    println("username:" + username + ", event:" + getTypeCounts(validRecords, username, "event") + ", total:" + getTypeCounts(validRecords, "event"))
  //    println("username:" + username + ", event_type:" + getTypeCounts(validRecords, username, "event_type") + ", total:" + getTypeCounts(validRecords, "event_type"))
  //  }
  //
  //  // 获得该用户（username）的col列的种类数
  //  def getTypeCounts(allRecords: DataFrame, username: String, col: String): Long = {
  //    allRecords.where("username=" + username.trim).select(col).distinct().count()
  //  }
  //
  //  // 获得总用户的col列的种类数
  //  def getTypeCounts(allRecords: DataFrame, col: String): Long = {
  //    allRecords.select(col).distinct().count()
  //  }

  // get valid user record
  def getValidUserRecords: DataFrame = {
    df.filter(record => record.fieldIndex("username") > 0)
      .filter(record => record.getString(record.fieldIndex("username")) != null)
      .filter(record => record.getString(record.fieldIndex("username")).nonEmpty)
  }
}
