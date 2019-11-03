package taborski.kleczek.chat.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String password;
}
