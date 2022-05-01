package local.demo.module.comment.entity;

import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import local.demo.module.post.entity.Post;
import local.demo.module.auth.entity.User;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(length = 4095)
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "postId", referencedColumnName = "id")
    private Post post;

    private Instant createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
}
