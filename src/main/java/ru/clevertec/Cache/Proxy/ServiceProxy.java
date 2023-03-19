package ru.clevertec.Cache.Proxy;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.clevertec.Cache.Cache;
import ru.clevertec.Cache.Factory.CacheFactory;
import ru.clevertec.Cache.Factory.CacheType;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class ServiceProxy {
    private Map<String, Cache> caches;
    private final int capacity;
    private final CacheType cacheType;

    /**
     * Constructor injects cache to field
     * @param cacheType algorithm implementation (LRU or LFU is available)
     * @param capacity max size of cache storage. <br/>
     * Params injected from application.yml file.
     */
    public ServiceProxy(@Value("${cache.algorithm}") CacheType cacheType,
                        @Value("${cache.capacity}") int capacity) {
        this.cacheType = cacheType;
        this.capacity = capacity;
        caches = new HashMap<>();
    }

    /**
     * Advice around method with {@link ru.clevertec.Cache.Annotations.Cacheable} annotation. <br/>
     * Method must follow next signature(name matching is unnecessary): T someName(int id),
     * i.e. get id and return entity(usual findById method).<br/>
     * Checks if cache contains object with passed id
     * then return value from cache, otherwise calls source method.
     * @return value from cache or from db
     */
    @Around("@annotation(ru.clevertec.Cache.Annotations.Cacheable))")
    public Object find(ProceedingJoinPoint pjp) {
        String name = buildName(pjp);
        Cache cache = caches.computeIfAbsent(name, v -> CacheFactory.getCache(cacheType, capacity));
        int id = Integer.parseInt(pjp.getArgs()[0].toString());
        Object value = cache.get(id);
        if(value == null) {
            try {
                value = pjp.proceed();
                cache.put(id, value);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * Advice around method with {@link ru.clevertec.Cache.Annotations.CachePut} annotation. <br/>
     * Method must follow next signature(name matching is unnecessary): {Object update(int id, Object value)}
     * i.e. get id and object and return nothing.
     * Usual update/create method. <br/>
     * Calls update in service and updates value in cache or just put value in cache.
     */
    @Around("@annotation(ru.clevertec.Cache.Annotations.CachePut))")
    public Object update(ProceedingJoinPoint joinPoint) {
        String name = buildName(joinPoint);
        Cache cache = caches.computeIfAbsent(name, v -> CacheFactory.getCache(cacheType, capacity));
        int id = Integer.parseInt(joinPoint.getArgs()[0].toString());
        Object value = joinPoint.getArgs()[1];
        cache.put(id, value);
        return value;
    }

    /**
     * Advice before method with {@link ru.clevertec.Cache.Annotations.CacheEvict} annotation. <br/>
     * Method must follow next signature(name matching is unnecessary): {T delete(int id)}.<br/>
     * Removes value from cache.
     * @return value from cache or from db
     */
    @Before("@annotation(ru.clevertec.Cache.Annotations.CacheEvict))")
    public void delete(JoinPoint joinPoint) {
        String name = buildName(joinPoint);
        Cache cache = caches.computeIfAbsent(name, v -> CacheFactory.getCache(cacheType, capacity));
        int id = Integer.parseInt(joinPoint.getArgs()[0].toString());
        cache.delete(id);
    }

    private String buildName(JoinPoint jp) {
        return jp.getClass().getName();
    }
}
