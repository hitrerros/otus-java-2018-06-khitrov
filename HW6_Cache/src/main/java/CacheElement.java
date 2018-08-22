import java.lang.ref.SoftReference;

public class CacheElement<T> extends SoftReference<T> {

    private final long creationTime;
    private long lastAccessTime;

      public CacheElement( T ref ) {
       super ( ref );
       this.creationTime = getCurrentTime();
       this.lastAccessTime = getCurrentTime();
   }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setAccessed() {
        lastAccessTime = getCurrentTime();
    }


}
