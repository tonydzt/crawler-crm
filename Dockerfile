FROM java:8
ADD target/crawler-crm-1.0-SNAPSHOT.jar  /data0/webroot/
CMD ["java -jar /data0/webroot/crawler-crm-1.0-SNAPSHOT.jar "]