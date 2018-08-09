package ru.otus.khitrov;

import javax.management.NotificationEmitter;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public class GCMonitor {

    private  List<GCListener> listeners = new ArrayList<>();

    public void runGCMonitor(){

        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();

        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println(gcbean);
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            GCListener listener = new GCListener( gcbean.getName() );
            listeners.add(listener);
            emitter.addNotificationListener(listener,null,null);
        }
    }

    public void readListeners(){
        for (GCListener listener : listeners){
            listener.GetFinalStatistics();
        }

    }

@SuppressWarnings("InfiniteLoopStatement")
    public static void main(String...args) throws InterruptedException {

    int size = 100_000;

    System.out.println("Running GC monitor, PID = " + ManagementFactory.getRuntimeMXBean().getName());
    GCMonitor gcMonitor = new GCMonitor();
    gcMonitor.runGCMonitor();

    try  {

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
    } catch (OutOfMemoryError out) {
          Thread.sleep(100);
          gcMonitor.readListeners();
        }
    }
}

