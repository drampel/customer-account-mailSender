package customer_account_mailSender.msAccount.client;

import customer_account_mailSender.msAccount.model.customer.CustomerDto;
import customer_account_mailSender.msAccount.model.exception.CustomerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public class CustomerWithExchange implements Customer {
    private final RestTemplate restTemplate;
    private final String customersUrl;

    public CustomerWithExchange(RestTemplate restTemplate,
                                @Value("${client.customers.url}") String customersUrl) {
        this.restTemplate = restTemplate;
        this.customersUrl = customersUrl;
    }

    @Override
    public CustomerDto getCustomer(Long id) {
        String url = String.format("%s/%d", customersUrl, id);
        try {
            return restTemplate.exchange(url,
                    GET,
                    null,
                    CustomerDto.class,
                    Long.toString(id)).getBody();
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == NOT_FOUND)
                throw new CustomerException(String.format("Customer with id %d not found", id));
            else
                throw new CustomerException(String.format("An unexpected error occurred for id %d from the 'customer' database", id));
        }
    }
}