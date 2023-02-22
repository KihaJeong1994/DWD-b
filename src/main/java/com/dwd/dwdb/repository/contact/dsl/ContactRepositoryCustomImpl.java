package com.dwd.dwdb.repository.contact.dsl;

import com.dwd.dwdb.model.contact.Contact;
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

import static com.dwd.dwdb.model.contact.QContact.contact;

public class ContactRepositoryCustomImpl extends QuerydslRepositorySupport implements ContactRepositoryCustom{

    public ContactRepositoryCustomImpl(MongoOperations operations) {
        super(operations);
    }

    @Override
    public Page<Contact> search(String title, String description, String createdBy, Pageable pageable) {
        List<Contact> contacts = from(contact)
                .where(
                    titleContains(title),
                    descriptionContains(description),
                    createdByContains(createdBy)
                )
                .orderBy(
                    pageable.getSort().stream().map(this::getOrderSpecifier).toArray(OrderSpecifier[]::new)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return PageableExecutionUtils.getPage(contacts,pageable,from(contact)::fetchCount);
    }

    private OrderSpecifier<? extends Serializable> getOrderSpecifier(Sort.Order order) {
        String orderProperty = order.getProperty();
        if (orderProperty.equals("updatedAt")) {
            if (order.getDirection() == Sort.Direction.ASC) {
                return contact.updatedAt.asc();
            } else {
                return contact.updatedAt.desc();
            }
        }
        // add additional cases for other sort properties as needed
        // ...
        else {
            throw new IllegalArgumentException("Unsupported sort property: " + orderProperty);
        }
    }

    private BooleanExpression titleContains(String title) {
        if(StringUtils.isEmpty(title)){
            return null;
        }
        return contact.title.containsIgnoreCase(title);
    }
    private BooleanExpression descriptionContains(String description) {
        if(StringUtils.isEmpty(description)){
            return null;
        }
        return contact.description.containsIgnoreCase(description);
    }
    private BooleanExpression createdByContains(String createdBy) {
        if(StringUtils.isEmpty(createdBy)){
            return null;
        }
        return contact.createdBy.containsIgnoreCase(createdBy);
    }
}
