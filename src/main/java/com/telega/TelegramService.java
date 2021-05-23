package com.telega;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public abstract class TelegramService extends TelegramLongPollingBot {
    public void sendMessage(String mess, Long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, mess);
//    sendMessage.setReplyMarkup(replyKeyboardMarkup)
        try {
            execute(sendMessage);
        } catch (Exception ignored) {
        }
    }
}
