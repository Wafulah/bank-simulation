package com.bank.bank_simulation.controller;

import com.bank.bank_simulation.dto.AccountDto;
import com.bank.bank_simulation.dto.ConvertDto;
import com.bank.bank_simulation.dto.TransferDto;
import com.bank.bank_simulation.entity.Account;
import com.bank.bank_simulation.entity.Transaction;
import com.bank.bank_simulation.entity.User;
import com.bank.bank_simulation.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller for handling account-related operations.
 * 
 * This class is responsible for managing account actions for authenticated users. 
 * It provides endpoints for creating accounts, retrieving user accounts, transferring funds, 
 * checking current exchange rates, finding accounts, and converting currencies.
 * 
 * Each method ensures that the user is authenticated before allowing access to any 
 * operations related to accounts and transactions. It uses Spring Security to manage authentication 
 * and relies on the AccountService to handle the business logic for each operation.
 * 
 * Key operations include:
 * 1. **Create Account** - Allows the authenticated user to create a new account.
 * 2. **Get User Accounts** - Retrieves a list of all accounts associated with the authenticated user.
 * 3. **Transfer Funds** - Allows the authenticated user to transfer funds between accounts.
 * 4. **Get Exchange Rates** - Provides the current exchange rates for supported currencies.
 * 5. **Find Account** - Allows the user to find an account by providing the account's code and recipient number.
 * 6. **Convert Currency** - Enables currency conversion from one account to another for the authenticated user.
 * 
 * The responses are returned using `ResponseEntity`, ensuring that the appropriate HTTP status codes are included 
 * along with the response body.
 * 
 * The methods in this controller rely on the `AccountService` class to interact with the data layer and perform
 * the necessary business logic. It also utilizes Spring Security's `Authentication` object to ensure that the
 * user is authenticated and authorized to perform these operations.
 * 
 * @author Wafulah
 */
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * Creates a new account for the authenticated user.
     *
     * @param accountDto The details of the new account.
     * @param authentication The current authentication context.
     * @return The created account.
     * @throws Exception If an error occurs during account creation.
     */
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody AccountDto accountDto, Authentication authentication) throws Exception {
        var user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(accountService.createAccount(accountDto, user));
    }

    /**
     * Retrieves a list of accounts associated with the authenticated user.
     *
     * @param authentication The current authentication context.
     * @return The list of user accounts.
     */
    @GetMapping
    public ResponseEntity<List<Account>> getUserAccounts(Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(accountService.getUserAccounts(user.getUid()));
    }

    /**
     * Transfers funds from one account to another for the authenticated user.
     *
     * @param transferDto The details of the transfer.
     * @param authentication The current authentication context.
     * @return The created transaction.
     * @throws Exception If an error occurs during the transfer.
     */
    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transferFunds(@RequestBody TransferDto transferDto, Authentication authentication) throws Exception {
        var user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(accountService.transferFunds(transferDto, user));
    }

    /**
     * Retrieves the current exchange rates for supported currencies.
     *
     * @return The map of currency codes to exchange rates.
     */
    @GetMapping("/rates")
    public ResponseEntity<Map<String, Double>> getExchangeRate() {
        return ResponseEntity.ok(accountService.getExchangeRate());
    }

    @PostMapping("/find")
    public ResponseEntity<Account> findAccount(@RequestBody TransferDto dto) {
        return ResponseEntity.ok(accountService.findAccount(dto.getCode() ,dto.getRecipientAccountNumber()));
    }

    /**
     * Converts a specified amount of currency from one account to another for the authenticated user.
     *
     * @param convertDto The details of the conversion.
     * @param authentication The current authentication context.
     * @return The created transaction.
     * @throws Exception If an error occurs during the conversion.
     */
    @PostMapping("/convert")
    public ResponseEntity<Transaction> convertCurrency(@RequestBody ConvertDto convertDto, Authentication authentication) throws Exception {
        var user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(accountService.convertCurrency(convertDto, user));
    }
}
