package org.example.bilbottenbackend.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AIResponse {
    private List<Choice> choices;

    @Getter
    @Setter
    public static class Choice {
        private Message message;
    }

    @Getter
    @Setter
    public static class Message {
        private String role;
        private String content;
    }
}