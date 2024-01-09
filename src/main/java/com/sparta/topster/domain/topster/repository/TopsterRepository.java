package com.sparta.topster.domain.topster.repository;

import com.sparta.topster.domain.topster.entity.Topster;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Topster.class, idClass = Long.class)
public interface TopsterRepository {

}
