package ru.aesaq.pastebin.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.aesaq.pastebin.service.CacheService;


@Component
public class CacheUpdateRunner implements ApplicationRunner {
    private final CacheService cacheService;

    public CacheUpdateRunner(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Thread thread = new Thread(() -> {
            cacheService.getPopularPosts();
            while (true) {
                try {
                    Thread.sleep(300000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                cacheService.updatePopularPosts();
            }
        });
        thread.start();
    }
}
