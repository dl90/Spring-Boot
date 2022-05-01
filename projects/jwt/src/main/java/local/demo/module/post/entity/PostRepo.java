package local.demo.module.post.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import local.demo.module.auth.entity.User;
import local.demo.module.subreddit.entity.Subreddit;


@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
