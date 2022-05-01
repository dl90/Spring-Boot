package local.demo.module.vote.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import local.demo.module.post.entity.Post;
import local.demo.module.auth.entity.User;


@Repository
public interface VoteRepo extends JpaRepository<Vote, Long> {
    Optional<Vote> findByPostAndUser(Post post, User currentUser);
}
