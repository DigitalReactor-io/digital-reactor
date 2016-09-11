package io.digitalreactor.web.ws;

import io.digitalreactor.web.contract.ConfigurationWebServiceContract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static io.digitalreactor.web.contract.ConfigurationWebServiceContract.WEB_SERVICE_PATH;

/**
 * Created by ingvard on 10.09.16.
 */
@RestController
@RequestMapping(value = WEB_SERVICE_PATH)
public class ConfigurationWebService implements ConfigurationWebServiceContract {
    @Value("${vendor.yandex.api.application.id}")
    private String applicationId;

    @RequestMapping(value = APPLICATION_ID_PATH, method = RequestMethod.GET)
    @ResponseBody
    public String configurationApplicationId() {
        return applicationId;
    }
}
