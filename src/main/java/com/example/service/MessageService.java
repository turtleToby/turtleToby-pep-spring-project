package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;
import com.example.entity.Message;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private  MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message addMessage(Message message) {
        if (!message.getMessageText().isEmpty() && message.getMessageText().length() < 255) {
            return messageRepository.save(message);
        }

        return null;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            return optionalMessage.get();
        }
        else {
            return null;
        }

    }

    public Integer deleteMessageById(Integer messageId) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            messageRepository.delete(optionalMessage.get());
            return 1;
        }
        else {
            return null;
        }
    }

    public Integer updateMessageById(Integer messageId, Message message) {
        String messageText = message.getMessageText();
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent() && !messageText.isEmpty() && messageText.length() < 255) {
            Message updatedMessage = optionalMessage.get();
            updatedMessage.setMessageText(messageText);
            messageRepository.save(updatedMessage);
            return 1;
        }
        else {
            return null;
        }
    }

    public List<Message> getAllMessagesByAccountId(Integer accountId) {
        return messageRepository.findMessagesByPostedBy(accountId);
    }
}
