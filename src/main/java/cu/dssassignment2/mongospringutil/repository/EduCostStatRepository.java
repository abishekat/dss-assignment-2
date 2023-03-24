package cu.dssassignment2.mongospringutil.repository;

import cu.dssassignment2.mongospringutil.model.EduCostStat;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EduCostStatRepository extends MongoRepository<EduCostStat, String> {

    List<cu.assignment2.proto.EduCostStat> findByYear(String year);

    List<EduCostStat> findByYearAndStateAndTypeAndLengthAndExpense(String year, String state, String type, String length, String expense);

    List<EduCostStat> findByYearAndTypeAndLength(String year, String type, String length);

    List<EduCostStat> findByYearAndTypeAndLengthAndExpense(String year, String type, String length, String expense);

    @Aggregation(pipeline = {
            "{$match: {\"region\": {\"$in\": [\"Northeast\", \"Midwest\", \"South\", \"West\", \"Pacific\"]}}}",
            "{$group: {\"_id\": {\"region\": \"$region\", \"year\": \"$year\", \"type\": \"$type\", \"length\": \"$length\"}, \"avgExpense\": {\"$avg\": \"$overall\"}}}",
            "{$project: {\"_id\": 0, \"region\": \"$_id.region\", \"year\": \"$_id.year\", \"type\": \"$_id.type\", \"length\": \"$_id.length\", \"avgExpense\": \"$avgExpense\"}}",
            "{$out: \"EduCostStatQueryFive\"}"
    })
    void aggregateRegionAvgExpense();

    @Query("{ region: { $in: [ 'Northeast', 'Midwest', 'South', 'West', 'Pacific' ] } }")
    List<EduCostStat> findAllByRegion();
}
