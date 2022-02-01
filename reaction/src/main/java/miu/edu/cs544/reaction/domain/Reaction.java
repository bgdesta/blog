package miu.edu.cs544.reaction.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miu.edu.cs544.reaction.enums.ReactionName;
import miu.edu.cs544.reaction.enums.ReactionType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Reaction {
    @Id
    @GeneratedValue
    private long reactid;


    private long userid;
    private long postid;

    private Date Createdat;
    private Date updatedat;

    @NotNull(message = "Reaction name is mandatory")

    private ReactionName reactionname;
    @NotNull(message = "Reaction type is mandatory")
    private ReactionType reactionto;

    public Reaction(long userid, long postid, ReactionName reactionName, ReactionType reactionTo) {

        this.userid = userid;
        this.postid = postid;


        this.reactionname = reactionName;
        this.reactionto = reactionTo;

        this.Createdat = new Date();
        this.updatedat = new Date();
    }
}
