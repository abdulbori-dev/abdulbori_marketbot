package com.company.mainBot.admin;

import com.company.dto.*;
import com.company.enums.*;
import com.company.manager.BotManager;
import com.company.service.*;
import com.company.util.InlineButtonUtilAdmin;
import com.company.util.InlineButtonUtilManager;
import com.company.util.InlineButtonUtilProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class AdminBot {
    @Autowired
    private BotManager botManager;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private MuddatliTolovService muddatliTolovService;

    public Map<Long, AdminAddCategoryEnum> adminCategoryMap = new HashMap<>();
    public Map<Long, AdminAddProductCategoryEnum> adminProductCategoryMap = new HashMap<>();
    public Map<Long, AdminAddProductEnum> adminAddProductEnumHashMap = new HashMap<>();

    public void handlerText(User user, Message message) {
        if (profileService.checkRole(ProfileRole.USER.name(), user.getId())) {
            return;
        }
        String text = message.getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        String language = profileService.getProfileLanguage(user.getId());

        if (adminAddProductEnumHashMap.containsKey(user.getId())) {
            if (adminAddProductEnumHashMap.get(user.getId()).equals(AdminAddProductEnum.TITLE)) {
                productService.updateTitle(text);

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("Description: ");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("Описание: ");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("Тавсиф: ");

                botManager.sendMessage(sendMessage);

                adminAddProductEnumHashMap.remove(user.getId());
                adminAddProductEnumHashMap.put(user.getId(), AdminAddProductEnum.DESCRIPTION);
            } else if (adminAddProductEnumHashMap.get(user.getId()).equals(AdminAddProductEnum.DESCRIPTION)) {
                productService.updateDescription(text);

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("Price: ");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("Цена: ");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("Нарх: ");

                botManager.sendMessage(sendMessage);

                adminAddProductEnumHashMap.remove(user.getId());
                adminAddProductEnumHashMap.put(user.getId(), AdminAddProductEnum.PRICE);
            } else if (adminAddProductEnumHashMap.get(user.getId()).equals(AdminAddProductEnum.PRICE)) {
                productService.updatePrice(Long.valueOf(text));

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("ProductCategory id: ");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("Ид категории продукта: ");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("Продуст Сатегорй ид: ");

                botManager.sendMessage(sendMessage);

                adminAddProductEnumHashMap.remove(user.getId());
                adminAddProductEnumHashMap.put(user.getId(), AdminAddProductEnum.PRODUCT_CATEGORY_ID);
            } else if (adminAddProductEnumHashMap.get(user.getId()).equals(AdminAddProductEnum.PRODUCT_CATEGORY_ID)) {
                productService.updateProductCategory(Integer.valueOf(text));

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("Product ni rasmini jonating: ");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("Добавьте изображение товара: ");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("Продуст ни расмини жонатинг: ");

                botManager.sendMessage(sendMessage);

                adminAddProductEnumHashMap.remove(user.getId());
                adminAddProductEnumHashMap.put(user.getId(), AdminAddProductEnum.PHOTO_ID);
            }
        } else if (adminProductCategoryMap.containsKey(user.getId())) {
            if (adminProductCategoryMap.get(user.getId()).equals(AdminAddProductCategoryEnum.TEXT_UZ)) {
                productCategoryService.updateTextUz(text);

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("(UZB) Serverda ko'rinadigan userga ko'rinmaydigan textni kiriting: ");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("(UZB) Введите текст, который не виден пользователю на сервере: ");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("(UZB) Серверда кўринадиган усерга кўринмайдиган техтни киритинг: ");

                botManager.sendMessage(sendMessage);

                adminProductCategoryMap.remove(user.getId());
                adminProductCategoryMap.put(user.getId(), AdminAddProductCategoryEnum.CALLBACK_UZ);
            } else if (adminProductCategoryMap.get(user.getId()).equals(AdminAddProductCategoryEnum.CALLBACK_UZ)) {

                productCategoryService.updateCallbackUz(text);

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("(RUS) Userga ko'rinadigan textni kiriting:  ");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("(RUS) Введите текст, видимый пользователю: ");
                else
                    sendMessage.setText("(RUS) Усерга кўринадиган техтни киритинг: ");

                botManager.sendMessage(sendMessage);

                adminProductCategoryMap.remove(user.getId());
                adminProductCategoryMap.put(user.getId(), AdminAddProductCategoryEnum.TEXT_RU);
            } else if (adminProductCategoryMap.get(user.getId()).equals(AdminAddProductCategoryEnum.TEXT_RU)) {

                productCategoryService.updateTextRu(text);

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("(RUS) Serverda ko'rinadigan userga ko'rinmaydigan textni kiriting: ");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("(RUS) Введите текст, который не виден пользователю на сервере: ");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("(RUS) Серверда кўринадиган усерга кўринмайдиган техтни киритинг: ");

                botManager.sendMessage(sendMessage);

                adminProductCategoryMap.remove(user.getId());
                adminProductCategoryMap.put(user.getId(), AdminAddProductCategoryEnum.CALLBACK_RU);
            } else if (adminProductCategoryMap.get(user.getId()).equals(AdminAddProductCategoryEnum.CALLBACK_RU)) {

                productCategoryService.updateCallBackRu(text);

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("(RUS) Userga ko'rinadigan textni kiriting:  ");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("(RUS) Введите текст, видимый пользователю: ");
                else
                    sendMessage.setText("(RUS) Усерга кўринадиган техтни киритинг: ");

                botManager.sendMessage(sendMessage);

                adminProductCategoryMap.remove(user.getId());
                adminProductCategoryMap.put(user.getId(), AdminAddProductCategoryEnum.TEXT_KRILL);
            } else if (adminProductCategoryMap.get(user.getId()).equals(AdminAddProductCategoryEnum.TEXT_KRILL)) {

                productCategoryService.updateTextKrill(text);

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("(RUS) Serverda ko'rinadigan userga ko'rinmaydigan textni kiriting: ");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("(RUS) Введите текст, который не виден пользователю на сервере: ");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("(RUS) Серверда кўринадиган усерга кўринмайдиган техтни киритинг: ");

                botManager.sendMessage(sendMessage);

                adminProductCategoryMap.remove(user.getId());
                adminProductCategoryMap.put(user.getId(), AdminAddProductCategoryEnum.CALLBACK_KRILL);
            } else if (adminProductCategoryMap.get(user.getId()).equals(AdminAddProductCategoryEnum.CALLBACK_KRILL)) {
                productCategoryService.updateCallbackKrill(text);

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("(RUS) Category ni kiriting (id sini): ");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("(RUS) Введите категорию (ид): ");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("(RUS) Сатегорй ни киритинг (ид сини): ");

                botManager.sendMessage(sendMessage);

                adminProductCategoryMap.remove(user.getId());
                adminProductCategoryMap.put(user.getId(), AdminAddProductCategoryEnum.CATEGORY_ID);
            } else if (adminProductCategoryMap.get(user.getId()).equals(AdminAddProductCategoryEnum.CATEGORY_ID)) {
                productCategoryService.updateCategoryId(Integer.valueOf(text));

                sendMessage.setText("Success");
                botManager.sendMessage(sendMessage);

                adminProductCategoryMap.remove(user.getId());
            }
        } else if (adminCategoryMap.containsKey(user.getId())) {
            if (adminCategoryMap.get(user.getId()).equals(AdminAddCategoryEnum.TEXT_UZ)) {

                categoryService.updateTextUz(text);

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("(UZB) Serverda ko'rinadigan userga ko'rinmaydigan textni kiriting: ");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("(UZB) Введите текст, который не виден пользователю на сервере: ");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("(UZB) Серверда кўринадиган усерга кўринмайдиган техтни киритинг: ");

                botManager.sendMessage(sendMessage);

                adminCategoryMap.remove(user.getId());
                adminCategoryMap.put(user.getId(), AdminAddCategoryEnum.CALLBACK_UZ);
            } else if (adminCategoryMap.get(user.getId()).equals(AdminAddCategoryEnum.CALLBACK_UZ)) {

                categoryService.updateCallbackUz(text);

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("(RUS) Userga ko'rinadigan textni kiriting:  ");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("(RUS) Введите текст, видимый пользователю: ");
                else
                    sendMessage.setText("(RUS) Усерга кўринадиган техтни киритинг: ");

                botManager.sendMessage(sendMessage);

                adminCategoryMap.remove(user.getId());
                adminCategoryMap.put(user.getId(), AdminAddCategoryEnum.TEXT_RU);
            } else if (adminCategoryMap.get(user.getId()).equals(AdminAddCategoryEnum.TEXT_RU)) {

                categoryService.updateTextRu(text);

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("(RUS) Serverda ko'rinadigan userga ko'rinmaydigan textni kiriting: ");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("(RUS) Введите текст, который не виден пользователю на сервере: ");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("(RUS) Серверда кўринадиган усерга кўринмайдиган техтни киритинг: ");

                botManager.sendMessage(sendMessage);

                adminCategoryMap.remove(user.getId());
                adminCategoryMap.put(user.getId(), AdminAddCategoryEnum.CALLBACK_RU);
            } else if (adminCategoryMap.get(user.getId()).equals(AdminAddCategoryEnum.CALLBACK_RU)) {

                categoryService.updateCallBackRu(text);

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("(RUS) Userga ko'rinadigan textni kiriting:  ");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("(RUS) Введите текст, видимый пользователю: ");
                else
                    sendMessage.setText("(RUS) Усерга кўринадиган техтни киритинг: ");

                botManager.sendMessage(sendMessage);

                adminCategoryMap.remove(user.getId());
                adminCategoryMap.put(user.getId(), AdminAddCategoryEnum.TEXT_KRILL);
            } else if (adminCategoryMap.get(user.getId()).equals(AdminAddCategoryEnum.TEXT_KRILL)) {

                categoryService.updateTextKrill(text);

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("(RUS) Serverda ko'rinadigan userga ko'rinmaydigan textni kiriting: ");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("(RUS) Введите текст, который не виден пользователю на сервере: ");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("(RUS) Серверда кўринадиган усерга кўринмайдиган техтни киритинг: ");

                botManager.sendMessage(sendMessage);

                adminCategoryMap.remove(user.getId());
                adminCategoryMap.put(user.getId(), AdminAddCategoryEnum.CALLBACK_KRILL);
            } else if (adminCategoryMap.get(user.getId()).equals(AdminAddCategoryEnum.CALLBACK_KRILL)) {

                categoryService.updateCallbackKrill(text);

                sendMessage.setText("Success");
                botManager.sendMessage(sendMessage);

                adminCategoryMap.remove(user.getId());
            }
        } else if (text.equals("/start")) {
            if (language.equals(ProfileBotLanguage.UZBEK.name())) {
                sendMessage.setText("Welcome to Admin: " + user.getFirstName());
                sendMessage.setReplyMarkup(InlineButtonUtilAdmin.menuNextAdminMenu(ProfileBotLanguage.UZBEK));
                botManager.sendMessage(sendMessage);
            } else if (language.equals(ProfileBotLanguage.RUSSIAN.name())) {
                sendMessage.setText("Welcome to Admin: " + user.getFirstName());
                sendMessage.setReplyMarkup(InlineButtonUtilAdmin.menuNextAdminMenu(ProfileBotLanguage.RUSSIAN));
                botManager.sendMessage(sendMessage);
            } else {
                sendMessage.setText("Welcome to Admin: " + user.getFirstName());
                sendMessage.setReplyMarkup(InlineButtonUtilAdmin.menuNextAdminMenu(ProfileBotLanguage.KRILL));
                botManager.sendMessage(sendMessage);
            }
        } else if (text.equals("Admin qo'shish") || text.equals("Добавить администратора") || text.equals("Админ қўшиш")) {
            if (language.equals(ProfileBotLanguage.UZBEK.name())) {
                sendMessage.setText("Admin qilmoqchi bolgan useringiz ustiga bosing: ");
                sendMessage.setReplyMarkup(menuAdminKeyboard());
                botManager.sendMessage(sendMessage);
            } else if (language.equals(ProfileBotLanguage.RUSSIAN.name())) {
                sendMessage.setText("Нажмите на пользователя, которого хотите администрировать: ");
                sendMessage.setReplyMarkup(menuAdminKeyboard());
                botManager.sendMessage(sendMessage);
            } else {
                sendMessage.setText("Админ қилмоқчи болган усерингиз устига босинг: ");
                sendMessage.setReplyMarkup(menuAdminKeyboard());
                botManager.sendMessage(sendMessage);
            }
        } else if (text.equals("Category qo'shish") || text.equals("Добавить категорию") || text.equals("категорй қўшиш")) {
            CategoryDTO category = new CategoryDTO();

            categoryService.createProfile(category);

            if (text.equals("Category qo'shish"))
                sendMessage.setText("(UZB) Userga ko'rinadigan textni kiriting:  ");
            else if (text.equals("Добавить категорию"))
                sendMessage.setText("(UZB) Введите текст, видимый пользователю: ");
            else
                sendMessage.setText("(UZB) Усерга кўринадиган техтни киритинг: ");

            botManager.sendMessage(sendMessage);

            adminCategoryMap.put(user.getId(), AdminAddCategoryEnum.TEXT_UZ);
        } else if (text.equals("Categorylar") || text.equals("Категории") || text.equals("категорйлар")) {
            if (text.equals("Categorylar")) {
                StringBuilder builder = new StringBuilder("Kategoryalar: " + "\n");

                List<CategoryDTO> categoryList = categoryService.getAllBy();

                for (CategoryDTO category : categoryList) {
                    builder.append("Id: ").append(category.getId()).append("\n");
                    builder.append("Name (UZB): ").append(category.getTextUz()).append("\n");
                    builder.append("Callback (UZB): ").append(category.getCallbackUz()).append("\n");
                    builder.append("Name (RUS): ").append(category.getTextRu()).append("\n");
                    builder.append("Callback (RUS): ").append(category.getCallbackRu()).append("\n");
                    builder.append("Name (KRILL): ").append(category.getTextEn()).append("\n");
                    builder.append("Callback (KRILL): ").append(category.getCallback()).append("\n");
                    builder.append("---" + "\n");
                }
                sendMessage.setText(builder.toString());
                botManager.sendMessage(sendMessage);
            } else if (text.equals("Категории")) {
                StringBuilder builder = new StringBuilder("Категории: " + "\n");

                List<CategoryDTO> categoryList = categoryService.getAllBy();

                for (CategoryDTO category : categoryList) {
                    builder.append("Id: ").append(category.getId()).append("\n");
                    builder.append("Имя (UZB): ").append(category.getTextUz()).append("\n");
                    builder.append("Callback (UZB): ").append(category.getCallbackUz()).append("\n");
                    builder.append("Имя (RUS): ").append(category.getTextRu()).append("\n");
                    builder.append("Callback (RUS): ").append(category.getCallbackRu()).append("\n");
                    builder.append("Имя (KRILL): ").append(category.getTextEn()).append("\n");
                    builder.append("Callback (KRILL): ").append(category.getCallback()).append("\n");
                    builder.append("---" + "\n");
                }
                sendMessage.setText(builder.toString());
                botManager.sendMessage(sendMessage);
            } else {
                StringBuilder builder = new StringBuilder("категорйлар: " + "\n");

                List<CategoryDTO> categoryList = categoryService.getAllBy();

                for (CategoryDTO category : categoryList) {
                    builder.append("Id: ").append(category.getId()).append("\n");
                    builder.append("Исм (UZB): ").append(category.getTextUz()).append("\n");
                    builder.append("Callback (UZB): ").append(category.getCallbackUz()).append("\n");
                    builder.append("Исм (RUS): ").append(category.getTextRu()).append("\n");
                    builder.append("Callback (RUS): ").append(category.getCallbackRu()).append("\n");
                    builder.append("Исм (KRILL): ").append(category.getTextEn()).append("\n");
                    builder.append("Callback (KRILL): ").append(category.getCallback()).append("\n");
                    builder.append("---" + "\n");
                }
                sendMessage.setText(builder.toString());
                botManager.sendMessage(sendMessage);
            }
        } else if (text.equals("Product Category qo'shish") || text.equals("Добавить категорию продукта") || text.equals("Продукт Сатегорй қўшиш")) {
            ProductCategoryDTO productCategory = new ProductCategoryDTO();

            productCategoryService.createProfile(productCategory);

            if (language.equals(ProfileBotLanguage.UZBEK.name()))
                sendMessage.setText("(UZB) Userga ko'rinadigan textni kiriting:  ");
            else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                sendMessage.setText("(UZB) Введите текст, видимый пользователю: ");
            else
                sendMessage.setText("(UZB) Усерга кўринадиган техтни киритинг: ");

            botManager.sendMessage(sendMessage);

            adminProductCategoryMap.put(user.getId(), AdminAddProductCategoryEnum.TEXT_UZ);
        } else if (text.equals("Product Categorylar") || text.equals("категории продукта") || text.equals("Продукт Сатегорйлар")) {
            if (text.equals("Product Categorylar")) {
                StringBuilder builder = new StringBuilder("Product Categorylar: " + "\n");

                List<ProductCategoryDTO> productCategoryList = productCategoryService.getAllBy();

                for (ProductCategoryDTO productCategory : productCategoryList) {
                    builder.append("Id: ").append(productCategory.getId()).append("\n");
                    builder.append("Name (UZB): ").append(productCategory.getTextUz()).append("\n");
                    builder.append("Callback (UZB): ").append(productCategory.getCallbackUz()).append("\n");
                    builder.append("Name (RUS): ").append(productCategory.getTextRu()).append("\n");
                    builder.append("Callback (RUS): ").append(productCategory.getCallbackRu()).append("\n");
                    builder.append("Name (KRILL): ").append(productCategory.getTextKrill()).append("\n");
                    builder.append("Callback (KRILL): ").append(productCategory.getCallbackKrill()).append("\n");
                    builder.append("Category id: ").append(productCategory.getCategoryId()).append("\n");
                    builder.append("---" + "\n");
                }
                sendMessage.setText(builder.toString());
                botManager.sendMessage(sendMessage);
            } else if (text.equals("категории продукта")) {
                StringBuilder builder = new StringBuilder("категории продукта: " + "\n");

                List<ProductCategoryDTO> productCategoryList = productCategoryService.getAllBy();

                for (ProductCategoryDTO productCategory : productCategoryList) {
                    builder.append("Id: ").append(productCategory.getId()).append("\n");
                    builder.append("Имя (UZB): ").append(productCategory.getTextUz()).append("\n");
                    builder.append("Callback (UZB): ").append(productCategory.getCallbackUz()).append("\n");
                    builder.append("Имя (RUS): ").append(productCategory.getTextRu()).append("\n");
                    builder.append("Callback (RUS): ").append(productCategory.getCallbackRu()).append("\n");
                    builder.append("Имя (KRILL): ").append(productCategory.getTextKrill()).append("\n");
                    builder.append("Callback (KRILL): ").append(productCategory.getCallbackKrill()).append("\n");
                    builder.append("Category id: ").append(productCategory.getCategoryId()).append("\n");
                    builder.append("---" + "\n");
                }
                sendMessage.setText(builder.toString());
                botManager.sendMessage(sendMessage);
            } else {
                StringBuilder builder = new StringBuilder("Продукт Сатегорйлар: " + "\n");

                List<ProductCategoryDTO> productCategoryList = productCategoryService.getAllBy();

                for (ProductCategoryDTO productCategory : productCategoryList) {
                    builder.append("Id: ").append(productCategory.getId()).append("\n");
                    builder.append("Исм (UZB): ").append(productCategory.getTextUz()).append("\n");
                    builder.append("Callback (UZB): ").append(productCategory.getCallbackUz()).append("\n");
                    builder.append("Исм (RUS): ").append(productCategory.getTextRu()).append("\n");
                    builder.append("Callback (RUS): ").append(productCategory.getCallbackRu()).append("\n");
                    builder.append("Исм (KRILL): ").append(productCategory.getTextKrill()).append("\n");
                    builder.append("Callback (KRILL): ").append(productCategory.getCallbackKrill()).append("\n");
                    builder.append("Category id: ").append(productCategory.getCategoryId()).append("\n");
                    builder.append("---" + "\n");
                }
                sendMessage.setText(builder.toString());
                botManager.sendMessage(sendMessage);
            }
        } else if (text.equals("Product qo'shish") || text.equals("Добавить продукт") || text.equals("Продукт қўшиш")) {
            ProductDTO product = new ProductDTO();

            productService.createProfile(product);

            if (language.equals(ProfileBotLanguage.UZBEK.name()))
                sendMessage.setText("Title :  ");
            else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                sendMessage.setText("Заголовок: ");
            else
                sendMessage.setText("Титле: ");

            botManager.sendMessage(sendMessage);

            adminAddProductEnumHashMap.put(user.getId(), AdminAddProductEnum.TITLE);
        } else if (text.equals("Bot Statistikasi") || text.equals("Статистика ботов") || text.equals("Бот Статистикаси")) {
            if (text.equals("Bot Statistikasi")) {
                StringBuilder builder = new StringBuilder("\uD83E\uDDD1\uD83C\uDFFB\u200D\uD83D\uDCBB Botdagi obunachilar: " + profileService.getAllProfileCount() + "\n\n");

                builder.append("Oxirgi 24 soatda: ").append(profileService.getAllProfileCountCurrentDay()).append("\n");
                builder.append("Oxirgi 1 haftada: ").append(profileService.getAllProfileCountCurrentWeek()).append("\n");
                builder.append("Oxirgi 1 oyda: ").append(profileService.getAllProfileCountCurrentMonth()).append("\n\n");
                builder.append("\uD83D\uDCCA @abdulbori_marketbot statistikasi");
                sendMessage.setText(builder.toString());

                botManager.sendMessage(sendMessage);
            } else if (text.equals("Статистика ботов")) {
                StringBuilder builder = new StringBuilder("\uD83E\uDDD1\uD83C\uDFFB\u200D\uD83D\uDCBB Подписчики на боте: " + profileService.getAllProfileCount() + "\n\n");

                builder.append("За последние 24 часа: ").append(profileService.getAllProfileCountCurrentDay()).append("\n");
                builder.append("За последнюю 1 неделю: ").append(profileService.getAllProfileCountCurrentWeek()).append("\n");
                builder.append("За последний 1 месяц: ").append(profileService.getAllProfileCountCurrentMonth()).append("\n\n");
                builder.append("\uD83D\uDCCA @abdulbori_marketbot статистика");
                sendMessage.setText(builder.toString());

                botManager.sendMessage(sendMessage);
            } else {
                StringBuilder builder = new StringBuilder("\uD83E\uDDD1\uD83C\uDFFB\u200D\uD83D\uDCBB Ботдаги обуначилар: " + profileService.getAllProfileCount() + "\n\n");

                builder.append("Охирги 24 соатда: ").append(profileService.getAllProfileCountCurrentDay()).append("\n");
                builder.append("Охирги 1 ҳафтада: ").append(profileService.getAllProfileCountCurrentWeek()).append("\n");
                builder.append("Охирги 1 ойда: ").append(profileService.getAllProfileCountCurrentMonth()).append("\n\n");
                builder.append("\uD83D\uDCCA @abdulbori_marketbot статистикаси");
                sendMessage.setText(builder.toString());

                botManager.sendMessage(sendMessage);
            }
        }
    }

    public void handlerCallbackQuery(User user, String text, Integer messageId) {

        if (profileService.checkRole(ProfileRole.USER.name(), user.getId())) {
            return;
        }

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(String.valueOf(user.getId()));
        editMessageText.setMessageId(messageId);

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(user.getId()));

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(user.getId()));

        String language = profileService.getProfileLanguage(user.getId());

        if (text.startsWith("user_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            profileService.updateRoleProfile(ProfileRole.ADMIN.name(), Long.valueOf(numberOnly));

            editMessageText.setText("Success");
            botManager.sendMsg(editMessageText);
        } else if (text.startsWith("profile_haqida_malumot_uz_") || text.startsWith("profile_haqida_malumot_rus_") || text.startsWith("profile_haqida_malumot_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            UserDetailDTO userDetail = userDetailService.getByProfileId(Long.valueOf(numberOnly));

            InputFile inputFile = new InputFile();
            inputFile.setMedia(userDetail.getPassportAndYou());

            sendPhoto.setPhoto(inputFile);
            sendPhoto.setCaption("user id: " + userDetail.getUserId() + "\n" +
                    "Organization: " + userDetail.getOrganization() + "\n" +
                    "Position: " + userDetail.getPosition() + "\n" +
                    "Internship: " + userDetail.getInternship() + "\n" +
                    "Salary: " + userDetail.getSalary() + "\n" +
                    "Passport scanner: " + userDetail.getPassportPhotoScanner() + "\n" +
                    "Passport address: " + userDetail.getPassportPhotoAddress() + "\n" +
                    "Phone number: " + userDetail.getPhoneNumber() + "\n" +
                    "add Number: " + userDetail.getAddNumber() + "\n" +
                    "Card: " + userDetail.getCard() + "\n" +
                    "Validity period: " + userDetail.getValidityPeriod() + "\n" +
                    "Card phone number: " + userDetail.getPhoneNumber() + "\n" +
                    "Created date: " + userDetail.getCreatedDate() + "\n");
            botManager.send(sendPhoto);
        } else if (text.startsWith("buyurtma_haqida_malumot_uz_") || text.startsWith("buyurtma_haqida_malumot_rus_") || text.startsWith("buyurtma_haqida_malumot_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            MuddatliTolovDTO muddatliTolov = muddatliTolovService.getByProfileId(Long.valueOf(numberOnly));

            sendMessage.setText("user id" + muddatliTolov.getUserId() + "\n" +
                    "Product id: " + muddatliTolov.getProductId() + "\n" +
                    "Created date: " + muddatliTolov.getCreatedDate() + "\n" +
                    "Status: " + muddatliTolov.getStatus() + "\n" +
                    "Type: " + muddatliTolov.getType() + "\n" +
                    "Price: " + muddatliTolov.getPrice());
            botManager.sendMessage(sendMessage);
        } else if (text.startsWith("muddatli_tolov_buyurtmani_tasdiqlash_uz_") || text.startsWith("muddatli_tolov_buyurtmani_tasdiqlash_ru_") || text.startsWith("muddatli_tolov_buyurtmani_tasdiqlash_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            muddatliTolovService.updateStatusByUserId(MuddatliTolovStatus.TASDIQLANGAN.name(), Long.valueOf(numberOnly));

            editMessageText.setText("Success");
            botManager.sendMsg(editMessageText);
        } else if (text.startsWith("muddatli_tolov_buyurtmani_bekor_uz_") || text.startsWith("muddatli_tolov_buyurtmani_bekor_ru_") || text.startsWith("muddatli_tolov_buyurtmani_bekor_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            muddatliTolovService.updateStatusByUserId(MuddatliTolovStatus.MALUMOTDA_XATOLIK.name(), Long.valueOf(numberOnly));

            editMessageText.setText("Success");
            botManager.sendMsg(editMessageText);
        }
    }

    public void handlerPhoto(User user, Message message) {

        if (profileService.checkRole(ProfileRole.USER.name(), user.getId())) {
            return;
        }

        if (adminAddProductEnumHashMap.containsKey(user.getId())) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));

            String image = message.getPhoto().get(message.getPhoto().size() - 1).getFileId();

            if (adminAddProductEnumHashMap.get(user.getId()).equals(AdminAddProductEnum.PHOTO_ID)) {
                productService.updatePhotoId(image);

                sendMessage.setText("Success");
                botManager.sendMessage(sendMessage);

                adminAddProductEnumHashMap.remove(user.getId());
            }
        }
    }

    public InlineKeyboardMarkup menuAdminKeyboard() {
        List<InlineKeyboardButton> buttonList = new LinkedList<>();
        for (ProfileDTO dto : profileService.getAllBy()) {
            InlineKeyboardButton button = null;
            button = InlineButtonUtilManager.button(dto.getName(), "user_" + dto.getId());
            buttonList.add(button);
        }

        List<List<InlineKeyboardButton>> rowList = new LinkedList<>();
        for (int j = 0; j < buttonList.size(); j++) {
            rowList.add(InlineButtonUtilManager.row(buttonList.get(j)));
        }
        return InlineButtonUtilManager.keyboard(rowList);
    }
}
