package com.valeriygulin.ssemultichatjavafx.model;

import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Message {

    private long id;
    @NonNull
    private String content;

    private User user;


}
