# ConnectHBase

A simple solution demonstrating a connection to HBase in Spark application and Kerberos environment. It comes together with Scala source code.<br>
The program is a classic Word Count program implemented as Spark job.<br>
There are four jobs available.
* sparktest Simple WordCount program used to test Spark execution
* loadhbase Standalone program, loads input file into HBase table
* sparkhbase Recreates the text file from HBase table and runs WordCount
* sparkrddhbase Creates RDD from HBase table using hbase-spark client and runs WordCount

# Files description
* sh
  * sh/run.sh Bash script launcher
  * sh/resource
    * sh/resource/shakespeare.txt Input text file
  * template
    * log4j.properties
    * param.properties Template param to customize
    * source.rc Template environment variables definition
* src Scala/Spark/HBase source code
* target/hbasespark.jar Compiled source code
  
# Installation

Clone the repository<br>
>git clone https://github.com/stanislawbartkowski/ConnectHBase.git<br>
>cd ConnectHBase/sh<br>
>cp template/* .

# Customize

## Customize source.rc

| Variable | Default value | Description
| --- | --- | -- |
| LIB | /usr/hdp/current/spark2-client/jars/\*:/usr/hdp/current/hbase-client/lib/\* | Spark and HBase client libraries
| CONF | /etc/hadoop/conf:/etc/hbase/conf | Hadoop and HBase configuration

**Important**: the order in LIB variable matters. *spark2-client* libraries should precede *hbase-client* libraries.

## Customize param.properties

| Variable | Default value | Description
| --- | --- | -- |
| infile | resource/shakespeare.txt | Input text file in local file system and HDFS
| outfile | /tmp/shakespeareWordCount | HDFS result
| hbasetable | shakespeare | HBase table where input file is loaded
| hbasefamily | cf1 | Column family
| hbasecolumn | col | Column name

# Kerberos 
Make sure the user running the test is authenticated and valid Kerberos ticket is obtained.
# sparktest
A simple WordCount Spark job.<br>
>  ./run.sh sparktest
# loadhbase
Loads input text file into HBase table.<br>
>  ./run.sh loadhbase
# sparkhbase
Recreates the text file from HBase table and run WordCount<br>
>  ./run.sh sparkhbase
# sparkrddhbase
Recreates the RDD using spark-hbase connector and run WordCount.
>  ./run.sh sparkrddhbase

