package com.sparta.topster.domain.comment.repository;

import com.sparta.topster.domain.comment.entity.Comment;
import java.util.List;
import javax.swing.text.html.parser.Entity;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class)
public interface CommentRespository {

  void save(String comment);

  List<Comment> findAll();

  Comment findById(Long commentId);

  void save(Comment comment);

  void delete(Comment comment);
}
