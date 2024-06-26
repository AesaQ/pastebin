package ru.aesaq.pastebin.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.aesaq.pastebin.generator.UniqueLinkGenerator;

@Component
public class LinkGeneratorRunner implements CommandLineRunner {
    private final UniqueLinkGenerator linkGenerator;

    public LinkGeneratorRunner(UniqueLinkGenerator linkGenerator) {
        this.linkGenerator = linkGenerator;
    }

    @Override
    @Async
    public void run(String... args) throws Exception {
        while (true) {
            while (linkGenerator.countByIsUsedFalse() < 100) {
                linkGenerator.generateUniqueLink(8);
                Thread.sleep(1000L);
            }
            Thread.sleep(1000L);
        }
    }
}
