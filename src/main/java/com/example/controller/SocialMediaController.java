package com.example.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.example.service.*;
import com.example.entity.*;
import com.example.exception.*;

import java.util.List;

@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService; 
    }

    //1
    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        Account addedAccount = null;
        try {
            addedAccount = accountService.addAccount(account);
        } catch (AccountAlreadyExistsException e) {
            return ResponseEntity.status(409).body(null);
        }

        return ResponseEntity.status(addedAccount != null ? 200 : 400).body (addedAccount);
    }

    //2
    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account searchedAccount = accountService.getAccountLogin(account);

        return ResponseEntity.status(searchedAccount != null ? 200 : 401).body(searchedAccount);
    }

    //3
    @PostMapping("messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message) {
        if (accountService.getAccountById(message.getPostedBy()) == null) {
            return ResponseEntity.status(400).body(null);
        }
        Message addedMessage = messageService.addMessage(message);

        return ResponseEntity.status(addedMessage != null ? 200 : 400).body(addedMessage);
    }

    //4
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    //5
    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        Message searchedMessage = messageService.getMessageById(messageId);

        return ResponseEntity.status(200).body(searchedMessage);
    }

    //6
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId) {
        Integer deletedMessage = messageService.deleteMessageById(messageId);

        return ResponseEntity.status(200).body(deletedMessage);
    }

    //7
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable Integer messageId, @RequestBody Message message) {
        Integer updatedMessage = messageService.updateMessageById(messageId, message);

        return ResponseEntity.status(updatedMessage != null ? 200 : 400).body(updatedMessage);
    }

    //8
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Integer accountId) {
        List<Message> accountMessages = messageService.getAllMessagesByAccountId(accountId);

        return ResponseEntity.status(200).body(accountMessages);
    }
}
