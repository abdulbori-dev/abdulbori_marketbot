package com.company.mainBot.user;

import com.company.dto.BuyurtmalarDTO;
import com.company.dto.ProductDTO;
import com.company.dto.SavatchaDTO;
import com.company.enums.*;
import com.company.mainBot.admin.AdminBot;
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
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MainBot {
    @Autowired
    private BotManager botManager;
    @Autowired
    private AdminBot adminBot;
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

    public Map<Long, RegistrationUserText> userStatusMap = new HashMap<>();

    public void handlerText(User user, Message message) {
        String text = message.getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));

        if (text.equals("/start")) {
            if (profileService.checkRole(ProfileRole.ADMIN.name(), user.getId())) {
                adminBot.handlerText(user, message);
                return;
            }

            sendMessage.setReplyMarkup(InlineButtonUtilManager.menuSingleKeyboard());
            sendMessage.setText("Tilni Tanlang: ");
            botManager.sendMessage(sendMessage);


            if (profileService.getById(user.getId()) && !profileService.checkPhoneNumber(user.getId())) {
                ProfileDTO dto = new ProfileDTO();
                dto.setStartedDate(LocalDateTime.now());
                dto.setId(user.getId());
                dto.setRole(ProfileRole.USER);
                dto.setStatus(ProfileStatus.ACTIVE);
                dto.setProfileBotLanguage(ProfileBotLanguage.UZBEK);

                profileService.createProfile(dto);
            }
        } else if (text.equals("\uD83D\uDC64 Profile") || text.equals("\uD83D\uDC64 Профиль") || text.equals("\uD83D\uDC64 Профиле")) {
            ProfileDTO dto = profileService.getByProfileId(user.getId());
            if (text.equals("\uD83D\uDC64 Profile")) {
                sendMessage.setText("Sizning Profilingiz \n" +
                        "Ism: " + dto.getName() + "\n" +
                        "Familya: " + dto.getSurname() + "\n" +
                        "Telefon raqam: " + dto.getPhone() + "\n");
                botManager.sendMessage(sendMessage);
            } else if (text.equals("\uD83D\uDC64 Профиль")) {
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
        } else if (text.equals("\uD83D\uDD0D Category") || text.equals("\uD83D\uDD0D Категория") || text.equals("\uD83D\uDD0D Сатегорй")) {
            categoryBot.handlerText(user, message);
        } else if (text.equals("\uD83D\uDED2 Savatcha") || text.equals("\uD83D\uDED2 Корзина") || text.equals("\uD83D\uDED2 Саватча")) {

            List<SavatchaDTO> savatchaList = savatchaService.getAllByUserId(user.getId());
            Long summa = 0L;
            String title = null;

            if (text.equals("\uD83D\uDED2 Savatcha")) {
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
                    buyurtma.setUserId(user.getId());
                }

                builder.append("Umumiy summa: " + summa + "\n" +
                        "\n" +
                        "---------------------------------");
                sendMessage.setText(builder.toString());
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuBuyurtmaTypeSavatchaKeyboardUZB(user.getId()));
                botManager.sendMessage(sendMessage);
            } else if (text.equals("\uD83D\uDED2 Корзина")) {
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
                    buyurtma.setUserId(user.getId());

                    buyurtmalarService.createProfile(buyurtma);
                }

                builder.append("Итого: " + summa + "\n" +
                        "\n" +
                        "---------------------------------");
                sendMessage.setText(builder.toString());
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuBuyurtmaTypeSavatchaKeyboardRUS(user.getId()));
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
                    buyurtma.setUserId(user.getId());

                    buyurtmalarService.createProfile(buyurtma);
                }

                builder.append("Умумий сумма: " + summa + "\n" +
                        "\n" +
                        "---------------------------------");
                sendMessage.setText(builder.toString());
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuBuyurtmaTypeSavatchaKeyboardKRILL(user.getId()));
                botManager.sendMessage(sendMessage);
            }
        } else if (text.equals("\uD83D\uDECD Buyurtmalar") || text.equals("\uD83D\uDECD Заказы") || text.equals("\uD83D\uDECD Буюртмалар")) {

            List<BuyurtmalarDTO> buyurtmalarList = buyurtmalarService.getAllByUserId(user.getId());
            String title = null;
            String status = null;

            if (text.equals("\uD83D\uDECD Buyurtmalar")) {
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
            } else if (text.equals("\uD83D\uDECD Заказы")) {
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
        } else if (userStatusMap.containsKey(user.getId())) {
            if (userStatusMap.get(user.getId()).equals(RegistrationUserText.NAME)) {
                String language = profileService.getProfileLanguage(user.getId());
                profileService.updateNameProfile(user.getId(), text);

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("Familyangizni kiriting: ");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("Введите вашу фамилию: ");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("Фамилянгизни киритинг: ");

                botManager.sendMessage(sendMessage);

                userStatusMap.remove(user.getId());
                userStatusMap.put(user.getId(), RegistrationUserText.SURNAME);
            } else if (userStatusMap.get(user.getId()).equals(RegistrationUserText.SURNAME)) {
                String language = profileService.getProfileLanguage(user.getId());
                profileService.updateSurnameProfile(user.getId(), text);

                if (language.equals(ProfileBotLanguage.UZBEK.name())) {
                    sendMessage.setText("Asosiy Menu: ");
                    sendMessage.setReplyMarkup(InlineButtonUtilManager.menuNextLogin(ProfileBotLanguage.UZBEK));
                    botManager.send(sendMessage);
                } else if (language.equals(ProfileBotLanguage.RUSSIAN.name())) {
                    sendMessage.setText("Основной раздел: ");
                    sendMessage.setReplyMarkup(InlineButtonUtilManager.menuNextLogin(ProfileBotLanguage.RUSSIAN));
                    botManager.send(sendMessage);
                } else if (language.equals(ProfileBotLanguage.KRILL.name())) {
                    sendMessage.setText("Асосий Мену: ");
                    sendMessage.setReplyMarkup(InlineButtonUtilManager.menuNextLogin(ProfileBotLanguage.KRILL));
                    botManager.send(sendMessage);
                }

                userStatusMap.remove(user.getId());
            }
        } else {
            muddatliTolovBot.handlerText(user, message);
        }
    }

    public void handlerCallbackQuery(User user, String text, Integer messageId, CallbackQuery callback) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(user.getId()));

        if (text.equals("uzbek_tili")) {
            profileService.updateLanguageProfile(user.getId(), ProfileBotLanguage.UZBEK.name());

            sendMessage.setText("Nomeringizni yuboring: ");
            sendMessage.setReplyMarkup(InlineButtonUtilManager.menuSingleKeyboards(ProfileBotLanguage.UZBEK));
            botManager.sendMessage(sendMessage);
        } else if (text.equals("rus_tili")) {
            profileService.updateLanguageProfile(user.getId(), ProfileBotLanguage.RUSSIAN.name());

            sendMessage.setText("Отправьте свой номер: ");
            sendMessage.setReplyMarkup(InlineButtonUtilManager.menuSingleKeyboards(ProfileBotLanguage.RUSSIAN));
            botManager.sendMessage(sendMessage);
        } else if (text.equals("krilcha")) {
            profileService.updateLanguageProfile(user.getId(), ProfileBotLanguage.KRILL.name());

            sendMessage.setText("Номеризни юборинг: ");
            sendMessage.setReplyMarkup(InlineButtonUtilManager.menuSingleKeyboards(ProfileBotLanguage.KRILL));
            botManager.sendMessage(sendMessage);
        } else {
            categoryBot.handlerCallbackQuery(user, text, messageId, callback);
        }
    }

    public void handlerUserContact(User user, Message message) {
        profileService.updatePhoneProfile(user.getId(), message.getContact().getPhoneNumber());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        String language = profileService.getProfileLanguage(user.getId());

        if (language.equals(ProfileBotLanguage.UZBEK.name())) {
            sendMessage.setText("Ismingizni kiriting: ");
            botManager.send(sendMessage);

            userStatusMap.put(user.getId(), RegistrationUserText.NAME);
        } else if (language.equals(ProfileBotLanguage.RUSSIAN.name())) {
            sendMessage.setText("Введите ваше имя: ");
            botManager.send(sendMessage);

            userStatusMap.put(user.getId(), RegistrationUserText.NAME);
        } else if (language.equals(ProfileBotLanguage.KRILL.name())) {
            sendMessage.setText("Исмингизни киритинг: ");
            botManager.send(sendMessage);

            userStatusMap.put(user.getId(), RegistrationUserText.NAME);
        }
    }

    public void handlerLocation(User user, Message message) {
        String language = profileService.getProfileLanguage(user.getId());

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

        for (ProfileDTO dto : profileService.getByProfileRole(ProfileRole.ADMIN.name())) {
            SendLocation sendLocation = new SendLocation();
            sendLocation.setLatitude(location.getLatitude());
            sendLocation.setLongitude(location.getLongitude());
            sendLocation.setChatId(String.valueOf(dto.getId()));
            if (language.equals(ProfileBotLanguage.UZBEK.name())) {
                sendLocation.setReplyMarkup(InlineButtonUtilProductCategory.menuCheckBuyurtmaKeyboardUZB(user.getId()));
            } else if (language.equals(ProfileBotLanguage.RUSSIAN.name())) {
                sendLocation.setReplyMarkup(InlineButtonUtilProductCategory.menuCheckBuyurtmaKeyboardRUS(user.getId()));
            } else if (language.equals(ProfileBotLanguage.KRILL.name())) {
                sendLocation.setReplyMarkup(InlineButtonUtilProductCategory.menuCheckBuyurtmaKeyboardKRILL(user.getId()));
            }
            botManager.send(sendLocation);

            SendMessage toAdmin = new SendMessage();
            toAdmin.setChatId(String.valueOf(dto.getId()));
            toAdmin.setParseMode("HTML");

            if (language.equals(ProfileBotLanguage.UZBEK.name())) {
                toAdmin.setReplyMarkup(InlineButtonUtilManager.menuNextLogin(ProfileBotLanguage.UZBEK));
            }
            else if (language.equals(ProfileBotLanguage.RUSSIAN.name())) {
                toAdmin.setReplyMarkup(InlineButtonUtilManager.menuNextLogin(ProfileBotLanguage.RUSSIAN));
            }
            else if (language.equals(ProfileBotLanguage.KRILL.name())) {
                toAdmin.setReplyMarkup(InlineButtonUtilManager.menuNextLogin(ProfileBotLanguage.KRILL));
            }

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
        }
    }
}
