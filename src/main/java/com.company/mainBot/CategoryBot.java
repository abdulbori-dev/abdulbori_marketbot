package com.company.mainBot;

import com.company.manager.BotManager;
import com.company.util.InlineButtonUtilCategory;
import com.company.util.InlineButtonUtilProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
public class CategoryBot {
    @Autowired
    private BotManager botManager;
    @Autowired
    private ProductCategoryBot productCategoryBot;
    @Autowired
    private MuddatliTolovBot muddatliTolovBot;

    public void handlerText(User user, Message message) {
        String text = message.getText();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(user.getId()));

        if (text.equals("Category") || text.equals("Категория") || text.equals("Сатегорй")) {
            if (text.equals("Category")) {
                sendMessage.setText("Birini Tanlang: ");
                sendMessage.setReplyMarkup(InlineButtonUtilCategory.menuCategoryKeyboardUZB());
                botManager.sendMessage(sendMessage);
            } else if (text.equals("Категория")) {
                sendMessage.setText("Выбери один: ");
                sendMessage.setReplyMarkup(InlineButtonUtilCategory.menuCategoryKeyboardRUS());
                botManager.sendMessage(sendMessage);
            } else {
                sendMessage.setText("Бирини Танланг: ");
                sendMessage.setReplyMarkup(InlineButtonUtilCategory.menuCategoryKeyboardKRILL());
                botManager.sendMessage(sendMessage);
            }
        } else {
            muddatliTolovBot.handlerText(user, message);
        }
    }

    public void handlerCallbackQuery(User user, String text, Integer messageId) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(String.valueOf(user.getId()));
        editMessageText.setMessageId(messageId);

        if (text.equals("mebellar_uz") || text.equals("mebellar_rus") || text.equals("mebellar_krill")) {

            if (text.equals("mebellar_uz")) {
                editMessageText.setText("Birini Tanlang: ");
                editMessageText.setReplyMarkup(InlineButtonUtilProductCategory.menuProductCategoryMebellarKeyboardUZB());
                botManager.sendMsg(editMessageText);
            } else if (text.equals("mebellar_rus")) {
                editMessageText.setText("Выбери один: ");
                editMessageText.setReplyMarkup(InlineButtonUtilProductCategory.menuProductCategoryMebellarKeyboardRUS());
                botManager.sendMsg(editMessageText);
            } else {
                editMessageText.setText("Бирини Танланг: ");
                editMessageText.setReplyMarkup(InlineButtonUtilProductCategory.menuProductCategoryMebellarKeyboardKRILL());
                botManager.sendMsg(editMessageText);
            }
        } else {
            productCategoryBot.handlerCallbackQuery(user, text, messageId);
        }
    }
}
