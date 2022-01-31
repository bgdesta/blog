package miu.edu.cs544.reaction.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miu.edu.cs544.reaction.enums.ReactName;
import miu.edu.cs544.reaction.enums.ReactType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
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

    //@NotNull(message = "Reaction name is mandatory")

    private ReactName reactionname;
   // @NotNull(message = "Reaction type is mandatory")
    private ReactType reactionto;

    public Reaction(long user_id, long post_id, ReactName reactionName, ReactType reactionTo) {

        this.userid = user_id;
        this.postid = post_id;


        this.reactionname = reactionName;
        this.reactionto = reactionTo;

        this.Createdat = new Date();
        this.updatedat = new Date();
    }
}
