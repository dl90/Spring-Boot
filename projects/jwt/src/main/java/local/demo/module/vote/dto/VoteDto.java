package local.demo.module.vote.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import local.demo.module.vote.entity.VoteType;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteDto {

    private VoteType voteType;
    private Long postId;
}
