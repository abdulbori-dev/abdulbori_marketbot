package com.company.util;

import com.company.enums.ProfileBotLanguage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class InlineButtonUtilMuddatliTolov {


    /**
     *
     *
     * Bu methodlar Keyboard Muddatli tovni typlarini belgilashda qollaniladi
     *
     *
     **/

    public static InlineKeyboardMarkup menuSingleKeyboard(Integer productId, ProfileBotLanguage language) {
        InlineKeyboardButton button1;
        InlineKeyboardButton button2;
        InlineKeyboardButton button3;
        InlineKeyboardButton button4;

        if (language.equals(ProfileBotLanguage.UZBEK)) {
            button1 = InlineButtonUtilManager.button("3 Oylik", "uch_oylik_uz_" + productId);
            button2 = InlineButtonUtilManager.button("6 Oylik", "olti_oylik_uz_" + productId);
            button3 = InlineButtonUtilManager.button("9 Oylik", "toqqiz_oylik_uz_" + productId);
            button4 = InlineButtonUtilManager.button("12 Oylik", "on_ikki_oylik_uz_" + productId);
        } else if (language.equals(ProfileBotLanguage.RUSSIAN)) {
            button1 = InlineButtonUtilManager.button("3 месяца", "uch_oylik_rus_" + productId);
            button2 = InlineButtonUtilManager.button("6 месяца", "olti_oylik_rus_" + productId);
            button3 = InlineButtonUtilManager.button("9 месяца", "toqqiz_oylik_rus_" + productId);
            button4 = InlineButtonUtilManager.button("12 месяца", "on_ikki_oylik_rus_" + productId);
        } else {
            button1 = InlineButtonUtilManager.button("3 Ойлик", "uch_oylik_krill_" + productId);
            button2 = InlineButtonUtilManager.button("6 Ойлик", "olti_oylik_krill_" + productId);
            button3 = InlineButtonUtilManager.button("9 Ойлик", "toqqiz_oylik_krill_" + productId);
            button4 = InlineButtonUtilManager.button("12 Ойлик", "on_ikki_oylik_krill_" + productId);
        }
        List<InlineKeyboardButton> row1 = InlineButtonUtilManager.row(button1, button2);
        List<InlineKeyboardButton> row3 = InlineButtonUtilManager.row(button3, button4);
        return InlineButtonUtilManager.keyboard(InlineButtonUtilManager.rowList(row1, row3));
    }

    /**
     *
     *
     *  Tasdiqlash
     *
     *
     **/

    public static InlineKeyboardMarkup menuMuddatliTolovniTasdiqlashKeyboard(Integer productId, ProfileBotLanguage language) {
        InlineKeyboardButton button1;
        InlineKeyboardButton button2;

        if (language.equals(ProfileBotLanguage.UZBEK)) {
            button1 = InlineButtonUtilManager.button("Tasdiqlash", "tasdiqlash_uz_" + productId);
            button2 = InlineButtonUtilManager.button("Bekor Qilish", "bekor_qilish_uz_" + productId);
        } else if (language.equals(ProfileBotLanguage.RUSSIAN)) {
            button1 = InlineButtonUtilManager.button("Подтверждение", "tasdiqlash_rus_" + productId);
            button2 = InlineButtonUtilManager.button("Отмена", "bekor_qilish_rus_" + productId);
        } else {
            button1 = InlineButtonUtilManager.button("Тасдиқлаш", "tasdiqlash_krill_" + productId);
            button2 = InlineButtonUtilManager.button("Бекор Қилиш", "bekor_qilish_krill_" + productId);
        }
        List<InlineKeyboardButton> row1 = InlineButtonUtilManager.row(button1, button2);
        return InlineButtonUtilManager.keyboard(InlineButtonUtilManager.rowList(row1));
    }
}
