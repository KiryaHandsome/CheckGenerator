package ru.clevertec.Cache.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.clevertec.Cache.Cache;
import ru.clevertec.Cache.CacheFactory;
import ru.clevertec.Cache.CacheType;
import ru.clevertec.Model.Product;

@Component
@Aspect
public class ProductServiceAspect {
    private Cache<Product> cache;

    /**
     * Constructor injects cache to field
     * @param type algorithm implementation (LRU or LFU is available)
     * @param capacity max size of cache storage. <br/>
     * Params injected from application.yml file.
     */
    public ProductServiceAspect(@Value("${cache.algorithm}") CacheType type,
                                @Value("${cache.capacity}") int capacity) {
        this.cache = CacheFactory.getCache(type, capacity);
    }

    /**
     * Advice around AbstractShopService.create(). <br/>
     * Put value to cache after AbstractShopService
     * creates new object in db.
     * @return value returned by ProductService
     */
    @Around("execution(* ru.clevertec.Service.AbstractShopService.create(..))")
    public Product create(ProceedingJoinPoint proceedingJoinPoint) {
        Product value = null;
        try {
            value = (Product) proceedingJoinPoint.proceed();
            cache.put((int) value.getId(), value);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Advice around AbstractShopService.find(). <br/>
     * Check if cache contains object with passed id
     * then return value from cache, otherwise call AbstractShopService.find()
     * @return value from cache or from db
     */
    @Around("execution(* ru.clevertec.Service.AbstractShopService.find(..))")
    public Product find(ProceedingJoinPoint proceedingJoinPoint) {
        int id = (int) proceedingJoinPoint.getArgs()[0];
        Product product = cache.get(id);
        if(product == null) {
            try {
                product = (Product) proceedingJoinPoint.proceed();
                cache.put((int) product.getId(), product);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        return product;
    }

    /**
     * Advice before ProductService.update(). <br/>
     * Calls update in service and updates value in cache.
     */
    @Before("execution(* ru.clevertec.Service.Implementation.ProductService.update(..))")
    public void update(JoinPoint joinPoint) {
        long id = (long) joinPoint.getArgs()[0];
        Product product = (Product) joinPoint.getArgs()[1];
        product.setId(id);
        cache.put(id, product);
    }

    /**
     * Advice before ProductService.delete(). <br/>
     * Removes value from cache.
     */
    @Before("execution(* ru.clevertec.Service.AbstractShopService.delete(..))")
    public void delete(JoinPoint joinPoint) {
        long id = (long) joinPoint.getArgs()[0];
        cache.delete(id);
    }
}
