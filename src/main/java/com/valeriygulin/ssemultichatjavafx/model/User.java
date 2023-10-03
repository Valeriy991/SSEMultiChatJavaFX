package com.valeriygulin.ssemultichatjavafx.model;


import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User {

    private long id;

    @NonNull
    private String login;

    @NonNull
    private String password;


}
