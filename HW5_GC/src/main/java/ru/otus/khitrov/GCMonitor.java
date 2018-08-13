package ru.otus.khitrov;

import javax.management.NotificationEmitter;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class GCMonitor {

    public void runGCMonitor(){

        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();

        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println(gcbean);
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            GCListener listener = new GCListener( gcbean.getName() );
            emitter.addNotificationListener(listener,null,null);
        }
    }


@SuppressWarnings("InfiniteLoopStatement")
    public static void main(String...args) throws InterruptedException {

    int size = 100_000;

    try {
        LogManager.getLogManager().readConfiguration(
                GCMonitor.class.getResourceAsStream("/logging.properties"));
    } catch (IOException e) {
        System.err.println("Could not setup logger configuration: " + e.toString());
    }

        System.out.println("Running GC monitor, PID = " + ManagementFactory.getRuntimeMXBean().getName());
        new GCMonitor().runGCMonitor();

        while (true) {

            Object[] array = new Object[size];

            for (int i = 0; i < array.length; i++) {
                array[i] = new Object();
            }

            Thread.sleep(1000);

            for (int i = 0; i < array.length / 2; i++) {
                array[i] = null;
            }

            size += 100000;
        }
    }
}


