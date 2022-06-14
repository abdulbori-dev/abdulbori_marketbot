package com.company.mainBot;

import com.company.dto.BuyurtmalarDTO;
import com.company.dto.ProductDTO;
import com.company.dto.SavatchaDTO;
import com.company.enums.BuyurtmalarStatus;
import com.company.enums.ProfileBotLanguage;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.service.BuyurtmalarService;
import com.company.service.ProductService;
import com.company.service.ProfileService;
import com.company.dto.ProfileDTO;
import com.company.manager.BotManager;
import com.company.service.SavatchaService;
import com.company.util.InlineButtonUtilManager;
import com.company.util.InlineButtonUtilProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MainBot {
    @Autowired
    private BotManager botManager;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private CategoryBot categoryBot;
    @Autowired
    private SavatchaService savatchaService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BuyurtmalarService buyurtmalarService;
    @Autowired
    private MuddatliTolovBot muddatliTolovBot;

    // TODO Bu Admin id si Testdan oldin qoyib qoyish kerak sababi Admin statusi bolgani bilan Admin Create qiladigan query yoq
    private String adminId = "911832156";

    public void handlerText(User user, Message message) {
        String text = message.getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));

        if (text.equals("/start")) {
            if (profileService.getById(Math.toIntExact(user.getId())) && !profileService.checkPhoneNumber(Math.toIntExact(user.getId()))) {
                sendMessage.setReplyMarkup(InlineButtonUtilManager.menuSingleKeyboard());
                sendMessage.setText("Tilni Tanlang: ");
                botManager.sendMessage(sendMessage);

                ProfileDTO dto = new ProfileDTO();
                dto.setStartedDate(LocalDateTime.now());
                dto.setSurname(user.getLastName());
                dto.setName(user.getFirstName());
                dto.setId(Math.toIntExact(user.getId()));
                dto.setRole(ProfileRole.USER);
                dto.setStatus(ProfileStatus.ACTIVE);
                dto.setProfileBotLanguage(ProfileBotLanguage.UZBEK);

                profileService.createProfile(dto);
            } else {
                String language = profileService.getProfileLanguage(Math.toIntExact(user.getId()));

                if (language.equals(ProfileBotLanguage.UZBEK.name())) {
                    sendMessage.setText("Asosiy Menu: ");
                    sendMessage.setReplyMarkup(InlineButtonUtilManager.menuNextLoginUzb());
                    botManager.send(sendMessage);
                } else if (language.equals(ProfileBotLanguage.RUSSIAN.name())) {
                    sendMessage.setText("Основной раздел: ");
                    sendMessage.setReplyMarkup(InlineButtonUtilManager.menuNextLoginRus());
                    botManager.send(sendMessage);
                } else if (language.equals(ProfileBotLanguage.KRILL.name())) {
                    sendMessage.setText("Асосий Мену: ");
                    sendMessage.setReplyMarkup(InlineButtonUtilManager.menuNextLoginKrill());
                    botManager.send(sendMessage);
                }
            }
        } else if (text.equals("Profile") || text.equals("Профиль") || text.equals("Профиле")) {

            ProfileDTO dto = profileService.getByProfileId(Math.toIntExact(user.getId()));
            if (text.equals("Profile")) {
                sendMessage.setText("Sizning Profilingiz \n" +
                        "Ism: " + dto.getName() + "\n" +
                        "Familya: " + dto.getSurname() + "\n" +
                        "Telefon raqam: " + dto.getPhone() + "\n");
                botManager.sendMessage(sendMessage);
            } else if (text.equals("Профиль")) {
                sendMessage.setText("Ваш профиль \n" +
                        "Имя: " + dto.getName() + "\n" +
                        "Фамилия: " + dto.getSurname() + "\n" +
                        "Номер телефона: " + dto.getPhone() + "\n");
                botManager.sendMessage(sendMessage);
            } else {
                sendMessage.setText("Сизнинг Профилингиз \n" +
                        "Исм: " + dto.getName() + "\n" +
                        "Фамиля: " + dto.getSurname() + "\n" +
                        "Телефон рақам: " + dto.getPhone() + "\n");
                botManager.sendMessage(sendMessage);
            }
        } else if (text.equals("Category") || text.equals("Категория") || text.equals("Сатегорй")) {
            categoryBot.handlerText(user, message);
        } else if (text.equals("Savatcha") || text.equals("Корзина") || text.equals("Саватча")) {

            List<SavatchaDTO> savatchaList = savatchaService.getAllByUserId(Math.toIntExact(user.getId()));
            Long summa = 0L;
            String title = null;

            if (text.equals("Savatcha")) {
                StringBuilder builder = new StringBuilder("Tanlagan maxsulotlaringiz: " + "\n");

                for (SavatchaDTO savatcha : savatchaList) {
                    ProductDTO product = productService.getProductById(savatcha.getProductId());

                    summa += product.getPrice();
                    title = product.getTitle();
                    builder.append("title: " + title + "\n");
                    builder.append("---" + "\n");

                    BuyurtmalarDTO buyurtma = new BuyurtmalarDTO();
                    buyurtma.setProductId(product.getId());
                    buyurtma.setStatus(BuyurtmalarStatus.SAVATCHADA);
                    buyurtma.setUserId(Math.toIntExact(user.getId()));
                }

                builder.append("Umumiy summa: " + summa + "\n" +
                        "\n" +
                        "---------------------------------");
                sendMessage.setText(builder.toString());
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuBuyurtmaTypeSavatchaKeyboardUZB(Math.toIntExact(user.getId())));
                botManager.sendMessage(sendMessage);
            } else if (text.equals("Корзина")) {
                StringBuilder builder = new StringBuilder("Выбранные вами продукты: " + "\n");

                for (SavatchaDTO savatcha : savatchaList) {
                    ProductDTO product = productService.getProductById(savatcha.getProductId());

                    summa += product.getPrice();
                    title = product.getTitle();
                    builder.append("заглавие: " + title + "\n");
                    builder.append("---" + "\n");

                    BuyurtmalarDTO buyurtma = new BuyurtmalarDTO();
                    buyurtma.setProductId(product.getId());
                    buyurtma.setStatus(BuyurtmalarStatus.SAVATCHADA);
                    buyurtma.setUserId(Math.toIntExact(user.getId()));

                    buyurtmalarService.createProfile(buyurtma);
                }

                builder.append("Итого: " + summa + "\n" +
                        "\n" +
                        "---------------------------------");
                sendMessage.setText(builder.toString());
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuBuyurtmaTypeSavatchaKeyboardRUS(Math.toIntExact(user.getId())));
                botManager.sendMessage(sendMessage);
            } else {
                StringBuilder builder = new StringBuilder("Танлаган махсулотларингиз: " + "\n");

                for (SavatchaDTO savatcha : savatchaList) {
                    ProductDTO product = productService.getProductById(savatcha.getProductId());

                    summa += product.getPrice();
                    title = product.getTitle();
                    builder.append("титле: " + title + "\n");
                    builder.append("---" + "\n");

                    BuyurtmalarDTO buyurtma = new BuyurtmalarDTO();
                    buyurtma.setProductId(product.getId());
                    buyurtma.setStatus(BuyurtmalarStatus.SAVATCHADA);
                    buyurtma.setUserId(Math.toIntExact(user.getId()));

                    buyurtmalarService.createProfile(buyurtma);
                }

                builder.append("Умумий сумма: " + summa + "\n" +
                        "\n" +
                        "---------------------------------");
                sendMessage.setText(builder.toString());
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuBuyurtmaTypeSavatchaKeyboardKRILL(Math.toIntExact(user.getId())));
                botManager.sendMessage(sendMessage);
            }
        } else if (text.equals("Buyurtmalar") || text.equals("Заказы") || text.equals("Буюртмалар")) {

            List<BuyurtmalarDTO> buyurtmalarList = buyurtmalarService.getAllByUserId(Math.toIntExact(user.getId()));
            String title = null;
            String status = null;

            if (text.equals("Buyurtmalar")) {
                StringBuilder builder = new StringBuilder("Sizning buyurtmalaringiz: " + "\n");

                for (BuyurtmalarDTO buyurtma : buyurtmalarList) {
                    if (buyurtma.getStatus().equals(BuyurtmalarStatus.DELIVERY)) {
                        ProductDTO product = productService.getProductById(buyurtma.getProductId());

                        title = product.getTitle();
                        status = "Yetkazib berilgan";
                        builder.append("Title: " + title + "\n" +
                                "Status: " + status + "\n" +
                                "-------------------------------" + "\n" + "\n");
                    } else if (buyurtma.getStatus().equals(BuyurtmalarStatus.DELIVERED)) {
                        ProductDTO product = productService.getProductById(buyurtma.getProductId());

                        title = product.getTitle();
                        status = "Yetkazib Berilmoqda";
                        builder.append("Title: " + title + "\n" +
                                "Status: " + status + "\n" +
                                "-------------------------------" + "\n" + "\n");
                    }
                }

                sendMessage.setText(builder.toString());
                botManager.sendMessage(sendMessage);
            } else if (text.equals("Заказы")) {
                StringBuilder builder = new StringBuilder("Ваши заказы: " + "\n");

                for (BuyurtmalarDTO buyurtma : buyurtmalarList) {
                    if (buyurtma.getStatus().equals(BuyurtmalarStatus.DELIVERY)) {
                        ProductDTO product = productService.getProductById(buyurtma.getProductId());

                        title = product.getTitle();
                        status = "Доставленный";
                        builder.append("Заголовок: " + title + "\n" +
                                "Статус: " + status + "\n" +
                                "-------------------------------" + "\n" + "\n");
                    } else if (buyurtma.getStatus().equals(BuyurtmalarStatus.DELIVERED)) {
                        ProductDTO product = productService.getProductById(buyurtma.getProductId());

                        title = product.getTitle();
                        status = "доставки";
                        builder.append("Заголовок: " + title + "\n" +
                                "Статус: " + status + "\n" +
                                "-------------------------------" + "\n" + "\n");
                    }
                }

                sendMessage.setText(builder.toString());
                botManager.sendMessage(sendMessage);
            } else {
                StringBuilder builder = new StringBuilder("Сизнинг Буюртмаларингиз: " + "\n");

                for (BuyurtmalarDTO buyurtma : buyurtmalarList) {
                    if (buyurtma.getStatus().equals(BuyurtmalarStatus.DELIVERY)) {
                        ProductDTO product = productService.getProductById(buyurtma.getProductId());

                        title = product.getTitle();
                        status = "Етказиб берилган";
                        builder.append("Титле: " + title + "\n" +
                                "Статус: " + status + "\n" +
                                "-------------------------------" + "\n" + "\n");
                    } else if (buyurtma.getStatus().equals(BuyurtmalarStatus.DELIVERED)) {
                        ProductDTO product = productService.getProductById(buyurtma.getProductId());

                        title = product.getTitle();
                        status = "Етказиб берилмоқда";
                        builder.append("Титле: " + title + "\n" +
                                "Статус: " + status + "\n" +
                                "-------------------------------" + "\n" + "\n");
                    }
                }

                sendMessage.setText(builder.toString());
                botManager.sendMessage(sendMessage);
            }
        } else {
            muddatliTolovBot.handlerText(user, message);
        }
    }

    public void handlerCallbackQuery(User user, String text, Integer messageId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(user.getId()));

        if (text.equals("uzbek_tili")) {
            profileService.updateLanguageProfile(Math.toIntExact(user.getId()), ProfileBotLanguage.UZBEK.name());

            sendMessage.setText("Nomeringizni yuboring: ");
            sendMessage.setReplyMarkup(InlineButtonUtilManager.menuSingleKeyboardsUzb());
            botManager.sendMessage(sendMessage);
        } else if (text.equals("rus_tili")) {
            profileService.updateLanguageProfile(Math.toIntExact(user.getId()), ProfileBotLanguage.RUSSIAN.name());

            sendMessage.setText("Отправьте свой номер: ");
            sendMessage.setReplyMarkup(InlineButtonUtilManager.menuSingleKeyboardsRus());
            botManager.sendMessage(sendMessage);
        } else if (text.equals("krilcha")) {
            profileService.updateLanguageProfile(Math.toIntExact(user.getId()), ProfileBotLanguage.KRILL.name());

            sendMessage.setText("Номеризни юборинг: ");
            sendMessage.setReplyMarkup(InlineButtonUtilManager.menuSingleKeyboardsKrill());
            botManager.sendMessage(sendMessage);
        } else {
            categoryBot.handlerCallbackQuery(user, text, messageId);
        }
    }

    public void handlerUserContact(User user, Message message) {
        profileService.updatePhoneProfile(Math.toIntExact(user.getId()), message.getContact().getPhoneNumber());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        String language = profileService.getProfileLanguage(Math.toIntExact(user.getId()));

        if (language.equals(ProfileBotLanguage.UZBEK.name())) {
            sendMessage.setText("Asosiy Menu: ");
            sendMessage.setReplyMarkup(InlineButtonUtilManager.menuNextLoginUzb());
            botManager.send(sendMessage);
        } else if (language.equals(ProfileBotLanguage.RUSSIAN.name())) {
            sendMessage.setText("Основной раздел: ");
            sendMessage.setReplyMarkup(InlineButtonUtilManager.menuNextLoginRus());
            botManager.send(sendMessage);
        } else if (language.equals(ProfileBotLanguage.KRILL.name())) {
            sendMessage.setText("Асосий Мену: ");
            sendMessage.setReplyMarkup(InlineButtonUtilManager.menuNextLoginKrill());
            botManager.send(sendMessage);
        }
    }

    public void handlerLocation(User user, Message message) {
        String language = profileService.getProfileLanguage(Math.toIntExact(user.getId()));

        Location location = message.getLocation();
        System.out.println(location.getLatitude() +
                " " + location.getLongitude());

        SendMessage sendMessage = new SendMessage();

        if (language.equals(ProfileBotLanguage.UZBEK.name()))
            sendMessage.setText("Qabul qilindi");
        else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
            sendMessage.setText("Принято");
        else if (language.equals(ProfileBotLanguage.KRILL.name()))
            sendMessage.setText("Қабул қилинди");

        sendMessage.setChatId(String.valueOf(user.getId()));
        botManager.send(sendMessage);

        SendMessage toAdmin = new SendMessage();
        toAdmin.setChatId(String.valueOf(adminId));
        toAdmin.setParseMode("HTML");

        if (language.equals(ProfileBotLanguage.UZBEK.name()))
            toAdmin.setReplyMarkup(InlineButtonUtilManager.menuNextLoginUzb());
        else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
            toAdmin.setReplyMarkup(InlineButtonUtilManager.menuNextLoginRus());
        else if (language.equals(ProfileBotLanguage.KRILL.name()))
            toAdmin.setReplyMarkup(InlineButtonUtilManager.menuNextLoginKrill());

        StringBuilder builder = new StringBuilder();
        builder.append("<a href=\"https://maps.google.com/?q=");
        builder.append(location.getLatitude());
        builder.append(",");
        builder.append(location.getLongitude());
        builder.append("\"");
        builder.append(">User Location</a>");

        toAdmin.setText("User: " + user.getFirstName() + " " + user.getLastName() + "\n" +
                "User Location:\n" + builder.toString());
        botManager.send(toAdmin);

        SendLocation sendLocation = new SendLocation();
        sendLocation.setLatitude(location.getLatitude());
        sendLocation.setLongitude(location.getLongitude());
        sendLocation.setChatId(String.valueOf(adminId));
        if (language.equals(ProfileBotLanguage.UZBEK.name())) {
            sendLocation.setReplyMarkup(InlineButtonUtilProductCategory.menuCheckBuyurtmaKeyboardUZB(Math.toIntExact(user.getId())));
        } else if (language.equals(ProfileBotLanguage.RUSSIAN.name())) {
            sendLocation.setReplyMarkup(InlineButtonUtilProductCategory.menuCheckBuyurtmaKeyboardRUS(Math.toIntExact(user.getId())));
        } else if (language.equals(ProfileBotLanguage.KRILL.name())) {
            sendLocation.setReplyMarkup(InlineButtonUtilProductCategory.menuCheckBuyurtmaKeyboardKRILL(Math.toIntExact(user.getId())));
        }
        botManager.send(sendLocation);
    }
}
