package com.telega;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public abstract class TelegramService extends TelegramLongPollingBot {
    private final ReplyKeyboardMarkup replyKeyboardMarkup;

    public TelegramService() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow k1 = new KeyboardRow();
        k1.add(new KeyboardButton("Узнать S&P500"));
        k1.add(new KeyboardButton("Узнать DJI"));
        k1.add(new KeyboardButton("Мои задачи"));
        List<KeyboardRow> list = new ArrayList<KeyboardRow>();
        list.add(k1);
        replyKeyboardMarkup.setKeyboard(list);
        this.replyKeyboardMarkup = replyKeyboardMarkup;
    }

    public void sendMessage(final String mess, final Long chatId) {
        final SendMessage sendMessage = new SendMessage(chatId, mess);
        sendMessage.setReplyMarkup(this.replyKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getText());
    }

}
