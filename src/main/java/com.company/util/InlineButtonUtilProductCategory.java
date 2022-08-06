package com.company.util;

import com.company.enums.ProfileBotLanguage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class InlineButtonUtilProductCategory {

    /**
     *
     *
     * Bu methodlarga ProductId callbackQuery sifatida keladi sababi product sotib olish va muddatli tolov va boshqa
     * ishlarni biroz osonlashtiradi tiliga qarab.
     *
     */

    public static InlineKeyboardMarkup menuSingleKeyboard(Integer productId, ProfileBotLanguage language) {
        InlineKeyboardButton button1;
        InlineKeyboardButton button2;
        InlineKeyboardButton button3;

        if (language.equals(ProfileBotLanguage.UZBEK)) {
            button1 = InlineButtonUtilManager.button("Muddatli to'lov", "muddatli_tolov_uz_" + productId);
            button2 = InlineButtonUtilManager.button("Buyurtma Berish", "buyurtma_berish_uz_" + productId);
            button3 = InlineButtonUtilManager.button("Savatchaga qo'shish", "savatchaga_qoshish_uz_" + productId);
        } else if (language.equals(ProfileBotLanguage.RUSSIAN)) {
            button1 = InlineButtonUtilManager.button("Muddatli to'lov", "muddatli_tolov_uz_" + productId);
            button2 = InlineButtonUtilManager.button("Buyurtma Berish", "buyurtma_berish_uz_" + productId);
            button3 = InlineButtonUtilManager.button("Savatchaga qo'shish", "savatchaga_qoshish_uz_" + productId);
        } else {
            button1 = InlineButtonUtilManager.button("Muddatli to'lov", "muddatli_tolov_uz_" + productId);
            button2 = InlineButtonUtilManager.button("Buyurtma Berish", "buyurtma_berish_uz_" + productId);
            button3 = InlineButtonUtilManager.button("Savatchaga qo'shish", "savatchaga_qoshish_uz_" + productId);
        }
        List<InlineKeyboardButton> row1 = InlineButtonUtilManager.row(button1, button2);
        List<InlineKeyboardButton> row2 = InlineButtonUtilManager.row(button3);
        return InlineButtonUtilManager.keyboard(InlineButtonUtilManager.rowList(row1, row2));
    }

    /**
     *
     *
     * Buyurtmani yetkazish yoki dostacka qilish
     *
     */

    public static InlineKeyboardMarkup menuBuyurtmaTypeKeyboard(Integer buyurtmaId, ProfileBotLanguage language) {
        InlineKeyboardButton button1;
        InlineKeyboardButton button2;

        if (language.equals(ProfileBotLanguage.UZBEK)) {
             button1 = InlineButtonUtilManager.button("Yetkazib Berish", "yetkazib_berish_uz_" + buyurtmaId);
             button2 = InlineButtonUtilManager.button("O'zim olib kelaman", "olib_ketish_uz_" + buyurtmaId);
        } else if (language.equals(ProfileBotLanguage.RUSSIAN)) {
             button1 = InlineButtonUtilManager.button("Доставка", "yetkazib_berish_ru_" + buyurtmaId);
             button2 = InlineButtonUtilManager.button("я сам принесу", "olib_ketish_ru_" + buyurtmaId);
        } else {
             button1 = InlineButtonUtilManager.button("Етказиб Бериш", "yetkazib_berish_krill_" + buyurtmaId);
             button2 = InlineButtonUtilManager.button("Ўзим олиб келаман", "olib_ketish_krill_" + buyurtmaId);
        }
        List<InlineKeyboardButton> row1 = InlineButtonUtilManager.row(button1, button2);
        return InlineButtonUtilManager.keyboard(InlineButtonUtilManager.rowList(row1));
    }

    /**
     *
     *
     * Joylashuv yuborish uchun bot tiliga qarab
     *
     */

    public static ReplyKeyboardMarkup menuSingleKeyboardsUzb() {
        KeyboardButton button3 = new KeyboardButton("\uD83D\uDCCD Joylashuvni yuborish");
        button3.setRequestLocation(true);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(button3);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(List.of(row2));

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup menuSingleKeyboardsRus() {
        KeyboardButton button3 = new KeyboardButton("\uD83D\uDCCD Отправить местоположение");
        button3.setRequestLocation(true);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(button3);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(List.of(row2));

        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup menuSingleKeyboardsKrill() {
        KeyboardButton button3 = new KeyboardButton("\uD83D\uDCCD Жойлашувни юбориш");
        button3.setRequestLocation(true);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(button3);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(List.of(row2));

        return replyKeyboardMarkup;
    }

    /**
     *
     *
     * Buyurtmalarni admin tasdiqlashi kerak ana shuning uchun kerak boladigan method
     *
     */

    public static InlineKeyboardMarkup menuCheckBuyurtmaKeyboardUZB(Long userId) {
        InlineKeyboardButton button1 = InlineButtonUtilManager.button("Buyurtmalarni Tasdiqlash", "buyurtmalarni_tasdiqlash_uz_" + userId);
        InlineKeyboardButton button2 = InlineButtonUtilManager.button("Buyurtmalarni korib chiqish", "buyurtmalarni_korish_uz_" + userId);
        List<InlineKeyboardButton> row1 = InlineButtonUtilManager.row(button1);
        List<InlineKeyboardButton> row2 = InlineButtonUtilManager.row(button2);
        return InlineButtonUtilManager.keyboard(InlineButtonUtilManager.rowList(row1, row2));
    }

    public static InlineKeyboardMarkup menuCheckBuyurtmaKeyboardRUS(Long buyurtmaId) {
        InlineKeyboardButton button1 = InlineButtonUtilManager.button("Подтвердить заказы", "buyurtmalarni_tasdiqlash_rus_" + buyurtmaId);
        InlineKeyboardButton button2 = InlineButtonUtilManager.button("Просмотрите заказы", "buyurtmalarni_korish_rus_" + buyurtmaId);
        List<InlineKeyboardButton> row1 = InlineButtonUtilManager.row(button1);
        List<InlineKeyboardButton> row2 = InlineButtonUtilManager.row(button2);
        return InlineButtonUtilManager.keyboard(InlineButtonUtilManager.rowList(row1, row2));
    }

    public static InlineKeyboardMarkup menuCheckBuyurtmaKeyboardKRILL(Long buyurtmaId) {
        InlineKeyboardButton button1 = InlineButtonUtilManager.button("Буюртмаларни Тасдиқлаш", "buyurtmalarni_tasdiqlash_krill_" + buyurtmaId);
        InlineKeyboardButton button2 = InlineButtonUtilManager.button("Буюртмаларни кориб чиқиш", "buyurtmalarni_korish_krill_" + buyurtmaId);
        List<InlineKeyboardButton> row2 = InlineButtonUtilManager.row(button1);
        List<InlineKeyboardButton> row1 = InlineButtonUtilManager.row(button2);
        return InlineButtonUtilManager.keyboard(InlineButtonUtilManager.rowList(row1, row2));
    }

    /**
     *
     *
     * Buyurtmalarni user yetib borgan yoki bormaganini aytishi kerak
     *
     */

    public static InlineKeyboardMarkup menuStatusBuyurtmaKeyboardUZB(Long userId) {
        InlineKeyboardButton button1 = InlineButtonUtilManager.button("Buyurtma Yetib keldi", "buyurma_yetib_keldi_uz_" + userId);
        List<InlineKeyboardButton> row1 = InlineButtonUtilManager.row(button1);
        return InlineButtonUtilManager.keyboard(InlineButtonUtilManager.rowList(row1));
    }

    public static InlineKeyboardMarkup menuStatusBuyurtmaKeyboardRUS(Long buyurtmaId) {
        InlineKeyboardButton button1 = InlineButtonUtilManager.button("Заказ прибыл", "buyurma_yetib_keldi_ru_" + buyurtmaId);
        List<InlineKeyboardButton> row1 = InlineButtonUtilManager.row(button1);
        return InlineButtonUtilManager.keyboard(InlineButtonUtilManager.rowList(row1));
    }

    public static InlineKeyboardMarkup menuStatusBuyurtmaKeyboardKRILL(Long buyurtmaId) {
        InlineKeyboardButton button1 = InlineButtonUtilManager.button("Буюртма Етиб келди", "buyurma_yetib_keldi_krill_" + buyurtmaId);
        List<InlineKeyboardButton> row2 = InlineButtonUtilManager.row(button1);
        return InlineButtonUtilManager.keyboard(InlineButtonUtilManager.rowList(row2));
    }

    /**
     *
     * Bu methoslar Savatchadan narsa sotib olishga yordam beradi tiliga qarab
     *
     */

    public static InlineKeyboardMarkup menuBuyurtmaTypeSavatchaKeyboardUZB(Long userId) {
        InlineKeyboardButton button1 = InlineButtonUtilManager.button("Yetkazib Berish", "yetkazib_berish_savatcha_uz_" + userId);
        InlineKeyboardButton button2 = InlineButtonUtilManager.button("O'zim olib kelaman", "olib_ketish_savatcha_uz_" + userId);
        InlineKeyboardButton button3 = InlineButtonUtilManager.button("Savatchani Tozalash", "tozalash_uz_" + userId);
        List<InlineKeyboardButton> row1 = InlineButtonUtilManager.row(button1, button2);
        List<InlineKeyboardButton> row2 = InlineButtonUtilManager.row(button3);
        return InlineButtonUtilManager.keyboard(InlineButtonUtilManager.rowList(row1, row2));
    }

    public static InlineKeyboardMarkup menuBuyurtmaTypeSavatchaKeyboardRUS(Long userId) {
        InlineKeyboardButton button1 = InlineButtonUtilManager.button("Доставка", "yetkazib_berish_savatcha_ru_" + userId);
        InlineKeyboardButton button2 = InlineButtonUtilManager.button("я сам принесу", "olib_ketish_savatcha_ru_" + userId);
        InlineKeyboardButton button3 = InlineButtonUtilManager.button("Очистка корзины", "tozalash_ru_" + userId);
        List<InlineKeyboardButton> row1 = InlineButtonUtilManager.row(button1, button2);
        List<InlineKeyboardButton> row2 = InlineButtonUtilManager.row(button3);
        return InlineButtonUtilManager.keyboard(InlineButtonUtilManager.rowList(row1, row2));
    }

    public static InlineKeyboardMarkup menuBuyurtmaTypeSavatchaKeyboardKRILL(Long userId) {
        InlineKeyboardButton button1 = InlineButtonUtilManager.button("Етказиб Бериш", "yetkazib_berish_savatcha_krill_" + userId);
        InlineKeyboardButton button2 = InlineButtonUtilManager.button("Ўзим олиб келаман", "olib_ketish_savatcha_krill_" + userId);
        InlineKeyboardButton button3 = InlineButtonUtilManager.button("Саватчани Тозалаш", "tozalash_krill_" + userId);
        List<InlineKeyboardButton> row1 = InlineButtonUtilManager.row(button1, button2);
        List<InlineKeyboardButton> row2 = InlineButtonUtilManager.row(button3);
        return InlineButtonUtilManager.keyboard(InlineButtonUtilManager.rowList(row1, row2));
    }
}
