package com.company.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class InlineButtonUtilManager {

    /**
     *
     * Tilni Tanlash uchun
     *
     */

    public static InlineKeyboardMarkup menuSingleKeyboard() {
        InlineKeyboardButton button1 = InlineButtonUtilManager.button("O'zbek", "uzbek_tili");
        InlineKeyboardButton button2 = InlineButtonUtilManager.button("русский", "rus_tili");
        InlineKeyboardButton button3 = InlineButtonUtilManager.button("крил", "krilcha");
        List<InlineKeyboardButton> row1 = InlineButtonUtilManager.row(button1, button2);
        List<InlineKeyboardButton> row3 = InlineButtonUtilManager.row(button3);
        return InlineButtonUtilManager.keyboard(InlineButtonUtilManager.rowList(row1,row3));
    }

    /**
     *
     * Telefon Nomer Uchun
     *
     */

    public static ReplyKeyboardMarkup menuSingleKeyboardsUzb() {
        KeyboardButton button3 = new KeyboardButton("\uD83D\uDCF1 Telefon nomer yuborish");
        button3.setRequestContact(true);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(button3);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(List.of(row2));

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup menuSingleKeyboardsRus() {
        KeyboardButton button3 = new KeyboardButton("\uD83D\uDCF1 Отправить номер телефона");
        button3.setRequestContact(true);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(button3);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(List.of(row2));

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup menuSingleKeyboardsKrill() {
        KeyboardButton button3 = new KeyboardButton("\uD83D\uDCF1 Телефон номер юбориш");
        button3.setRequestContact(true);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(button3);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(List.of(row2));

        return replyKeyboardMarkup;
    }

    /**
     *
     * Login bolimidan otgandan keyin
     *
     */

    public static ReplyKeyboardMarkup menuNextLoginUzb() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow1.add("Profile");
        keyboardRow1.add("Buyurtmalar");
        keyboardRow2.add("Category");
        keyboardRow2.add("Savatcha");
        keyboardRowList.add(keyboardRow1);
        keyboardRowList.add(keyboardRow2);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup menuNextLoginRus() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow1.add("Профиль");
        keyboardRow1.add("Заказы");
        keyboardRow2.add("Категория");
        keyboardRow2.add("Корзина");
        keyboardRowList.add(keyboardRow1);
        keyboardRowList.add(keyboardRow2);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup menuNextLoginKrill() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow1.add("Профиле");
        keyboardRow1.add("Буюртмалар");
        keyboardRow2.add("Сатегорй");
        keyboardRow2.add("Саватча");
        keyboardRowList.add(keyboardRow1);
        keyboardRowList.add(keyboardRow2);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        return replyKeyboardMarkup;
    }

    /**
     *
     * CallbackQuery uchun kerak boladigan methodlar
     *
     */

    public static InlineKeyboardButton button(String text, String callBack) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callBack);
        return button;
    }

    public static List<InlineKeyboardButton> row(InlineKeyboardButton... buttons) {
        return new LinkedList<>(Arrays.asList(buttons));
    }

    public static List<List<InlineKeyboardButton>> rowList(List<InlineKeyboardButton>... rows) {
        return new LinkedList<>(Arrays.asList(rows));
    }

    public static InlineKeyboardMarkup keyboard(List<List<InlineKeyboardButton>> rows) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }
}
