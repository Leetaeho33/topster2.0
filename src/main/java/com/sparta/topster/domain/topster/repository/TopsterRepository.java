package com.sparta.topster.domain.topster.repository;

import com.sparta.topster.domain.topster.entity.Topster;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = Topster.class, idClass = Long.class)
public interface TopsterRepository {
    Topster save(Topster topster);

    Optional<Topster> findById(Long topsterId);
    List<Topster> findByUserId(Long userId);
    void delete(Topster topster);

}
