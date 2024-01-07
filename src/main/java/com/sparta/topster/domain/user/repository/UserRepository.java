package com.sparta.topster.domain.user.repository;

import com.sparta.topster.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = User.class, idClass = Long.class)
public interface UserRepository {

    Optional<User> findByUsername(String username);

    Optional<User> findBynickname(String nickname);

    User save(User user);

    User findById(Long id);

    User findByKakaoId(Long kakaoId);

    User findByEmail(String kakaoEmail);
}
