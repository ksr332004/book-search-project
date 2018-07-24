package com.seran.repository.predicate;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.seran.entity.QBookmark;


public class BookmarkSearchPredicate {

    public  static Predicate searchBookmarkByUserIdAndTarget(Integer userId, String target, String query) {
        QBookmark qBookmark = QBookmark.bookmark;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qBookmark.userId.eq(userId));

        if (!query.equals("none")) {
            if (target.equals("title")) {
                builder.and(qBookmark.title.like("%" + query + "%"));
            } else if (target.equals("contents")) {
                builder.and(qBookmark.contents.like("%" + query + "%"));
            }
        }

        return builder;
    }

}