package local.demo.module.vote;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import local.demo.module.auth.AuthService;
import local.demo.module.auth.entity.User;
import local.demo.module.post.PostNotFoundException;
import local.demo.module.post.entity.Post;
import local.demo.module.post.entity.PostRepo;
import local.demo.module.vote.dto.VoteDto;
import local.demo.module.vote.entity.Vote;
import local.demo.module.vote.entity.VoteRepo;
import local.demo.module.vote.entity.VoteType;


@Service
@AllArgsConstructor
public class VoteService {

    private final PostRepo postRepo;
    private final VoteRepo voteRepo;
    private final AuthService authService;

    @Transactional
    protected void vote(VoteDto voteDto) {
        User user = authService.getCurrentUser();
        Long postId = voteDto.getPostId();
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("postId: %d".formatted(postId)));

        Optional<Vote> previousVote = voteRepo.findByPostAndUser(post, user);
        int voteCount = post.getVoteCount();

        if (previousVote.isPresent()) {
            if (previousVote.get().getVoteType().equals(voteDto.getVoteType())) {
                return;
            } else {
                voteRepo.delete(previousVote.get());
            }
        } else {
            voteRepo.save(mapDto2Vote(voteDto, post, user));
        }

        if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(voteCount + 1);
        } else {
            post.setVoteCount(voteCount - 1);
        }

        postRepo.save(post);
    }

    private Vote mapDto2Vote(VoteDto voteDto, Post post, User user) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(user)
                .build();
    }
}
