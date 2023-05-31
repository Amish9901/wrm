#!/bin/bash

BASEDIR=$(dirname "$0")
cd $BASEDIR
mydir=$(pwd)
cd $OLDPWD

pid=${mydir}/server.pid
out=${mydir}/out.out


case "$1" in
start)
   nohup java -Dapi-package=api -cp "./Config;./lib;./lib/*" com.edriving.commons.rest.boot.server.WebServer 2>&1 &
     echo $! > ${pid}
     ;;
debug)
   nohup java -Dapi-package=api -Xmx1536m -XX:-OmitStackTraceInFastThrow -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -cp "./config;./lib;./lib/*" com.edriving.commons.web.server.WebServer > ${out} 2>&1&
   echo $! >${pid}
   ;;
stop)
   kill `cat ${pid}`
   rm ${pid}
   ;;
status)
   if [ -e ${pid} ]; then
      echo $0 is running, pid=`cat ${pid}`
   else
      echo $0 is NOT running
      exit 1
   fi
   ;;
*)
   echo "Usage: $0 {start|stop|status|debug}"
esac

exit 0
