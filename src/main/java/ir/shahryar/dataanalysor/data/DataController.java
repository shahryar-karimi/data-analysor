package ir.shahryar.dataanalysor.data;

import ir.shahryar.dataanalysor.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping("analyze")
    public DTO analyze(@RequestParam String coinName) {
        String result;
        try {
            result = dataService.linearRegressionAnalyze(coinName);
        } catch (IOException e) {
            result = "failed to connect to the database";
        }
        return new DTO(result);
    }
}
