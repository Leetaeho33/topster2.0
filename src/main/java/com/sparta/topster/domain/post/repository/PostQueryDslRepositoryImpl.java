package com.sparta.topster.domain.post.repository;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;
import static com.sparta.topster.domain.post.entity.QPost.post;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.topster.domain.post.dto.request.PostSearchCond;
import com.sparta.topster.domain.post.dto.request.PostSortReq;
import com.sparta.topster.domain.post.dto.response.PostListRes;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class PostQueryDslRepositoryImpl implements PostQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PostListRes> getPostList(PostSearchCond cond, Pageable pageable,
        PostSortReq sortReq) {

        List<PostListRes> list = jpaQueryFactory.select(
                Projections.fields(PostListRes.class,
                    post.id,
                    post.user.nickname,
                    post.title,
                    post.createdAt))
            .from(post)
            .leftJoin(post.user)
            .where(search(cond.key(), cond.query()))
            .orderBy(sort(sortReq.getSortBy(), sortReq.getAsc()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory.select(post.count())
            .from(post)
            .where(search(cond.key(), cond.query()));

        return PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);
    }

    private BooleanExpression search(String key, String query) {
        if (!StringUtils.hasText(key)) {
            return null;
        }
        switch (key) {
            case "title":
                return post.title.contains(query);
            case "content":
                return post.content.contains(query);
            case "author":
                return post.user.nickname.contains(query);
            default:
                return null;
        }
    }

    private OrderSpecifier sort(String sortBy, boolean asc) {
        if (!StringUtils.hasText(sortBy)) {
            return post.createdAt.desc();
        }
        return getSort(sortBy, asc ? ASC : DESC);
    }

    private OrderSpecifier getSort(String sortBy, Order order) {
        switch (sortBy) {
            case "createdAt":
                return new OrderSpecifier<>(order, post.createdAt);
            case "title":
                return new OrderSpecifier<>(order, post.title);
            default:
                return new OrderSpecifier<>(DESC, post.createdAt);
        }
    }
}
