package com.ivanfranchin.orderapi.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Message {

    private String senderName;
    private String receiverName;
    private String message;
    private String media;
    private String mediaType;
    private Status status;
}
