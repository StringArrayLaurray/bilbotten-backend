package org.example.bilbottenbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class AIRequest {
    private String model;
    private List<Message> messages;
    private double temperature;
    private double top_p;

    public AIRequest(){}
    public AIRequest(String model, List<Message> messages, double temperature, double top_p) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
        this.top_p = top_p;
    }

    @Setter
    @Getter
    public static class Message {
        private String role;
        private List<ContentPart> content;

        public Message(){}
        public Message(String role, List<ContentPart> content){
            this.role = role;
            this.content = content;
        }
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
    public static class ImageUrl {
        private String url;

        public ImageUrl(){}
        public ImageUrl(String url){
            this.url = url;
        }
    }


}