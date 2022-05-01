package local.demo.module.comment;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import local.demo.module.comment.dto.CommentDto;


@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CommentDto commentDto) {
        commentService.saveNew(commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getAllByPostId(@PathVariable Long postId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.getAllByPostId(postId));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<CommentDto>> getAllByUsername(@PathVariable String username) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.getAllByUsername(username));
    }
}
