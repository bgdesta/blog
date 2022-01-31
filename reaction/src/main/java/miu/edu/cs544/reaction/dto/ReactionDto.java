package miu.edu.cs544.reaction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miu.edu.cs544.reaction.enums.ReactName;
import miu.edu.cs544.reaction.enums.ReactType;

import javax.validation.constraints.NotEmpty;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReactionDto {

    private long id;
    private long user_id;
    private long post_id;

    @NotEmpty(message = "Reaction name is mandatory")
    private ReactName reactName;
    @NotEmpty(message = "Reaction type is mandatory")
    private ReactType reactionTo;
}
