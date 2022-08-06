package com.company.mainBot.user;

import com.company.dto.CategoryDTO;
import com.company.dto.ProductCategoryDTO;
import com.company.enums.ProfileBotLanguage;
import com.company.manager.BotManager;
import com.company.service.CategoryService;
import com.company.service.ProductCategoryService;
import com.company.service.ProfileService;
import com.company.util.InlineButtonUtilManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.LinkedList;
import java.util.List;

@Component
public class CategoryBot {
    @Autowired
    private BotManager botManager;
    @Autowired
    private ProductCategoryBot productCategoryBot;
    @Autowired
    private MuddatliTolovBot muddatliTolovBot;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProductCategoryService productCategoryService;

    public void handlerText(User user, Message message) {
        String text = message.getText();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(user.getId()));

        if (text.equals("\uD83D\uDD0D Category") || text.equals("\uD83D\uDD0D Категория") || text.equals("\uD83D\uDD0D Сатегорй")) {
            if (text.equals("\uD83D\uDD0D Category")) {
                sendMessage.setText("Birini Tanlang: ");
                sendMessage.setReplyMarkup(menuCategoryKeyboard("UZB"));
                botManager.sendMessage(sendMessage);
            } else if (text.equals("\uD83D\uDD0D Категория")) {
                sendMessage.setText("Выбери один: ");
                sendMessage.setReplyMarkup(menuCategoryKeyboard("RUS"));
                botManager.sendMessage(sendMessage);
            } else {
                sendMessage.setText("Бирини Танланг: ");
                sendMessage.setReplyMarkup(menuCategoryKeyboard("KRILL"));
                botManager.sendMessage(sendMessage);
            }
        } else {
            muddatliTolovBot.handlerText(user, message);
        }
    }

    public void handlerCallbackQuery(User user, String text, Integer messageId, CallbackQuery callback) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(String.valueOf(user.getId()));
        editMessageText.setMessageId(messageId);

        String numberOnly = text.replaceAll("[^0-9]", "");
        String language = profileService.getProfileLanguage(user.getId());
        if (!categoryService.checkCategory(text)) {
            if (language.equals(ProfileBotLanguage.UZBEK.name())) {
                editMessageText.setText("Birini Tanlang: ");
                editMessageText.setReplyMarkup(menuProductCategory("UZB", text));
                botManager.sendMsg(editMessageText);
            } else if (language.equals(ProfileBotLanguage.RUSSIAN.name())) {
                editMessageText.setText("Выбери один: ");
                editMessageText.setReplyMarkup(menuProductCategory("RUS", text));
                botManager.sendMsg(editMessageText);
            } else {
                editMessageText.setText("Бирини Танланг: ");
                editMessageText.setReplyMarkup(menuProductCategory("KRILL", text));
                botManager.sendMsg(editMessageText);
            }
        } else {
            productCategoryBot.handlerCallbackQuery(user, text, messageId, callback);
        }
    }

    public InlineKeyboardMarkup menuCategoryKeyboard(String language) {
        List<InlineKeyboardButton> buttonList = new LinkedList<>();
        for (CategoryDTO dto : categoryService.getAllBy()) {
            InlineKeyboardButton button = null;
            if (language.equals("UZB")) {
                button = InlineButtonUtilManager.button(dto.getTextUz(), dto.getCallbackUz());
            } else if (language.equals("RUS")) {
                button = InlineButtonUtilManager.button(dto.getTextRu(), dto.getCallbackRu());
            } else if (language.equals("KRILL")) {
                button = InlineButtonUtilManager.button(dto.getTextEn(), dto.getCallback());
            }
            buttonList.add(button);
        }

        List<List<InlineKeyboardButton>> rowList = new LinkedList<>();
        for (int j = 0; j < buttonList.size(); j++) {
            rowList.add(InlineButtonUtilManager.row(buttonList.get(j)));
        }
        return InlineButtonUtilManager.keyboard(rowList);
    }

    public InlineKeyboardMarkup menuProductCategory(String language, String callback) {
        List<InlineKeyboardButton> buttonList = new LinkedList<>();
        for (ProductCategoryDTO dto : productCategoryService.getAllByCategory(callback)) {
            InlineKeyboardButton button = switch (language) {
                case "UZB" -> InlineButtonUtilManager.button(dto.getTextUz(), dto.getCallbackUz());
                case "RUS" -> InlineButtonUtilManager.button(dto.getTextRu(), dto.getCallbackRu());
                case "KRILL" -> InlineButtonUtilManager.button(dto.getTextKrill(), dto.getCallbackKrill());
                default -> null;
            };
            buttonList.add(button);
        }

        List<List<InlineKeyboardButton>> rowList = new LinkedList<>();
        for (int j = 0; j < buttonList.size(); j++) {
            rowList.add(InlineButtonUtilManager.row(buttonList.get(j)));
        }
        return InlineButtonUtilManager.keyboard(rowList);
    }
}
