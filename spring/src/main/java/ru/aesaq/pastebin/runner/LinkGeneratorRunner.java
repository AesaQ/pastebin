package ru.aesaq.pastebin.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.aesaq.pastebin.generator.UniqueLinkGenerator;

@Component
public class LinkGeneratorRunner implements CommandLineRunner {
    private final UniqueLinkGenerator linkGenerator;

    public LinkGeneratorRunner(UniqueLinkGenerator linkGenerator) {
        this.linkGenerator = linkGenerator;
    }

    @Override
    public void run(String... args) throws Exception {
        Thread thread = new Thread(() -> {
            while (true) {
                while (linkGenerator.countByIsUsedFalse() < 100) {
                    linkGenerator.generateUniqueLink(8);
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }
}
