package com.dwd.dwdb.repository.dsl;

import com.dwd.dwdb.enums.Site;
import com.dwd.dwdb.model.lecture.Lecture;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.io.Serializable;
import java.util.List;

import static com.dwd.dwdb.model.lecture.QLecture.lecture;

public class LectureRepositoryCustomImpl extends QuerydslRepositorySupport implements LectureRepositoryCustom {


    public LectureRepositoryCustomImpl(MongoOperations operations) {
        super(operations);
    }

    @Override
    public Page<Lecture> search(String title, Site site, Double rate, Pageable pageable) {
        List<Lecture> content = from(lecture).where(
                titleContains(title),
                siteEquals(site),
                rateAbout(rate)
        )
        .orderBy(pageable.getSort().stream().map(
                this::getOrderSpecifier
        ).toArray(OrderSpecifier[]::new))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
        return PageableExecutionUtils.getPage(content,pageable,from(lecture)::fetchCount);
    }

    private OrderSpecifier<? extends Serializable> getOrderSpecifier(Sort.Order order) {
        String orderProperty = order.getProperty();
        if (orderProperty.equals("rate")) {
            if (order.getDirection() == Sort.Direction.ASC) {
                return lecture.rate.asc();
            } else {
                return lecture.rate.desc();
            }
        }else if (orderProperty.equals("updatedAt")) {
            if (order.getDirection() == Sort.Direction.ASC) {
                return lecture.updatedAt.asc();
            } else {
                return lecture.updatedAt.desc();
            }
        }else if (orderProperty.equals("reviewsCnt")) {
            if (order.getDirection() == Sort.Direction.ASC) {
                return lecture.reviewsCnt.asc();
            } else {
                return lecture.reviewsCnt.desc();
            }
        }
        // add additional cases for other sort properties as needed
        // ...
        else {
            throw new IllegalArgumentException("Unsupported sort property: " + orderProperty);
        }
    }

    private BooleanExpression rateAbout(Double rate) {
        if(rate==null){
            return null;
        }
        return lecture.rate.goe(rate).and(lecture.rate.lt(rate + 1));
    }

    private BooleanExpression siteEquals(Site site) {
        if(site==null){
            return null;
        }
        return lecture.site.eq(site);
    }

    private BooleanExpression titleContains(String title) {
        if(StringUtils.isEmpty(title)){
            return null;
        }
        return lecture.title.contains(title);
    }
}
