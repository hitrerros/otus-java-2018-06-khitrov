import java.lang.ref.SoftReference;
import java.util.*;
import java.util.function.Function;

public class MyCacheEngineImpl<K, V> implements MyCacheEngine<K, V> {

    Map<K, CacheElement<V>> storage = new LinkedHashMap<>();
    private static final int TIME_THRESHOLD_MS = 5;


    private final Timer timer = new Timer();
    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private int hit = 0;
    private int miss = 0;

    MyCacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.idleTimeMs = idleTimeMs;
        this.lifeTimeMs = lifeTimeMs;
        this.isEternal = isEternal;
    }

    @Override
    public void put(K key, V value) {

        if (storage.size() == maxElements) {
            K firstKey = storage.keySet().iterator().next();
            storage.remove(firstKey);
        }

        storage.put(key, new CacheElement<>(value));

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }

    }

    @Override
    public V get(K key) {

        V value = null;

        CacheElement<V> cacheElement = storage.get(key);
        if (cacheElement != null) {
            value = cacheElement.get();
            if (value != null) {
                hit++;
                cacheElement.setAccessed();
            } else {
                miss++;
            }
        } else {
            miss++;
        }

        return value;
    }


    @Override
    public int getHitCount() {
        return hit;
    }

    @Override
    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private TimerTask getTimerTask(final K key, Function<CacheElement<V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                CacheElement<V> element = storage.get(key);
                if (element == null
                        || element.get() == null
                        || isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis())) {
                    storage.remove(key);
                    this.cancel();
                }
            }
        };
    }


    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }

}
