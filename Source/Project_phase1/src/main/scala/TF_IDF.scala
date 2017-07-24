/**
  * Created by rohithkumar on 6/23/17.
  */


import org.apache.spark._
import org.apache.spark.mllib.feature._

import scala.collection.immutable.HashMap


object TF_IDF {

  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "/usr/local/Cellar/apache-spark/2.1.0/bin/")

    val sparkConf = new SparkConf().setAppName("TF-IDF").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    //Reading the Text File
    val documents = sc.textFile("/Users/satheeshchandra/Documents/Github/CS5560_KDM_Project/KDM_Project/project/data/mylab")

    //Getting the Lemmatised form of the words in TextFile
    val documentseq = documents.map(f => {
      val lemmatised = CoreNLP.returnLemma(f)
      val splitString = lemmatised.split(" ")
      splitString.toSeq
    })

    //Creating an object of HashingTF Class
    val hashingTF = new HashingTF()

    //Creating Term Frequency of the document
    val tf = hashingTF.transform(documentseq)
    tf.cache()


    val idf = new IDF().fit(tf)

    //Creating Inverse Document Frequency
    val tfidf = idf.transform(tf)

    val tfidfvalues = tfidf.flatMap(f => {
      val ff: Array[String] = f.toString.replace(",[", ";").split(";")
      val values = ff(2).replace("]", "").replace(")", "").split(",")
      values
    })

    val tfidfindex = tfidf.flatMap(f => {
      val ff: Array[String] = f.toString.replace(",[", ";").split(";")
      val indices = ff(1).replace("]", "").replace(")", "").split(",")
      indices
    })

    tfidf.foreach(f => println(f))

    val tfidfData = tfidfindex.zip(tfidfvalues)

    var hm = new HashMap[String, Double]

    tfidfData.collect().foreach(f => {
      hm += f._1 -> f._2.toDouble
    })

    val mapp = sc.broadcast(hm)

    val documentData = documentseq.flatMap(_.toList)
    val dd = documentData.map(f => {
      val i = hashingTF.indexOf(f)
      val h = mapp.value
      (f, h(i.toString))
    })

    val dd1 = dd.distinct().sortBy(_._2, false)
    dd1.take(20).foreach(f => {
      println(f)
    })

  }

}

