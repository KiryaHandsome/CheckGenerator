package ru.clevertec.Cache.Aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.clevertec.Cache.Cache;
import ru.clevertec.Cache.CacheFactory;
import ru.clevertec.Cache.CacheType;
import ru.clevertec.Models.Product;

@Component
@Aspect
public class ProductServiceAspect {
    private Cache<Product> cache;

    public ProductServiceAspect(@Value("${cache.algorithm}") CacheType type,
                                @Value("${cache.capacity}") int capacity) {
        this.cache = CacheFactory.getCache(type, capacity);
    }

    @Around("execution(* ru.clevertec.Services.AbstractShopService.create(..))")
    public Product create(ProceedingJoinPoint proceedingJoinPoint) {
        Product value = null;
        try {
            value = (Product) proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        cache.put((int) value.getId(), value);
        return value;
    }

    @Around("execution(* ru.clevertec.Services.AbstractShopService.find(..))")
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

    @Before("execution(* ru.clevertec.Services.Implementations.ProductService.update(..))")
    public void update(JoinPoint joinPoint) {
        long id = (long) joinPoint.getArgs()[0];
        Product product = (Product) joinPoint.getArgs()[1];
        product.setId(id);
        cache.put(id, product);
    }

    @Before("execution(* ru.clevertec.Services.AbstractShopService.delete(..))")
    public void delete(JoinPoint joinPoint) {
        long id = (long) joinPoint.getArgs()[0];
        cache.delete(id);
    }
}
