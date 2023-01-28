package com.dwd.dwdb.repository.dsl;

import com.dwd.dwdb.enums.Site;
import com.dwd.dwdb.model.Lecture;
import com.querydsl.core.types.dsl.BooleanExpression;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.dwd.dwdb.model.QLecture.lecture;

public class LectureRepositoryCustomImpl extends QuerydslRepositorySupport implements LectureRepositoryCustom {


    public LectureRepositoryCustomImpl(MongoOperations operations) {
        super(operations);
    }

    @Override
    public List<Lecture> search(String title, Site site, Double rate) {
        return from(lecture).where(
                titleContains(title),
                siteEquals(site),
                rateAbout(rate)
        ).fetch();
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
