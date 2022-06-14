package com.company.manager;

import com.company.mainBot.MainBot;
import com.company.mainBot.MuddatliTolovBot;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;

@Service
public class BotManager extends TelegramLongPollingBot {

    @Autowired
    @Lazy
    private MainBot mainBot;
    @Autowired
    @Lazy
    private MuddatliTolovBot muddatliTolovBot;

    @Override
    public String getBotUsername() {
        return "abdulbori_marketbot";
    }

    @Override
    public String getBotToken() {
        return "5533119636:AAErtPJGHfLPr_OvftICoKtJDLrbtQXIwAY";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (update.hasCallbackQuery()) {
            User calBackUser = update.getCallbackQuery().getFrom();
            String text = update.getCallbackQuery().getData();
            log(calBackUser, text);
            mainBot.handlerCallbackQuery(calBackUser, text, update.getCallbackQuery().getMessage().getMessageId());
        } else if (message.hasText()) {
            User user = message.getFrom();
            log(user, message.getText());
            mainBot.handlerText(user, message);
        } else if (message.hasContact()) {
            User user = message.getFrom();
            log(user, message.getContact().getPhoneNumber());
            mainBot.handlerUserContact(user, message);
        } else if (message.hasLocation()) {
            User user = message.getFrom();
            log(user, message.getLocation().getLatitude() + " " + message.getLocation().getLongitude());
            mainBot.handlerLocation(user, message);
        } else if (message.hasPhoto()) {
            User user = message.getFrom();
            log(user, message.getText());
            muddatliTolovBot.handlerPhoto(user, message);
        }
    }

    public void send(Object object) {
        try {
            if (object instanceof SendMessage) {
                execute((SendMessage) object);
            } else if (object instanceof EditMessageText) {
                execute((EditMessageText) object);
            } else if (object instanceof SendPhoto) {
                execute((SendPhoto) object);
            } else if (object instanceof SendVideo) {
                execute((SendVideo) object);
            } else if (object instanceof SendContact) {
                execute((SendContact) object);
            } else if (object instanceof SendLocation) {
                execute((SendLocation) object);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendPhoto(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendContact(SendContact sendContact) {
        try {
            execute(sendContact);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendVideo(SendVideo sendVideo) {
        try {
            execute(sendVideo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(EditMessageText editMessageText) {
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void log(User user, String text) {
        String str = String.format(LocalDateTime.now() + ",  userId: %d, firstName: %s, lastName: %s, text: %s",
                user.getId(), user.getFirstName(), user.getLastName(), text);
        System.out.println(str);
    }
}
