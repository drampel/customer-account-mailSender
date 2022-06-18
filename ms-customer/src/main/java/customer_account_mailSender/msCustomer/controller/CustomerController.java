package customer_account_mailSender.msCustomer.controller;

import customer_account_mailSender.msCustomer.model.CustomerDto;
import customer_account_mailSender.msCustomer.model.validation.AddData;
import customer_account_mailSender.msCustomer.model.validation.UpdateData;
import customer_account_mailSender.msCustomer.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/customers")
@Api(value = "Customers resource endpoints")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation("Endpoint to get all customers")
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        return new ResponseEntity<>(customerService.getCustomers(), FOUND);
    }

    @ApiOperation("Endpoint to get customer by ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.getCustomer(id), FOUND);
    }

    @ApiOperation("Endpoint for adding a customer")
    @PostMapping
    public ResponseEntity<CustomerDto> addCustomer(@Validated(value = AddData.class)
                                                   @RequestBody CustomerDto dto) {
        return new ResponseEntity<>(customerService.addCustomer(dto), CREATED);
    }

    @ApiOperation("Endpoint to update customer data by ID")
    @PutMapping(path = "/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id,
                                                      @Validated(value = UpdateData.class)
                                                      @RequestBody CustomerDto dto) {
        dto.setId(id);
        return new ResponseEntity<>(customerService.updateCustomer(dto), OK);
    }

    @ApiOperation("Endpoint to delete a customer by ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CustomerDto> deleteCustomer(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.deleteCustomer(id), OK);
    }
}