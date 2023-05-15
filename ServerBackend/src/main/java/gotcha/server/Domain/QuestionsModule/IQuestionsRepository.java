package gotcha.server.Domain.QuestionsModule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuestionsRepository extends JpaRepository<Question, Integer> {
}
