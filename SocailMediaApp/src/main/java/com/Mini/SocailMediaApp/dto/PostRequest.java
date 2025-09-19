package com.Mini.SocailMediaApp.dto;

public class PostRequest {
    private String content;
    private String imageUrl; // optional


    public PostRequest() {}
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
