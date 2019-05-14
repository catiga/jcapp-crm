#!/bin/bash
 
if [ "$JAVA_HOME" != "" ]; then
  #echo "run java in $JAVA_HOME"
  JAVA_HOME=$JAVA_HOME
fi
 
if [ "$JAVA_HOME" = "" ]; then
  echo "Error: JAVA_HOME is not set."
  exit 1
fi
 
JAVA=$JAVA_HOME/bin/java
SERVER_HOME=$BASE_DIR
BASE_HOME=$BASE_DIR
BASE_NAME="ALARM"
STARTUP_CLASS="com.starit.ipran.socket.ServerStart"
 
#SERVER JMX port
export JMX_PORT=9232
HOST_NAME=`hostname`
SERVER_NAME=$BASE_NAME-$HOST_NAME-$JMX_PORT
#export CLASSPATH=$BASE_DIR/conf:$(ls $BASE_DIR/lib/*.jar | tr '\n' :)
export CLASSPATH=$BASE_DIR/conf:$BASE_DIR/lib/*
 
#SERVER jvm args
SERVER_JVM_ARGS="-Xmx2048m -Xms1024m"
SERVER_APP_ARGS="-DBASE_HOME=$BASE_HOME -DSERVER_NAME=$SERVER_NAME"
SERVER_JMX_ARGS="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=$JMX_PORT"
SERVER_JVM_ARGS="$SERVER_JVM_ARGS -cp $CLASSPATH $SERVER_JMX_ARGS $SERVER_APP_ARGS"
if [ -z "$SERVER_ARGS" ]; then
  export SERVER_ARGS="$SERVER_JVM_ARGS"
fi
