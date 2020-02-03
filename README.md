# ConnectHBase

A simple solution demonstrating connection to HBase in Spark application and Kerberos environment. It comes together with Scala source code.<br>
The program is a classic Word Count program implemented as Spark job. 

# Files description
* sh
  * sh/run.sh Bash script launcher
  * sh/resources
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

Customize source.rc<br>

| Variable | Default value | Description
| --- | --- | -- |
| LIB | /usr/hdp/current/hbase-client/lib/\*:/usr/hdp/current/spark2-client/jars/\* | Spark and HBase client libraries
| CONF | /etc/hadoop/conf:/etc/hbase/conf | Hadoop and HBase configuration

Customize param.properties

| Variable | Default value | Description
| --- | --- | -- |
| infile | resource/shakespeare.txt | Input text file in local file system and HDFS
| outfile | /tmp/shakespeareWordCount | HDFS result
| hbasetable | shakespeare | HBase table where input file is loaded
| hbasefamily | cf1 | Column family
| hbasecolumn | col | Column name





