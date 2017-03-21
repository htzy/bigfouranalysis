package com.huangshihe.sparkanalysis.demo

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by root on 3/21/17.
  */
object MyWordCount {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("My WordCount").setMaster("spark://hadoopcloud1:7077")
      .set("spark.submit.deployMode", "cluster")
    val sc = new SparkContext(conf)
    sc.addJar("/home/bigfour/workspaces/bigfouranalysis/sparkanalysis/target/bigfour-analysis.jar")
    val line = sc.textFile("hdfs://hadoopcloud1:9000/tmp/tracking-1.log")
    //  val result = line.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_).collect()
    val result = line.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_).take(100)
    result.foreach(println)
    sc.stop()
  }

}
