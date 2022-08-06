package com.company.util;

import com.company.enums.ProfileBotLanguage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
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
     * Tilni Tanlash uchun
     */

    public static InlineKeyboardMarkup menuSingleKeyboard() {
        InlineKeyboardButton button1 = InlineButtonUtilManager.button("\uD83C\uDDFA\uD83C\uDDFF O'zbek", "uzbek_tili");
        InlineKeyboardButton button2 = InlineButtonUtilManager.button("\uD83C\uDDF7\uD83C\uDDFA русский", "rus_tili");
        InlineKeyboardButton button3 = InlineButtonUtilManager.button("\uD83C\uDDFA\uD83C\uDDFF крил", "krilcha");
        List<InlineKeyboardButton> row1 = InlineButtonUtilManager.row(button1, button2);
        List<InlineKeyboardButton> row3 = InlineButtonUtilManager.row(button3);
        return InlineButtonUtilManager.keyboard(InlineButtonUtilManager.rowList(row1, row3));
    }

    /**
     * Telefon Nomer Uchun
     */

    public static ReplyKeyboardMarkup menuSingleKeyboards(ProfileBotLanguage language) {
        KeyboardButton button3;
        if (language.equals(ProfileBotLanguage.UZBEK)) {
            button3 = new KeyboardButton("\uD83D\uDCF1 Telefon nomer yuborish");
        } else if (language.equals(ProfileBotLanguage.RUSSIAN)) {
            button3 = new KeyboardButton("\uD83D\uDCF1 Отправить номер телефона");
        } else {
            button3 = new KeyboardButton("\uD83D\uDCF1 Телефон номер юбориш");
        }
        button3.setRequestContact(true);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(button3);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(List.of(row2));

        return replyKeyboardMarkup;
    }

    /**
     * Login bolimidan otgandan keyin
     */

    public static ReplyKeyboardMarkup menuNextLogin(ProfileBotLanguage language) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        if (language.equals(ProfileBotLanguage.UZBEK)) {
            keyboardRow1.add("\uD83D\uDC64 Profile");
            keyboardRow1.add("\uD83D\uDECD Buyurtmalar");
            keyboardRow2.add("\uD83D\uDD0D Category");
            keyboardRow2.add("\uD83D\uDED2 Savatcha");
        } else if (language.equals(ProfileBotLanguage.RUSSIAN)) {
            keyboardRow1.add("\uD83D\uDC64 Профиль");
            keyboardRow1.add("\uD83D\uDECD Заказы");
            keyboardRow2.add("\uD83D\uDD0D Категория");
            keyboardRow2.add("\uD83D\uDED2 Корзина");
        } else {
            keyboardRow1.add("\uD83D\uDC64 Профиле");
            keyboardRow1.add("\uD83D\uDECD Буюртмалар");
            keyboardRow2.add("\uD83D\uDD0D Сатегорй");
            keyboardRow2.add("\uD83D\uDED2 Саватча");
        }
        keyboardRowList.add(keyboardRow1);
        keyboardRowList.add(keyboardRow2);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        return replyKeyboardMarkup;
    }

    /**
     * CallbackQuery uchun kerak boladigan methodlar
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

    /**
     * Muddatli tolovdan keyingi menu
     */

    public static ReplyKeyboard menuMuddatliTolovNext(Long id, ProfileBotLanguage language) {

        InlineKeyboardButton button1;
        InlineKeyboardButton button2;
        InlineKeyboardButton button3;
        InlineKeyboardButton button4;

        if (language.equals(ProfileBotLanguage.UZBEK)) {
            button1 = InlineButtonUtilManager.button("Profile haqida to'liq ma'lumot", "profile_haqida_malumot_uz_" + id);
            button2 = InlineButtonUtilManager.button("Buyurtma haqida ma'lumot", "buyurtma_haqida_malumot_uz_" + id);
            button3 = InlineButtonUtilManager.button("Buyurtmani tasdiqlash", "muddatli_tolov_buyurtmani_tasdiqlash_uz_" + id);
            button4 = InlineButtonUtilManager.button("Buyurtmani bekor qilish", "muddatli_tolov_buyurtmani_bekor_uz_" + id);
        } else if (language.equals(ProfileBotLanguage.RUSSIAN)) {
            button1 = InlineButtonUtilManager.button("Полная информация профиля", "profile_haqida_malumot_rus_" + id);
            button2 = InlineButtonUtilManager.button("Запросить информацию", "buyurtma_haqida_malumot_rus_" + id);
            button3 = InlineButtonUtilManager.button("Подтверждение заказа", "muddatli_tolov_buyurtmani_tasdiqlash_ru_" + id);
            button4 = InlineButtonUtilManager.button("Отменить заказ", "muddatli_tolov_buyurtmani_bekor_ru_" + id);
        } else {
            button1 = InlineButtonUtilManager.button("Профиле ҳақида тўлиқ маълумот", "profile_haqida_malumot_krill_" + id);
            button2 = InlineButtonUtilManager.button("Буюртма ҳақида маълумот", "buyurtma_haqida_malumot_krill_" + id);
            button3 = InlineButtonUtilManager.button("Буюртмани тасдиқлаш", "muddatli_tolov_buyurtmani_tasdiqlash_krill_" + id);
            button4 = InlineButtonUtilManager.button("Буюртмани бекор қилиш", "muddatli_tolov_buyurtmani_bekor_krill_" + id);
        }
        List<InlineKeyboardButton> row1 = InlineButtonUtilManager.row(button1);
        List<InlineKeyboardButton> row2 = InlineButtonUtilManager.row(button2);
        List<InlineKeyboardButton> row3 = InlineButtonUtilManager.row(button3);
        List<InlineKeyboardButton> row4 = InlineButtonUtilManager.row(button4);
        return InlineButtonUtilManager.keyboard(InlineButtonUtilManager.rowList(row1, row2, row3, row4));
    }
}
