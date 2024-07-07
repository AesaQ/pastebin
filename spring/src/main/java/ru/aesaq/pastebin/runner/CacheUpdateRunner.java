package ru.aesaq.pastebin.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import ru.aesaq.pastebin.repository.HashRepository;
import ru.aesaq.pastebin.repository.PostRepository;
import ru.aesaq.pastebin.service.CacheService;


@Component
public class CacheUpdateRunner implements ApplicationRunner {
    private final CacheService cacheService;
    private final CacheManager cacheManager;

    private final PostRepository postRepository;
    private final HashRepository hashRepository;

    public CacheUpdateRunner(CacheService cacheService, CacheManager cacheManager, PostRepository postRepository, HashRepository hashRepository) {
        this.cacheService = cacheService;
        this.cacheManager = cacheManager;
        this.postRepository = postRepository;
        this.hashRepository = hashRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Thread thread = new Thread(() -> {
            cacheService.getPopularPosts();
            System.out.println(cacheManager.getCache("hash_post"));
            while (true) {
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                cacheService.updatePopularPosts();
            }
        });
        thread.start();
    }
}
