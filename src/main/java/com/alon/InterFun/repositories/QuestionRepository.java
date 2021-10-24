package com.alon.InterFun.repositories;

import com.alon.InterFun.entities.Company;
import com.alon.InterFun.entities.Question;
import com.alon.InterFun.utilities.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByType(QuestionType type);
    List<Question> findByTopic(String topic);
    List<Question> findByTopicAndType(QuestionType type, String topic);

}
