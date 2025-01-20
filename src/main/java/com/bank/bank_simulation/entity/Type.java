package com.bank.bank_simulation.entity;

/**
 * This enum represents different types of transactions in the application.
 *
 * @author Wafulah
 * @since 1.0
 */
public enum Type {
    /**
     * Represents a withdrawal transaction.
     */
    WITHDRAW,

    /**
     * Represents a deposit transaction.
     */
    DEPOSIT,

    /**
     * Represents a debit transaction.
     */
    DEBIT,

    /**
     * Represents a conversion transaction.
     */
    CONVERSION,

    /**
     * Represents a credit transaction.
     */
    CREDIT
}
