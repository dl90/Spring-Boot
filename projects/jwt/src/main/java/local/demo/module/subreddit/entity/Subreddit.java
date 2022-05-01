package local.demo.module.subreddit.entity;

import java.time.Instant;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import local.demo.module.auth.entity.User;
import local.demo.module.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Subreddit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name required")
    private String name;

    @NotBlank(message = "Description required")
    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Post> posts;

    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
