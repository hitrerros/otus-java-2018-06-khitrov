package ru.otus.khitrov.cache;


import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.stereotype.Component;
import ru.otus.khitrov.base.dataSets.UserDataSet;

@Component
public class CacheHelper {

    private  final Cache<Long, UserDataSet> userDataSetCache;

    public CacheHelper() {

        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().withCache("users",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, UserDataSet.class,
                                ResourcePoolsBuilder.heap(100))
                                .build())
                .build(true);

        this.userDataSetCache = cacheManager.getCache("users", Long.class, UserDataSet.class);

    }

    public UserDataSet getValue(long id) {
      if (!userDataSetCache.containsKey(id)) return  null;
          return userDataSetCache.get(id);
    }

    public void  setValue(UserDataSet user) {
        userDataSetCache.put( user.getId(), user  );
    }


}
