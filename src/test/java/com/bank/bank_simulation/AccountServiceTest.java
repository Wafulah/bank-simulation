package com.bank.bank_simulation;

import com.bank.bank_simulation.dto.AccountDto;
import com.bank.bank_simulation.dto.TransferDto;
import com.bank.bank_simulation.entity.Account;
import com.bank.bank_simulation.entity.Transaction;
import com.bank.bank_simulation.entity.User;
import com.bank.bank_simulation.repository.AccountRepository;
import com.bank.bank_simulation.service.helper.AccountHelper;
import com.bank.bank_simulation.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountHelper accountHelper;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccount_success() throws Exception {
        // Arrange
        AccountDto accountDto = new AccountDto();
        accountDto.setCode("USD");
        accountDto.setLabel("Savings");
        accountDto.setSymbol('$');
        User user = new User();
        user.setUid("user123");

        Account mockAccount = new Account();
        mockAccount.setCode("USD");
        mockAccount.setLabel("Savings");
        mockAccount.setSymbol('$');
        when(accountHelper.createAccount(accountDto, user)).thenReturn(mockAccount);

        // Act
        Account createdAccount = accountService.createAccount(accountDto, user);

        // Assert
        assertNotNull(createdAccount);
        assertEquals("USD", createdAccount.getCode());
        assertEquals("Savings", createdAccount.getLabel());
        verify(accountHelper, times(1)).createAccount(accountDto, user);
    }

    @Test
    void getUserAccounts_success() {
        // Arrange
        String userId = "user123";
        Account mockAccount1 = new Account();
        mockAccount1.setLabel("Savings");
        Account mockAccount2 = new Account();
        mockAccount2.setLabel("Checking");

        when(accountRepository.findAllByOwnerUid(userId)).thenReturn(List.of(mockAccount1, mockAccount2));

        // Act
        List<Account> accounts = accountService.getUserAccounts(userId);

        // Assert
        assertNotNull(accounts);
        assertEquals(2, accounts.size());
        verify(accountRepository, times(1)).findAllByOwnerUid(userId);
    }

    @Test
    void transferFunds_success() throws Exception {
        // Arrange
        TransferDto transferDto = new TransferDto();
        transferDto.setCode("USD");
        transferDto.setRecipientAccountNumber(987654321L);
        transferDto.setAmount(100.0);

        User user = new User();
        user.setUid("user123");

        Account senderAccount = new Account();
        senderAccount.setCode("USD");
        senderAccount.setLabel("Savings");
        senderAccount.setBalance(500.0);

        Account receiverAccount = new Account();
        receiverAccount.setCode("USD");
        receiverAccount.setLabel("Checking");
        receiverAccount.setBalance(200.0);

        Transaction mockTransaction = mock(Transaction.class);
        when(accountRepository.findByCodeAndOwnerUid("USD", "user123")).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByAccountNumber(987654321L)).thenReturn(Optional.of(receiverAccount));
        when(accountHelper.performTransfer(senderAccount, receiverAccount, 100.0, user)).thenReturn(mockTransaction);

        // Mocking Transaction behavior
        when(mockTransaction.getAmount()).thenReturn(100.0);

        // Act
        Transaction transaction = accountService.transferFunds(transferDto, user);

        // Assert
        assertNotNull(transaction);
        assertEquals(100.0, transaction.getAmount());
        verify(accountRepository, times(1)).findByCodeAndOwnerUid("USD", "user123");
        verify(accountRepository, times(1)).findByAccountNumber(987654321L);
        verify(accountHelper, times(1)).performTransfer(senderAccount, receiverAccount, 100.0, user);
    }

    @Test
    void transferFunds_senderAccountNotFound_throwsException() {
        // Arrange
        TransferDto transferDto = new TransferDto();
        transferDto.setCode("USD");
        transferDto.setRecipientAccountNumber(987654321L);
        transferDto.setAmount(100.0);

        User user = new User();
        user.setUid("user123");

        when(accountRepository.findByCodeAndOwnerUid("USD", "user123")).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            accountService.transferFunds(transferDto, user);
        });

        assertEquals("Account of type currency do not exists for user", exception.getMessage());
        verify(accountRepository, times(1)).findByCodeAndOwnerUid("USD", "user123");
        verify(accountRepository, never()).findByAccountNumber(anyLong());
        try {
        verify(accountHelper, never()).performTransfer(any(), any(), anyDouble(), any());
            } catch (Exception e) {
        fail("Exception should not have been thrown during verification: " + e.getMessage());
        }
    }
}
