. ./source.rc
HJAR=../target/hbasespark.jar
# java -Dlog4j.configuration=file:$PWD/log4j.properties  -cp $LIB:$HJAR HelloScala ./param.properties sparktest

PARAM=./param.properties

help() {
    echo "./run.sh <param>"
    echo " sparktest : Simple wordcount spark test"
    echo " loadhbase : Load textfile into hbase"
    echo " sparkhbase : Run hbase spark job"
    echo " sparkrddhbase : Run hbase-spark RDD job"
    exit 
}

readparam() {
  while IFS='=' read -r key value
  do
    case $key in 
      infile) INFILE=$value;;
      outfile) OUTFILE=$value;;
      *);;
    esac  
  done < $PARAM
#  echo $INFILE
#  echo $OUTFILE
}

removetemp() {
  hdfs dfs -rm -r $OUTFILE
}

runsparktest() {  
  removetemp
  local -r dire=`dirname $INFILE`
  hdfs dfs -mkdir -p $dire
  hdfs dfs -copyFromLocal $INFILE $INFILE
  spark-submit  --class HelloScala --master yarn --name ShakespeareWordCount $HJAR $PARAM sparktest    
}

runloadhbase() {
  java -Dlog4j.configuration=file:$PWD/log4j.properties  -cp $CONF:$LIB:$HJAR HelloScala $PARAM hbaseload
}

runsparkhbase() {
  local -r param=$1
  removetemp
  spark-submit --conf "spark.driver.extraClassPath=$CONF:$LIB" --class HelloScala --master yarn --name ShakespeareWordCount $HJAR $PARAM $param
}

readparam

case $1 in 
  sparktest) runsparktest;;
  loadhbase) runloadhbase;;
  sparkhbase) runsparkhbase hbasespark ;;
  sparkrddhbase) runsparkhbase hbaserddspark ;;
  *) help;;
esac