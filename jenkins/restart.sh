#!/bin/bash

echo ---------------------------------------------
echo "stop服务开始"
pidlist=`ps -ef|grep vein_scan_dms_api.*.jar | grep -v "grep"|awk '{print $2}'`
if [ "$pidlist" = "" ]; then
    echo "no vein_scan_dms_api pid alive"
else
    echo "vein_scan_dms_api Id list :$pidlist"
    for pid in ${pidlist}
    {
        kill -9 $pid
        echo "KILL $pid:"
        echo "service stop success"
    }
fi
echo "stop服务脚本结束"
echo ---------------------------------------------

echo "start服务脚本开始"
JAVA_HOME=/home/HiramHe/software/java/jdk1.8.0_212
classpath=$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt.jar
echo classpath=$classpath
dir=/home/HiramHe/jenkins_jars/vein_scan_dms_api
echo dir=$dir
echo "启动接口服务"
nohup $JAVA_HOME/bin/java -classpath $classpath -XX:-UseGCOverheadLimit -Xms256m -Xmx1024m -jar $dir/vein_scan_dms_api-1.0-SNAPSHOT.jar > $dir/log/$(date +'%Y%m%d').log 2>&1 &
echo "start服务脚本结束"
echo ---------------------------------------------