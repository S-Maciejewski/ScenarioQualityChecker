package pl.put.poznan.transformer.rest;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.Scenario;
import pl.put.poznan.transformer.logic.Step;

@RestController
public class TextTransformerController {

    private static final Logger logger = LoggerFactory.getLogger(TextTransformerController.class);

    @RequestMapping(path = "/{function}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JSONObject post(@PathVariable String function, @RequestBody JSONObject sendedScenario) {

        // log the parameters
        logger.debug(function);
        logger.info(sendedScenario.toJSONString());

        // running logic here
        Scenario scenario = new Scenario(sendedScenario);

        return scenario.triggerFunction(function, 0);
    }

    @RequestMapping(path = "/showScenario/{maxDepth}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public JSONObject post(@PathVariable int maxDepth, @RequestBody JSONObject sendedScenario) {

        // log the parameters
        logger.debug("showScenario");
        logger.info(sendedScenario.toJSONString());

        // running logic here
        Scenario scenario = new Scenario(sendedScenario);

        return scenario.triggerFunction("showScenarioWithMaxDepth", maxDepth);
    }
}


