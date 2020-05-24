package com.example.mrinal_raj;

public class MessageModel {
    private String message_content;
    private String sent_date_time;
    private String type;

    public MessageModel() {
        message_content = "This message is being been fetched!!!";
        //senders_profile_pic = "https://eitrawmaterials.eu/wp-content/uploads/2016/09/person-icon.png";
        type = "text";
    }

    public MessageModel(String message_content, String sent_date_time, String type) {
        this.message_content = message_content;
        this.sent_date_time = sent_date_time;
        this.type = type;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public String getSent_date_time() {
        return sent_date_time;
    }

    public void setSent_date_time(String sent_date_time) {
        this.sent_date_time = sent_date_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
