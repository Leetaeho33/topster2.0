package com.sparta.topster.domain.comment.repository;

import com.sparta.topster.domain.comment.entity.Comment;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.parser.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class)
public interface CommentRespository {

  void save(String comment);

  Page<Comment> findByPostId(Long postId, Pageable pageable);

  void save(Comment comment);

  void delete(Comment comment);

  Optional<Comment> findById(Long commentId);
}
