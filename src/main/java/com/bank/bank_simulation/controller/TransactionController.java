package com.bank.bank_simulation.controller;

import com.bank.bank_simulation.entity.Transaction;
import com.bank.bank_simulation.entity.User;
import com.bank.bank_simulation.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class responsible for handling HTTP requests related to transaction operations.
 * It provides endpoints for retrieving transactions for the authenticated user, specific cards, or specific accounts.
 * 
 * This class leverages Spring MVC annotations to map HTTP requests to specific methods.
 * It also integrates Spring Security to ensure that each request is made by an authenticated user.
 * 
 * Endpoints:
 * - `/transactions`: Retrieves all transactions for the authenticated user.
 * - `/transactions/c/{cardId}`: Retrieves transactions related to a specific card.
 * - `/transactions/a/{accountId}`: Retrieves transactions related to a specific account.
 * 
 * All methods support pagination through the `page` query parameter to retrieve transaction data in chunks.
 * The business logic for retrieving the transactions is handled by the `TransactionService` class.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Retrieves all transactions for the authenticated user.
     *
     * @param page The page number for pagination.
     * @param auth The current authentication object.
     * @return A list of transactions.
     */
    @GetMapping
    public List<Transaction> getAllTransactions(@RequestParam String page, Authentication auth) {
        return transactionService.getAllTransactions(page, (User) auth.getPrincipal());
    }

    /**
     * Retrieves transactions for a specific card.
     *
     * @param cardId The ID of the card.
     * @param page The page number for pagination.
     * @param auth The current authentication object.
     * @return A list of transactions.
     */
    @GetMapping("/c/{cardId}")
    public List<Transaction> getTransactionsByCardId(@PathVariable String cardId, @RequestParam String page, Authentication auth) {
        return transactionService.getTransactionsByCardId(cardId, page, (User) auth.getPrincipal());
    }

    /**
     * Retrieves transactions for a specific account.
     *
     * @param accountId The ID of the account.
     * @param page The page number for pagination.
     * @param auth The current authentication object.
     * @return A list of transactions.
     */
    @GetMapping("/a/{accountId}")
    public List<Transaction> getTransactionsByAccountId(@PathVariable String accountId, @RequestParam String page, Authentication auth) {
        return transactionService.getTransactionsByAccountId(accountId, page, (User) auth.getPrincipal());
    }
}