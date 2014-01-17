#!/bin/bash
# 
# while true ; do
#    if ifconfig wlan0 | grep -q "inet addr:" ; then
#       sleep 240
#    else
#       echo "Network connection down! Attempting reconnection."
#       ifup --force wlan0
#       sleep 10
#    fi
# done

 
LOGFILE=/home/pi/prayertime/Logs/network-monitor.log
 
if ifconfig eth0 | grep -q "inet addr:" ;
then
        echo "$(date "+%m %d %Y %T") : Ethernet OK" >> $LOGFILE
else
        echo "$(date "+%m %d %Y %T") : Ethernet connection down! Attempting reconnection." >> $LOGFILE
        ifup --force eth0
        OUT=$? #save exit status of last command to decide what to do next
        if [ $OUT -eq 0 ] ; then
                STATE=$(ifconfig eth0 | grep "inet addr:")
                echo "$(date "+%m %d %Y %T") : Network connection reset. Current state is" $STATE >> $LOGFILE
        else
                echo "$(date "+%m %d %Y %T") : Failed to reset ethernet connection" >> $LOGFILE
        fi
fi