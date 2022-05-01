package local.demo.module.subreddit;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import local.demo.module.auth.AuthService;
import local.demo.module.auth.entity.User;
import local.demo.module.subreddit.entity.Subreddit;
import local.demo.module.subreddit.entity.SubredditRepo;
import local.demo.module.subreddit.dto.SubredditDto;


@Service
@AllArgsConstructor
public class SubredditService {

    private final SubredditRepo subredditRepo;
    private final AuthService authService;

    @Transactional
    protected SubredditDto saveNew(SubredditDto subredditDto) {
        User user = authService.getCurrentUser();
        Subreddit newSubreddit = mapDto2Subreddit(subredditDto);
        newSubreddit.setUser(user);

        Subreddit saved = subredditRepo.save(newSubreddit);
        subredditDto.setId(saved.getId());
        subredditDto.setNumberOfPosts(0);
        return subredditDto;
    }

    protected SubredditDto get(Long id) {
        Subreddit subreddit = subredditRepo.findById(id)
                .orElseThrow(() -> new SubredditNotFoundException("id: %d".formatted(id)));
        return mapSubreddit2Dto(subreddit);
    }

    protected List<SubredditDto> getAll() {
        return subredditRepo.findAll()
                .stream()
                .map(this::mapSubreddit2Dto)
                .toList();
    }

    private Subreddit mapDto2Subreddit(SubredditDto subredditDto) {
        return Subreddit.builder()
                .name(subredditDto.getName())
                .description(subredditDto.getDescription())
                .createdAt(Instant.now())
                .build();
    }

    private SubredditDto mapSubreddit2Dto(Subreddit subreddit) {
        return SubredditDto.builder()
                .id(subreddit.getId())
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .numberOfPosts(subreddit.getPosts().size())
                .build();
    }
}
