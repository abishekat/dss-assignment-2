package cu.dssassignment2.mongospringutil.repository;

import cu.dssassignment2.mongospringutil.model.EduCostStat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EduCostStatRepository extends MongoRepository<EduCostStat, String> {

    List<cu.assignment2.proto.EduCostStat> findByYear(String year);
    List<EduCostStat> findByYearAndStateAndTypeAndLengthAndExpense(int year, String state, String type, String length, String expense);
}
