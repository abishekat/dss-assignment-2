package cu.dssassignment2.mongospringutil;

import cu.dssassignment2.mongospringutil.repository.EduCostStatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("cu.dssassignment2.mongospringutil.repository")
@ComponentScan("cu.dssassignment2.mongospringutil.*")
public class DssAssignment2Application {
    @Autowired
    EduCostStatRepository eduCostStatRepository;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(DssAssignment2Application.class, args);
    }

}