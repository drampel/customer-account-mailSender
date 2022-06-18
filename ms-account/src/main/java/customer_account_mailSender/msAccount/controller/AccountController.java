package customer_account_mailSender.msAccount.controller;

import customer_account_mailSender.msAccount.model.AccountDto;
import customer_account_mailSender.msAccount.model.validation.AddData;
import customer_account_mailSender.msAccount.model.validation.UpdateData;
import customer_account_mailSender.msAccount.service.AccountService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/accounts")
@Api(value = "Customer accounts resource endpoints")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation("Endpoint to get all existing accounts or all accounts of a single customer by customer_id")
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAccounts(@RequestParam(name = "customer_id", required = false)
                                                                Long customerId) {
        return new ResponseEntity<>(accountService.getAccounts(customerId), FOUND);
    }

    @ApiOperation("Endpoint to get customer account by ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<AccountDto> getAccountByID(@PathVariable Long id) {
        return new ResponseEntity<>(accountService.getAccountByID(id), FOUND);
    }

    @ApiOperation("Endpoint to get customer account by account_no")
    @GetMapping("/account_no/{account_no}")
    public ResponseEntity<AccountDto> getAccountByAccountNo(@PathVariable(name = "account_no") String accountNo) {
        return new ResponseEntity<>(accountService.getAccountByAccountNo(accountNo), FOUND);
    }

    @ApiOperation("Endpoint for creating a customer account")
    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Validated(value = AddData.class)
                                                    @RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), CREATED);
    }

    @ApiOperation("Endpoint to update customer account data by ID")
    @PutMapping(path = "/{id}")
    public ResponseEntity<AccountDto> updateAccountById(@PathVariable Long id,
                                                        @Validated(value = UpdateData.class)
                                                        @RequestBody AccountDto accountDto) {
        accountDto.setId(id);
        return new ResponseEntity<>(accountService.updateAccountById(accountDto), OK);
    }

    @ApiOperation("Endpoint to update customer account data by account_no")
    @PutMapping(path = "/account_no/{account_no}")
    public ResponseEntity<AccountDto> updateAccountByAccountNo(@PathVariable("account_no") String accountNo,
                                                               @Validated(value = UpdateData.class)
                                                               @RequestBody AccountDto accountDto) {
        accountDto.setAccountNo(accountNo);
        return new ResponseEntity<>(accountService.updateAccountByAccountNo(accountDto), OK);
    }

    @ApiOperation("Endpoint to delete customer account by ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<AccountDto> deleteAccountByID(@PathVariable Long id) {
        return new ResponseEntity<>(accountService.deleteAccountByID(id), OK);
    }

    @ApiOperation("Endpoint to delete customer account by account_no")
    @DeleteMapping(path = "/account_no/{account_no}")
    public ResponseEntity<AccountDto> deleteAccountByAccountNo(@PathVariable("account_no") String accountNo) {
        return new ResponseEntity<>(accountService.deleteAccountByAccountNo(accountNo), OK);
    }
}