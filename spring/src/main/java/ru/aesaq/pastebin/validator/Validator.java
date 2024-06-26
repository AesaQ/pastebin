package ru.aesaq.pastebin.validator;

import org.springframework.stereotype.Component;
import ru.aesaq.pastebin.entity.Post;

@Component
public class Validator {
    public String checkPost(Post post) {
        if (post.getUserId() == null) {
            return "error: user_id is null";
        }
        if (post.getTitle() == null) {
            return "error: title is null";
        }
        if (post.getContent() == null) {
            return "error: content is null";
        }
        return "ok";
    }
}
