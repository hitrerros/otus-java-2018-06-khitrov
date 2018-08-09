package ru.otus.khitrov;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.openmbean.CompositeData;


class GCListener implements  javax.management.NotificationListener  {

    private long totalGcDuration;
    private int  youngCollect;
    private int oldCollect;

    private final String gcName;

    GCListener(String name){
        gcName = name;
    }

    @Override
    public void handleNotification(Notification notification, Object handback) {

        System.out.println("GC Handled!");

        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            //get the information associated with this notification
            GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
            //get all the info and pretty print it
            long duration = info.getGcInfo().getDuration();
            String gctype = info.getGcAction();
            if ("end of minor GC".equals(gctype)) {
                gctype = "YOUNG";
                youngCollect++;
            } else if ("end of major GC".equals(gctype)) {
                gctype = "OLD";
                oldCollect++;
            }

            System.out.println("TYPE: " + gctype + ": - " + info.getGcInfo().getId()+
                    " " + info.getGcName() + " (from " + info.getGcCause()+
                    ") "+duration + " milliseconds; start-end times "
                    + info.getGcInfo().getStartTime()+ "-"
                    + info.getGcInfo().getEndTime());

            totalGcDuration += info.getGcInfo().getDuration();
            long percent = totalGcDuration*1000L/info.getGcInfo().getEndTime();

        }

    }

    public void GetFinalStatistics(){

        System.out.println("Type: " + gcName );
        if (youngCollect > 0)  System.out.println("Young collections: " + youngCollect);
        if (oldCollect > 0)    System.out.println("Old   collections: " + oldCollect);
        System.out.println("Total duration:" + totalGcDuration);

    }

}
