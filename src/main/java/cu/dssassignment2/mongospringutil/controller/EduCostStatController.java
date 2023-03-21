package cu.dssassignment2.mongospringutil.controller;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import cu.dssassignment2.mongospringutil.model.EduCostStat;
import cu.dssassignment2.mongospringutil.service.EduCostStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@RestController
@RequestMapping("/")
public class EduCostStatController {

    @Autowired
    EduCostStatService eduCostStatService;

    @GetMapping("/stats")
    public List<EduCostStat> getAllStats() {
        try {
            List<EduCostStat> stat = eduCostStatService.getAllStat();
            if (stat == null || stat.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No resorts found");
            }
            return stat;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while fetching resorts", e);
        }
    }

    @GetMapping("/stat/{yr}/{state}/{expense1}/{expense2}/{length}/{type}")
    public List<EduCostStat> getAllStatsByRequest(@PathVariable String yr,@PathVariable String state,
    @PathVariable String expense1,@PathVariable String expense2, @PathVariable String length, @PathVariable String type) {
        String expense=expense1+'/'+expense2;
        int year = Integer.parseInt(yr);
        try {
            List<EduCostStat> stat = eduCostStatService.getEduCostStatByQ1Request(year, state, type, length, expense);
            if (stat == null || stat.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No resorts found");
            }
            return stat;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while fetching resorts", e);
        }
    }

    @GetMapping("/stats/{year}")
    public List<EduCostStat> getAllStatsByYear(@PathVariable String year) {
        try {
            List<EduCostStat> stat = eduCostStatService.getAllStatByYear(year);
            if (stat == null || stat.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No resorts found");
            }
            return stat;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while fetching resorts", e);
        }
    }

    @PostMapping("/upload")
    public String uploadCsvFile(@RequestBody String request) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream("nces330_20.csv")))) {

            CsvToBean<EduCostStat> csvToBean = new CsvToBeanBuilder<EduCostStat>(reader).withType(EduCostStat.class)
                    .withIgnoreLeadingWhiteSpace(true).build();

            List<EduCostStat> eduCostStats = csvToBean.parse();

            for (EduCostStat eduCostStat : eduCostStats) {
                eduCostStatService.uploadDataset(eduCostStat);
            }

            return "File uploaded successfully.";

        } catch (IOException e) {
            return "File upload failed: " + e.getMessage();
        }

    }
}
