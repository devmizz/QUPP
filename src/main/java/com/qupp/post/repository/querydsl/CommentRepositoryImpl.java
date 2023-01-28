package com.qupp.post.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.qupp.post.repository.QComment.comment1;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public long getCommentCount() {
        /*
        SELECT count(comment.id)
        FROM comment;
         */

        return jpaQueryFactory
                .select(comment1.count())
                .from(comment1)
                .fetchOne();
    }

}