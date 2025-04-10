package org.example.bilbottenbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AIRequest {
    private String model;
    private List<Message> messages;
    private double temperature;
    private double top_p;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message {
        private String role;
        private List<ContentPart> content;
    }

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ContentPart {
        private String type;
        private String text; // Only used when type == "text"
        private ImageUrl image_url; // Only used when type == "image_url"

        public ContentPart(){}
        public ContentPart(String type, String text, ImageUrl image_url){
            this.type = type;
            this.text = text;
            this.image_url = image_url;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageUrl {
        private String url;
    }
}