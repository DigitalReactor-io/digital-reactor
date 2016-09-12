package io.digitalreactor.web.contract;

/**
 * Created by ingvard on 10.09.16.
 */
public interface ConfigurationWebServiceContract {
    final String WEB_SERVICE_PATH = "configuration";
    final String APPLICATION_ID_PATH = "application/id";

    String configurationApplicationId();

}
