package local.demo.module.comment;

import java.util.List;
import java.time.Instant;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import local.demo.module.auth.AuthService;
import local.demo.module.auth.entity.User;
import local.demo.module.auth.entity.UserRepo;
import local.demo.module.comment.dto.CommentDto;
import local.demo.module.comment.entity.Comment;
import local.demo.module.comment.entity.CommentRepo;
import local.demo.module.post.PostNotFoundException;
import local.demo.module.post.entity.Post;
import local.demo.module.post.entity.PostRepo;


@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final AuthService authService;

    @Transactional
    protected void saveNew(CommentDto commentDto) throws PostNotFoundException {
        Long postId = commentDto.getPostId();
        Post post = postRepo.findById(postId).orElseThrow(() ->
                new PostNotFoundException("postId: %d".formatted(postId)));
        Comment newComment = mapDto2Comment(commentDto, post, authService.getCurrentUser());
        commentRepo.save(newComment);
    }

    protected List<CommentDto> getAllByPostId(Long postId) throws PostNotFoundException {
        Post post = postRepo.findById(postId).orElseThrow(() ->
                new PostNotFoundException("postId: %d not found".formatted(postId)));
        return commentRepo.findByPost(post)
                .stream()
                .map(this::mapComment2Dto)
                .toList();
    }

    protected List<CommentDto> getAllByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("username: %s".formatted(username)));
        return commentRepo.findAllByUser(user)
                .stream()
                .map(this::mapComment2Dto)
                .toList();
    }

    private Comment mapDto2Comment(CommentDto commentDto, Post post, User user) {
        return Comment.builder()
                .text(commentDto.getText())
                .createdAt(Instant.now())
                .post(post)
                .user(user)
                .build();
    }

    private CommentDto mapComment2Dto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .username(comment.getUser().getUsername())
                .build();
    }
}
