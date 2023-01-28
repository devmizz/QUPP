package com.qupp.mail.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseMessage {
    private String to;
    private String subject;
    private String message;
}
