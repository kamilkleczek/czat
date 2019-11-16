package taborski.kleczek.chat.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class History {
    private Long id;
    private String message;
    private Long senderId;
}
