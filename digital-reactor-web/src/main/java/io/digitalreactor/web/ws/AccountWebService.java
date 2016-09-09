package io.digitalreactor.web.ws;

import io.digitalreactor.dao.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by MStepachev on 07.09.2016.
 */
@RestController
@RequestMapping(value = "accounts")
public class AccountWebService {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "{email}/short", method = RequestMethod.GET)
    @ResponseBody
    public void getShortAccountInfo(@PathVariable String email) {
       int i = 0;
    }
}
