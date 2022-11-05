package com.codestates.stackoverflow.question.entity;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@Slf4j
public class QuestionsHibernateSearchInit implements ApplicationListener<ContextRefreshedEvent> {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initializeHibernateSearch();
    }

    public void initializeHibernateSearch() {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            log.error("Hibernate Search indexes 빌드 중 에러 발생 "
                    + e.toString());
        }
    }
}
