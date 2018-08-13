package ru.otus.khitrov;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.openmbean.CompositeData;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


class GCListener implements  javax.management.NotificationListener  {

    private static Logger log = Logger.getLogger(GCListener.class.getName());
    private final String gcName;
    private long startMinute, lastMinute, duration;
    private  int numOfCollect;

    GCListener(String name){
        gcName = name;
        startMinute = 0L;
        lastMinute = 0L;
        duration = 0L;
        numOfCollect = 0;
    }

    @Override
    public void handleNotification(Notification notification, Object handback) {

        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            //get the information associated with this notification
            GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

            startMinute = TimeUnit.MILLISECONDS.toMinutes(info.getGcInfo().getStartTime());

            if  ( lastMinute!= startMinute ) {
                if (numOfCollect > 0)
                  log.log(Level.INFO, "Minute = " + String.valueOf(lastMinute+1)
                                           + ", GC Type = " + gcName +
                                           ", number of collections = " + numOfCollect +
                                           ", duration = " + duration);
                numOfCollect = 0;
                duration  = 0;
                lastMinute = startMinute;
            }

            numOfCollect++;
            duration += info.getGcInfo().getDuration();
 /*
            System.out.println("TYPE: " + gcName + ": - " + info.getGcInfo().getId()+
                     " " + info.getGcName() + " (from " + info.getGcCause()+
                     ") "+duration + " milliseconds; start-end times "
                     + info.getGcInfo().getStartTime()+ "-"
                     + info.getGcInfo().getEndTime());
 */
        }
    }
}
