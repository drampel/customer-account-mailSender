package customer_account_mailSender.msCustomer.client;

import customer_account_mailSender.msCustomer.model.account.AccountDto;
import customer_account_mailSender.msCustomer.model.exception.AccountException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public class AccountWithExchange implements Account {
    private final RestTemplate restTemplate;
    private final String accountsUrl;

    public AccountWithExchange(RestTemplate restTemplate,
                               @Value("${client.accounts.url}") String accountsUrl) {
        this.restTemplate = restTemplate;
        this.accountsUrl = accountsUrl;
    }

    @Override
    public List<AccountDto> getAccountsByCustomerId(Long customerId) {
        String url = String.format("%s?customer_id=%d", accountsUrl, customerId);
        try {
            ResponseEntity<List<AccountDto>> responseEntity = restTemplate.exchange(url,
                    GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    },
                    Long.toString(customerId));
            return responseEntity.getBody();
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == NOT_FOUND) return List.of();
            else throw new AccountException("An unexpected error occurred in the 'account' database.");
        }
    }

    @Override
    public void deleteAccountById(Long id) {
        String url = String.format("%s/%d", accountsUrl, id);
        try {
            restTemplate.exchange(url,
                    DELETE,
                    null,
                    void.class,
                    Long.toString(id));
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == NOT_FOUND)
                throw new AccountException(String.format("Account with id %d not found", id));
            else
                throw new AccountException(String.format("An unexpected error occurred for id %d from the 'account' database", id));
        }
    }
}