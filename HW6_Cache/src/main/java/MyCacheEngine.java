public interface MyCacheEngine <K, V>  {

    void put(K key, V value);

    V get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();

}
