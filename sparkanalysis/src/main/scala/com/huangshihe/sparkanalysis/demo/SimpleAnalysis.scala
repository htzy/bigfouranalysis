package com.huangshihe.sparkanalysis.demo

import java.util.Properties

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.types._

/**
  * Created by root on 3/21/17.
  */


object SimpleAnalysis {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("SimpleAnalysis").master("spark://hadoopcloud1:7077")
      .config("park.submit.deployMode", "cluster").getOrCreate()
    spark.sparkContext.addJar("/home/bigfour/mysql-connector-java-5.1.41/mysql-connector-java-5.1.41-bin.jar")
    val df = spark.read.json("hdfs://hadoopcloud1:9000/tmp/tracking-1.log")
    // mysql config, this ip(mysql server address) and port can ping or telnet to spark cluster
    val jdbcUrl = "jdbc:mysql://192.168.4.4:3306/"

    val connectProperties = new Properties()
    connectProperties.put("user", "root")
    connectProperties.put("password", "root")
    connectProperties.put("driver", "com.mysql.jdbc.Driver")
    // count user operates
    val userOperates = df.groupBy("username").count()
    userOperates.write.mode(SaveMode.Append).jdbc(jdbcUrl, "bigfouranalysis.simple", connectProperties)

    val schema = StructType(StructField("username", StringType)::StructField("count", LongType)::Nil)
    // if you want to change schema
    val userOperatesTable = spark.sqlContext.createDataFrame(userOperates.rdd, schema)
    userOperatesTable.write.mode(SaveMode.Append).jdbc(jdbcUrl, "bigfouranalysis.simpleUser", connectProperties)
  }

}
