package io.digitalreactor.web.contract;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ingvard on 10.09.16.
 */
public interface ConfigurationWebServiceContract {
    final String WEB_SERVICE_PATH = "configuration";
    final String APPLICATION_ID_PATH = "application/id";

    String configurationApplicationId();

}
