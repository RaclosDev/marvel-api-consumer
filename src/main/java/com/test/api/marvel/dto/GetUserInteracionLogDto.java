package com.test.api.marvel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetUserInteracionLogDto {

    Long id;
    String username;
    String url;
    String urlMethod;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
    LocalDateTime timestamp;

    String remoteAdress;

}
