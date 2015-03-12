#!/bin/sh -xv
#set your tomcat root here
TOMCAT_ROOT="F:/RD/tools/apache-tomcat-7.0.42-windows-x86/apache-tomcat-7.0.42"

build_deploy_rd() {
echo "Building the rd project....."
mvn clean install

echo "************************************************"
echo "Copying the rd war to tomcat..."
echo "************************************************"

rm -Rvf $TOMCAT_WEBAPPS/rd
rm -Rvf $TOMCAT_LOGS/*
rm -Rvf $TOMCAT_WEBAPPS/rd.war
rm -Rvf $TOMCAT_WORK
echo "rd war deleted"
cp target/rd.war $TOMCAT_WEBAPPS/rd.war
echo "rd war deployed"

}

TOMCAT_WEBAPPS=$TOMCAT_ROOT/webapps
TOMCAT_TEMP=$TOMCAT_ROOT/temp
TOMCAT_WORK=$TOMCAT_ROOT/work
TOMCAT_LOGS=$TOMCAT_ROOT/logs

case "$1" in
1)
  build_deploy_rd
  ;;
    *)
        echo $"
        1.   Build deploy rd"
        RETVAL=1
        ;;
esac

exit $RETVAL
