package com.company.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class InlineButtonUtilCategory {

    /**
     *
     * category Manu
     *
     */

    public static InlineKeyboardMarkup menuCategoryKeyboardUZB() {
        InlineKeyboardButton button1 = InlineButtonUtilManager.button("Mebellar", "mebellar_uz");
        InlineKeyboardButton button2 = InlineButtonUtilManager.button("Maishiy Texnikalar", "maishiy_texnikalar_uz");
        InlineKeyboardButton button3 = InlineButtonUtilManager.button("Telefonlar, aksesuralar, gadjetlar", "telegon_va_psv_uz");
        InlineKeyboardButton button4 = InlineButtonUtilManager.button("Noutbuk, PC, Printer", "noutbuk_va_psv_uz");
        InlineKeyboardButton button5 = InlineButtonUtilManager.button("Gilamlar", "gilamlar_uz");
        InlineKeyboardButton button6 = InlineButtonUtilManager.button("Televizor, Audio jihoz", "televizor_va_jihozlar_uz");
        InlineKeyboardButton button7 = InlineButtonUtilManager.button("Sport anjomlari", "sport_anjomlari_uz");
        InlineKeyboardButton button8 = InlineButtonUtilManager.button("Sovg'alar, suvinerlar", "sovgalar_uz");
        InlineKeyboardButton button9 = InlineButtonUtilManager.button("Oshxona jihozlari", "oshxona_jihozlari_uz");
        List<InlineKeyboardButton> row1 = InlineButtonUtilManager.row(button1, button2);
        List<InlineKeyboardButton> row2 = InlineButtonUtilManager.row(button3, button4);
        List<InlineKeyboardButton> row3 = InlineButtonUtilManager.row(button5, button6);
        List<InlineKeyboardButton> row5 = InlineButtonUtilManager.row(button7, button8);
        List<InlineKeyboardButton> row4 = InlineButtonUtilManager.row(button9);
        return InlineButtonUtilManager.keyboard(InlineButtonUtilManager.rowList(row1, row2, row5, row3, row4));
    }

    public static InlineKeyboardMarkup menuCategoryKeyboardRUS() {
        InlineKeyboardButton button1 = InlineButtonUtilManager.button("Мебель", "mebellar_rus");
        InlineKeyboardButton button2 = InlineButtonUtilManager.button("Бытовая техника", "maishiy_texnikalar_rus");
        InlineKeyboardButton button3 = InlineButtonUtilManager.button("Телефоны, аксессуары, гаджеты", "telefon_va_psv_rus");
        InlineKeyboardButton button4 = InlineButtonUtilManager.button("Ноутбук, ПК, принтер", "noutbuk_va_psv_rus");
        InlineKeyboardButton button5 = InlineButtonUtilManager.button("ковры", "gilamlar_rus");
        InlineKeyboardButton button6 = InlineButtonUtilManager.button("ТВ, Аудиотехника", "televizor_va_jihozlar_rus");
        InlineKeyboardButton button7 = InlineButtonUtilManager.button("Спортивное оборудование", "sport_anjomlari_rus");
        InlineKeyboardButton button8 = InlineButtonUtilManager.button("Подарки, сувениры", "sovgalar_rus");
        InlineKeyboardButton button9 = InlineButtonUtilManager.button("Кухонные приборы", "oshxona_jihozlari_rus");
        List<InlineKeyboardButton> row1 = InlineButtonUtilManager.row(button1, button2);
        List<InlineKeyboardButton> row2 = InlineButtonUtilManager.row(button3, button4);
        List<InlineKeyboardButton> row3 = InlineButtonUtilManager.row(button5, button6);
        List<InlineKeyboardButton> row5 = InlineButtonUtilManager.row(button7, button8);
        List<InlineKeyboardButton> row4 = InlineButtonUtilManager.row(button9);
        return InlineButtonUtilManager.keyboard(InlineButtonUtilManager.rowList(row1, row2, row5, row3, row4));
    }

    public static InlineKeyboardMarkup menuCategoryKeyboardKRILL() {
        InlineKeyboardButton button1 = InlineButtonUtilManager.button("Мебеллар", "mebellar_krill");
        InlineKeyboardButton button2 = InlineButtonUtilManager.button("Маиший Техникалар", "maishiy_texnikalar_krill");
        InlineKeyboardButton button3 = InlineButtonUtilManager.button("Телефонлар, аксесуралар, гаджетлар", "telegon_va_psv_krill");
        InlineKeyboardButton button4 = InlineButtonUtilManager.button("Ноутбук, ПС, Принтер", "noutbuk_va_psv_krill");
        InlineKeyboardButton button5 = InlineButtonUtilManager.button("Гиламлар", "gilamlar_krill");
        InlineKeyboardButton button6 = InlineButtonUtilManager.button("Телевизор, Аудио жиҳоз", "televizor_va_jihozlar_krill");
        InlineKeyboardButton button7 = InlineButtonUtilManager.button("Спорт анжомлари", "sport_anjomlari_krill");
        InlineKeyboardButton button8 = InlineButtonUtilManager.button("Совғалар, сувинерлар", "sovgalar_krill");
        InlineKeyboardButton button9 = InlineButtonUtilManager.button("Ошхона жиҳозлари", "oshxona_jihozlari_krill");
        List<InlineKeyboardButton> row1 = InlineButtonUtilManager.row(button1, button2);
        List<InlineKeyboardButton> row2 = InlineButtonUtilManager.row(button3, button4);
        List<InlineKeyboardButton> row3 = InlineButtonUtilManager.row(button5, button6);
        List<InlineKeyboardButton> row5 = InlineButtonUtilManager.row(button7, button8);
        List<InlineKeyboardButton> row4 = InlineButtonUtilManager.row(button9);
        return InlineButtonUtilManager.keyboard(InlineButtonUtilManager.rowList(row1, row2, row5, row3, row4));
    }
}
