package com.company.util;

import com.company.enums.ProfileBotLanguage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class InlineButtonUtilAdmin {

    /**
     * Admin Menu
     */

    public static ReplyKeyboardMarkup menuNextAdminMenu(ProfileBotLanguage language) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        KeyboardRow keyboardRow4 = new KeyboardRow();
        KeyboardRow keyboardRow5 = new KeyboardRow();
        if (language.equals(ProfileBotLanguage.UZBEK)) {
            keyboardRow1.add("Admin qo'shish");
            keyboardRow2.add("Category qo'shish");
            keyboardRow2.add("Categorylar");
            keyboardRow3.add("Product Category qo'shish");
            keyboardRow3.add("Product Categorylar");
            keyboardRow4.add("Product qo'shish");
            keyboardRow5.add("Bot Statistikasi");
        } else if (language.equals(ProfileBotLanguage.RUSSIAN)) {
            keyboardRow2.add("Добавить администратора");
            keyboardRow2.add("Добавить категорию");
            keyboardRow2.add("Категории");
            keyboardRow3.add("Добавить категорию продукта");
            keyboardRow3.add("категории продукта");
            keyboardRow4.add("Добавить продукт");
            keyboardRow5.add("Статистика ботов");
        } else {
            keyboardRow1.add("Админ қўшиш");
            keyboardRow2.add("категорй қўшиш");
            keyboardRow2.add("категорйлар");
            keyboardRow3.add("Продукт Сатегорй қўшиш");
            keyboardRow3.add("Продукт Сатегорйлар");
            keyboardRow4.add("Продукт қўшиш");
            keyboardRow5.add("Бот Статистикаси");
        }
        keyboardRowList.add(keyboardRow1);
        keyboardRowList.add(keyboardRow2);
        keyboardRowList.add(keyboardRow3);
        keyboardRowList.add(keyboardRow4);
        keyboardRowList.add(keyboardRow5);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        return replyKeyboardMarkup;
    }
}
