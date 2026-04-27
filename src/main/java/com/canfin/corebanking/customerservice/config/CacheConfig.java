package com.canfin.corebanking.customerservice.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {

    public static final String CUSTOMER_CACHE = "customers";
    public static final String APPROVED_CUSTOMERS_CACHE = "approvedCustomers";
    public static final String SAVINGS_ACCOUNT_CACHE = "savingsAccounts";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CUSTOMER_CACHE, APPROVED_CUSTOMERS_CACHE, SAVINGS_ACCOUNT_CACHE);
    }

    // Evict all caches every 10 minutes to prevent stale data
    @Scheduled(fixedRate = 600000)
    public void evictAllCaches() {
        CacheManager cm = cacheManager();
        cm.getCacheNames().forEach(name -> cm.getCache(name).clear());
    }
}
