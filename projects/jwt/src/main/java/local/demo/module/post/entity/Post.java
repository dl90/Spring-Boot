package local.demo.module.post.entity;

import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import local.demo.module.subreddit.entity.Subreddit;
import local.demo.module.auth.entity.User;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title != empty or Null")
    private String title;

    @Nullable
    @Lob
    private String description;

    @Nullable
    private String url;

    private Integer voteCount = 0;

    private Instant createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subredditId", referencedColumnName = "id")
    private Subreddit subreddit;
}
