package miu.edu.cs544.reaction.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import miu.edu.cs544.reaction.enums.ReactName;
import miu.edu.cs544.reaction.enums.ReactType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class React {
    @Id
    @GeneratedValue
    private long id;

    private long user_id;
    private long post_id;
    private Date CreatedAt;
    private Date updatedAt;
    private ReactName reactName;
    private ReactType reactType;

    public React(long user_id, long post_id, ReactName reactName, ReactType reactType) {

        this.user_id = user_id;
        this.post_id = post_id;
        this.reactName = reactName;
        this.CreatedAt = new Date();
        this.updatedAt = new Date();
        this.reactType = reactType;
    }
}
