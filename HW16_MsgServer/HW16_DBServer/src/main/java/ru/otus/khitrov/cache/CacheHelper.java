package ru.otus.khitrov.cache;


import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.stereotype.Component;
import ru.otus.khitrov.db.dataSets.DataSet;


@Component
 public  class CacheHelper<T extends DataSet>{

    private  final Cache<Long, T> dataSetCache;

    public CacheHelper() {

        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().withCache("users",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, DataSet.class,
                                ResourcePoolsBuilder.heap(100))
                                .build())
                .build(true);

        this.dataSetCache = (Cache<Long, T>) cacheManager.getCache("users", Long.class, DataSet.class);

    }

     public   T getValue(long id) {
      if (!dataSetCache.containsKey(id)) return  null;
          return  dataSetCache.get(id);
    }

    public   void  setValue(T user) {
        dataSetCache.put( user.getId(),  user  );
    }


}
