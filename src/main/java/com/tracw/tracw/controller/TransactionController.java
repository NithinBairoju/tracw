package com.tracw.tracw.controller;

import com.tracw.tracw.model.Transaction;
import com.tracw.tracw.model.User;
import com.tracw.tracw.service.TransactionService;
import com.tracw.tracw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @GetMapping("/new")
    public String showTransactionForm(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        model.addAttribute("currentBalance", user.getBalance());
        return "transaction-form";
    }

    @PostMapping("/new")
    public String processTransaction(
            @RequestParam BigDecimal amount,
            @RequestParam String type,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            String username = authentication.getName();
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new IllegalStateException("User not found"));

            if (type.equals("DEPOSIT")) {
                transactionService.deposit(user, amount);
                redirectAttributes.addFlashAttribute("message", "Deposit successful!");
            } else if (type.equals("WITHDRAW")) {
                transactionService.withdraw(user, amount);
                redirectAttributes.addFlashAttribute("message", "Withdrawal successful!");
            }

            return "redirect:/transactions/history";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/transactions/new";
        }
    }

    @GetMapping("/transfer")
    public String showTransferForm(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        model.addAttribute("currentBalance", user.getBalance());
        return "transfer-form";
    }

    @PostMapping("/transfer")
    public String processTransfer(
            @RequestParam String recipientPhone,
            @RequestParam BigDecimal amount,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            String username = authentication.getName();
            User sender = userService.findByUsername(username)
                    .orElseThrow(() -> new IllegalStateException("Sender not found"));

            User recipient = userService.findByPhoneNumber(recipientPhone)
                    .orElseThrow(() -> new IllegalStateException("Recipient not found"));

            transactionService.transfer(sender, recipient, amount);
            redirectAttributes.addFlashAttribute("message", "Transfer successful!");
            return "redirect:/transactions/history";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/transactions/transfer";
        }
    }

    @GetMapping("/history")
    public String showTransactionHistory(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        List<Transaction> transactions = transactionService.getUserTransactions(user);

        model.addAttribute("transactions", transactions);
        model.addAttribute("currentBalance", user.getBalance());

        return "transaction-history";
    }
}
