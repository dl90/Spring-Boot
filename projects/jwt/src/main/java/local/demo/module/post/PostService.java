package local.demo.module.post;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import local.demo.module.auth.AuthService;
import local.demo.module.auth.entity.User;
import local.demo.module.auth.entity.UserRepo;
import local.demo.module.comment.entity.CommentRepo;
import local.demo.module.subreddit.entity.Subreddit;
import local.demo.module.subreddit.entity.SubredditRepo;
import local.demo.module.subreddit.SubredditNotFoundException;
import local.demo.module.post.entity.Post;
import local.demo.module.post.dto.PostRequest;
import local.demo.module.post.dto.PostResponse;
import local.demo.module.post.entity.PostRepo;


@Service
@AllArgsConstructor
public class PostService {

    private final AuthService authService;
    private final UserRepo userRepo;
    private final PostRepo postRepo;
    private final SubredditRepo subredditRepo;
    private final CommentRepo commentRepo;

    @Transactional
    protected void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepo.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));

        Post newPost = mapPostRequest2Post(postRequest, subreddit, authService.getCurrentUser());
        subreddit.getPosts().add(newPost);

        postRepo.save(newPost);
        subredditRepo.save(subreddit);
    }

    protected PostResponse getPost(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return mapPost2PostResponse(post);
    }

    protected List<PostResponse> getAllPosts() {
        return postRepo.findAll().stream()
                .map(this::mapPost2PostResponse)
                .toList();
    }

    protected List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepo.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepo.findAllBySubreddit(subreddit);
        return posts.stream()
                .map(this::mapPost2PostResponse)
                .toList();
    }

    protected List<PostResponse> getPostsByUsername(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepo.findByUser(user)
                .stream()
                .map(this::mapPost2PostResponse)
                .toList();
    }

    private Post mapPostRequest2Post(PostRequest postRequest, Subreddit subreddit, User user) {
        return Post.builder()
                .title(postRequest.getTitle())
                .description(postRequest.getDescription())
                .url(postRequest.getUrl())
                .voteCount(0)
                .createdAt(Instant.now())
                .user(user)
                .subreddit(subreddit)
                .build();
    }

    private PostResponse mapPost2PostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .username(post.getUser().getUsername())
                .title(post.getTitle())
                .url(post.getUrl())
                .description(post.getDescription())
                .createdAt(post.getCreatedAt())
                .subredditName(post.getSubreddit().getName())
                .voteCount(post.getVoteCount())
                .commentCount(commentRepo.findByPost(post).size())
                .build();
    }
}
