package com.codestates.stackoverflow.question.service;

import com.codestates.stackoverflow.question.entity.Question;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

@Service
@Slf4j
public class HibernateSearchService {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

//    public void buildIndex() throws InterruptedException {
//        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
//        fullTextEntityManager.createIndexer().startAndWait();
//    }

    @SuppressWarnings("unchecked")
    public List<Question> searchQuestions(String keyword) {
        log.info("[searchQuestions] keyword = " + keyword);
//        log.info("[searchQuestions] keyword = " + keyword + " page = " + page + " size = " + size);

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder = fullTextEntityManager
                                    .getSearchFactory()
                                    .buildQueryBuilder()
                                    .forEntity(Question.class).get();

        //Hibernate Query DSL을 통해 Lucene Query를 생성
        Query query = queryBuilder.keyword().wildcard().onFields("title", "content")
                .matching(keyword).createQuery();
        log.info("Query: " + query.toString());
        //Lucene Query를 Hibernate FullTextQuery로 변환
        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, Question.class);
        log.info("FullTextQuery: " + fullTextQuery.toString());
        //페이지네이션 설정 후, 쿼리 실행 결과를 가져옴
//        fullTextQuery.setFirstResult(1);
//        fullTextQuery.setMaxResults(10);
        List<Question> questions = fullTextQuery.getResultList();
        log.info("Questions: " + questions);

        return questions;
    }
}
