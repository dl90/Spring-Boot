package local.demo.module.auth.entity;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


public interface RefreshTokenRepo extends CrudRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
