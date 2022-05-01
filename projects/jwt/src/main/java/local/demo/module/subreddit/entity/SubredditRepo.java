package local.demo.module.subreddit.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubredditRepo extends JpaRepository<Subreddit, Long> {

    Optional<Subreddit> findByName(String subredditName);
}
