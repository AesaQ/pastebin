package ru.aesaq.pastebin.generator;

import org.springframework.stereotype.Component;
import ru.aesaq.pastebin.entity.Hash;
import ru.aesaq.pastebin.repository.HashRepository;

import java.util.Base64;
import java.util.UUID;

@Component
public class UniqueLinkGenerator {
    private HashRepository hashRepository = null;

    public UniqueLinkGenerator(HashRepository hashRepository) {
        this.hashRepository = hashRepository;
    }

    public void generateUniqueLink(int length) {
        String uniqueValue = String.valueOf(UUID.randomUUID());
        Base64.Encoder URL_SAFE_ENCODER = Base64.getUrlEncoder().withoutPadding();

        String encodedLink = URL_SAFE_ENCODER.encodeToString(uniqueValue.getBytes());
        encodedLink = encodedLink.substring(0, Math.min(encodedLink.length(), length));

        while (isLinkExistsInDB(encodedLink)) {
            uniqueValue = String.valueOf(UUID.randomUUID());
            encodedLink = URL_SAFE_ENCODER.encodeToString(uniqueValue.getBytes());
            encodedLink = encodedLink.substring(0, Math.min(encodedLink.length(), length));
        }
        hashRepository.save(new Hash(encodedLink));
    }

    private boolean isLinkExistsInDB(String link) {
        return hashRepository.findByHash(link).isPresent();
    }
}
