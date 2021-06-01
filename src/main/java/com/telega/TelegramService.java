package com.telega;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public abstract class TelegramService extends TelegramLongPollingBot {

    public void sendMessage(final String mess,
                            final Long chatId,
                            final ReplyKeyboardMarkup replyKeyboardMarkup) {
        final SendMessage sendMessage = new SendMessage(chatId, mess);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (Exception ignored) {
        }
    }
    public void sendMessage(final String mess,
                            final Long chatId) {
        final SendMessage sendMessage = new SendMessage(chatId, mess);
        try {
            execute(sendMessage);
        } catch (Exception ignored) {
        }
    }
    public void sendMessage(final String mess,
                            final Long chatId,
                            final InlineKeyboardMarkup inlineKeyboardMarkup) {
        final SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText(mess)
                .setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (Exception ignored) {
        }
    }





}
