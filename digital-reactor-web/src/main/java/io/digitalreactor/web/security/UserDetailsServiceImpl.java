package io.digitalreactor.web.security;

import io.digitalreactor.dao.AccountRepository;
import io.digitalreactor.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;

/**
 * Created by MStepachev on 07.09.2016.
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private AccountRepository accountRepository;

    public UserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);

        return new User(account.getEmail(), account.getPassword(), Arrays.asList(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ADMIN";
            }
        }));
    }
}
