package local.demo.module.post.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {

    private Long id;
    private String username;
    private String title;
    private String description;
    private String url;
    private Instant createdAt;
    private String subredditName;
    private Integer voteCount;
    private Integer commentCount;
}
