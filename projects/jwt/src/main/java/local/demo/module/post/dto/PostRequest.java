package local.demo.module.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequest {

    private String title;
    private String description;
    private String url;
    private String subredditName;
}
