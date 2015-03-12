#!/bin/sh -xv

deploy_artifacts_to_prod_tomcat() {
echo "************************************************"
echo "Building the rd project"
echo "************************************************"
mvn clean install -Dmaven.test.skip=true

echo "************************************************"
echo "Stopping tomcat"
echo "************************************************"
ssh $USER_NAME@$SERVER "sudo /sbin/service tomcat6 stop"

echo "************************************************"
echo "Backing up the old the project from Prod machine"
echo "************************************************"

dateTimeStamp=`date '+%d_%m_%y_%H_%M_%S'`

ssh $USER_NAME@$SERVER "sudo rm -Rvf $TOMCAT_WEBAPPS/$DES_WAR_FOLDER && echo $dateTimeStamp && mkdir $LOGS_BKP/$dateTimeStamp &&  mv -vf $TOMCAT_LOGS/* $LOGS_BKP/$dateTimeStamp/ && mkdir $WAR_BKP/$dateTimeStamp && mv -vf $TOMCAT_WEBAPPS/$DES_WAR $WAR_BKP/$dateTimeStamp/$DES_WAR"

echo "************************************************"
echo "Deploying the project to Prod machine"
echo "************************************************"
scp target/$SRC_WAR $USER_NAME@$SERVER:$NEW/$DES_WAR

echo "************************************************"
echo "Starting tomcat"
echo "************************************************"
ssh $USER_NAME@$SERVER "sudo mv $NEW/$DES_WAR $TOMCAT_WEBAPPS/$DES_WAR && sudo chown tomcat:rd $TOMCAT_WEBAPPS/$DES_WAR"
ssh $USER_NAME@$SERVER "sudo /sbin/service tomcat6 start"
}

TOMCAT_WEBAPPS="~/webapps"
TOMCAT_TEMP="~/temp"
NEW="~/temp/new"
TOMCAT_WORK="/usr/share/tomcat6/work"
TOMCAT_LOGS="~/logs"
SRC_WAR="rd.war"
SRC_WAR_FOLDER="rd"
DES_WAR="rd.war"
DES_WAR_FOLDER="rd"
WAR_BKP="~/temp/war_bkp"
LOGS_BKP="~/temp/logs_dump"
USER_NAME="mshenoy"
SERVER="ec2-204-236-220-80.compute-1.amazonaws.com"

case "$1" in
1)
	deploy_artifacts_to_prod_tomcat
	;;
    *)
        echo $"
            1.   Deploy artifact"
        RETVAL=1
        ;;
esac

exit $RETVAL
