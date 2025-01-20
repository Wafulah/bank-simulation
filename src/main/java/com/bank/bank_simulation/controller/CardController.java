package com.bank.bank_simulation.controller;

import com.bank.bank_simulation.entity.Card;
import com.bank.bank_simulation.entity.Transaction;
import com.bank.bank_simulation.entity.User;
import com.bank.bank_simulation.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * This class is responsible for handling HTTP requests related to card operations.
 * It uses Spring MVC annotations to map requests to specific methods.
 * 
 * The CardController handles requests for retrieving, creating, crediting, and debiting cards.
 * It interacts with the CardService to perform the necessary business logic and returns the appropriate responses to the client.
 * 
 * Endpoints:
 * 1. GET /card: Retrieves the card details for the authenticated user.
 * 2. POST /card/create: Creates a new card for the authenticated user with an initial amount.
 * 3. POST /card/credit: Credits a specified amount to the authenticated user's card.
 * 4. POST /card/debit: Debits a specified amount from the authenticated user's card.
 * 
 * The controller ensures that the user's identity is validated using Spring Security's authentication mechanism
 * before performing any card-related operation.
 */
@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

      /**
     * Retrieves the card details of the authenticated user.
     * 
     * This method accesses the authenticated user's details from the authentication object,
     * delegates the task to CardService to retrieve the card information, and returns the result
     * as a ResponseEntity containing the card details.
     *
     * @param authentication The current authentication object, which contains the authenticated user.
     * @return A ResponseEntity containing the card details if the operation is successful.
     */
    @GetMapping
    public ResponseEntity<Card> getCard(Authentication authentication){
        var user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(cardService.getCard(user));
    }

    /**
     * Creates a new card for the authenticated user with the specified initial amount.
     *
     * @param amount The initial amount to be credited to the new card.
     * @param authentication The current authentication object, which contains the authenticated user.
     * @return A ResponseEntity containing the newly created card if the operation is successful.
     * @throws Exception If any error occurs during the card creation process.
     */
    @PostMapping("/create")
    public ResponseEntity<Card> createCard(@RequestParam double amount, Authentication authentication) throws Exception {
        var user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(cardService.createCard(amount, user));
    }

    /**
     * Credits the authenticated user's card with the specified amount.
     *
     * @param amount The amount to be credited to the user's card.
     * @param authentication The current authentication object, which contains the authenticated user.
     * @return A ResponseEntity containing the transaction details if the operation is successful.
     */
    @PostMapping("/credit")
    public ResponseEntity<Transaction> creditCard(@RequestParam double amount, Authentication authentication){
        var user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(cardService.creditCard(amount, user));
    }

    /**
     * Debits the authenticated user's card with the specified amount.
     *
     * @param amount The amount to be debited from the user's card.
     * @param authentication The current authentication object, which contains the authenticated user.
     * @return A ResponseEntity containing the transaction details if the operation is successful.
     */
    @PostMapping("/debit")
    public ResponseEntity<Transaction> debitCard(@RequestParam double amount, Authentication authentication){
        var user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(cardService.debitCard(amount, user));
    }
}
