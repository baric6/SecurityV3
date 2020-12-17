package com.baric.securityv3;

public class ProgrammingdbModel {

    private String title;
    private String Comment;
    private String KeyRefrence;
    private String topic;
    private String id;

    public ProgrammingdbModel(String title, String comment, String KeyRefrence, String topic) {
        this.title = title;
        Comment = comment;
        this.KeyRefrence = KeyRefrence;
        this.topic = topic;
    }

    public ProgrammingdbModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getKeyRefrence() {
        return KeyRefrence;
    }

    public void setKeyRefrence(String url) {
        this.KeyRefrence = url;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProgrammingdbModel{" +
                "title='" + title + '\'' +
                ", Comment='" + Comment + '\'' +
                ", url='" + KeyRefrence + '\'' +
                ", topic='" + topic + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
