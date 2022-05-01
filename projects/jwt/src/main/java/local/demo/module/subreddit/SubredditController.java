package local.demo.module.subreddit;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import local.demo.module.subreddit.dto.SubredditDto;


@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditDto> create(@RequestBody SubredditDto subredditDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(subredditService.saveNew(subredditDto));
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getOne(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.get(id));
    }
}
