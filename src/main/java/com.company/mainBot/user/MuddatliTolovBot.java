package com.company.mainBot.user;

import com.company.dto.MuddatliTolovDTO;
import com.company.dto.ProductDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.UserDetailDTO;
import com.company.enums.*;
import com.company.mainBot.admin.AdminBot;
import com.company.manager.BotManager;
import com.company.service.MuddatliTolovService;
import com.company.service.ProductService;
import com.company.service.ProfileService;
import com.company.service.UserDetailService;
import com.company.util.InlineButtonUtilManager;
import com.company.util.InlineButtonUtilMuddatliTolov;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class MuddatliTolovBot {
    @Autowired
    private BotManager botManager;
    @Autowired
    private AdminBot adminBot;
    @Autowired
    private MuddatliTolovService muddatliTolovService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private ProfileService profileService;

    public Map<Long, UserDetailEnum> userStatusMap = new HashMap<>();

    public void handlerText(User user, Message message) {
        String text = message.getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));

        String language = profileService.getProfileLanguage(user.getId());

        if (userStatusMap.containsKey(user.getId())) {
            if (userStatusMap.get(user.getId()).equals(UserDetailEnum.ORGANIZATION)) {

                userDetailService.updateOrganizationProfile(text, user.getId());

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("Lavozimingiz?");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("Твоя позиция?");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("Лавозимингиз?");

                botManager.sendMessage(sendMessage);

                userStatusMap.remove(user.getId());
                userStatusMap.put(user.getId(), UserDetailEnum.POSITION);
            } else if (userStatusMap.get(user.getId()).equals(UserDetailEnum.POSITION)) {

                userDetailService.updatePositionProfile(text, user.getId());

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("Stajingiz?");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("Ваша стажировка?");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("Стажингиз?");
                botManager.sendMessage(sendMessage);

                userStatusMap.remove(user.getId());
                userStatusMap.put(user.getId(), UserDetailEnum.INTERNSHIP);
            } else if (userStatusMap.get(user.getId()).equals(UserDetailEnum.INTERNSHIP)) {

                userDetailService.updateInternshipProfile(text, user.getId());

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("Ish haqi miqdori?");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("Размер зарплаты");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("Иш ҳақи миқдори?");
                botManager.sendMessage(sendMessage);

                userStatusMap.remove(user.getId());
                userStatusMap.put(user.getId(), UserDetailEnum.SALARY);
            } else if (userStatusMap.get(user.getId()).equals(UserDetailEnum.SALARY)) {

                userDetailService.updateSalaryProfile(Integer.valueOf(text), user.getId());

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("Paspostingizni scanneri yoki rasmini tashlang");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("Скиньте сканер или фото своего паспорта");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("Паспостингизни ссаннери ёки расмини ташланг");
                botManager.sendMessage(sendMessage);

                userStatusMap.remove(user.getId());
                userStatusMap.put(user.getId(), UserDetailEnum.PASSPORT_PHOTO_SCANNER);
            } else if (userStatusMap.get(user.getId()).equals(UserDetailEnum.PHONE_NUMBER)) {

                userDetailService.updatePhoneNumberProfile(text, user.getId());

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("Qo'shimcha Tel raqam kiriting");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("Введите дополнительный номер телефона");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("Қўшимча Тел рақам киритинг");
                botManager.sendMessage(sendMessage);

                userStatusMap.remove(user.getId());
                userStatusMap.put(user.getId(), UserDetailEnum.ADD_NUMBER);
            } else if (userStatusMap.get(user.getId()).equals(UserDetailEnum.ADD_NUMBER)) {
                userDetailService.updateAddNumberProfile(text, user.getId());

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("Plastik karta raqami kiriting");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("Введите номер пластиковой карты");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("Пластик карта рақами киритинг");
                botManager.sendMessage(sendMessage);

                userStatusMap.remove(user.getId());
                userStatusMap.put(user.getId(), UserDetailEnum.CARD);
            } else if (userStatusMap.get(user.getId()).equals(UserDetailEnum.CARD)) {
                userDetailService.updateCardProfile(text, user.getId());

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("Amal qilish muddatini kiriting (01/26)");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("Введите срок действия (01/26)");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("Амал қилиш муддатини киритинг (01/26)");
                botManager.sendMessage(sendMessage);

                userStatusMap.remove(user.getId());
                userStatusMap.put(user.getId(), UserDetailEnum.VALIDITY_PERIOD);
            } else if (userStatusMap.get(user.getId()).equals(UserDetailEnum.VALIDITY_PERIOD)) {
                userDetailService.updateCalidityPeriod(text, user.getId());

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("Ushbu kartaga biriktirilgan telefon nomerni kiriting");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("Введите номер телефона, привязанный к этой карте");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("Ушбу картага бириктирилган телефон номерни киритинг");
                botManager.sendMessage(sendMessage);

                userStatusMap.remove(user.getId());
                userStatusMap.put(user.getId(), UserDetailEnum.CARD_PHONE_NUMBER);
            } else if (userStatusMap.get(user.getId()).equals(UserDetailEnum.CARD_PHONE_NUMBER)) {
                userDetailService.updateCardPhoneNumber(text, user.getId());

                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    sendMessage.setText("Sizning ma'lumotlaringiz Adminga yuborildi. Tez orada siz bilan bog'lanishadi");
                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    sendMessage.setText("Ваша информация отправлена администратору. С вами свяжутся в ближайшее время");
                else if (language.equals(ProfileBotLanguage.KRILL.name()))
                    sendMessage.setText("Сизнинг маълумотларингиз Админга юборилди. Тез орада сиз билан боғланишади");
                botManager.sendMessage(sendMessage);

                userStatusMap.remove(user.getId());

                for (ProfileDTO dto : profileService.getByProfileRole(ProfileRole.ADMIN.name())) {
                    SendMessage toAdmin = new SendMessage();
                    toAdmin.setChatId(String.valueOf(dto.getId()));
                    toAdmin.setParseMode("HTML");
                    toAdmin.setText(user.getFirstName() + "\n Muddatli to'lov amalga oshirdi. Sizga qanday ma'lumotlar kerak: ");

                    if (dto.getProfileBotLanguage().name().equals(ProfileBotLanguage.UZBEK.name())) {
                        toAdmin.setReplyMarkup(InlineButtonUtilManager.menuMuddatliTolovNext(user.getId(), ProfileBotLanguage.UZBEK));
                    } else if (dto.getProfileBotLanguage().name().equals(ProfileBotLanguage.RUSSIAN.name())) {
                        toAdmin.setReplyMarkup(InlineButtonUtilManager.menuMuddatliTolovNext(user.getId(), ProfileBotLanguage.RUSSIAN));
                    } else if (dto.getProfileBotLanguage().name().equals(ProfileBotLanguage.KRILL.name())) {
                        toAdmin.setReplyMarkup(InlineButtonUtilManager.menuMuddatliTolovNext(user.getId(), ProfileBotLanguage.KRILL));
                    }

                    botManager.send(toAdmin);
                }
            }
        } else {
            adminBot.handlerText(user, message);
        }
    }

    public void handlerCallbackQuery(User user, String text, Integer messageId) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(user.getId()));

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(user.getId()));

        if (text.startsWith("muddatli_tolov_uz_") || text.startsWith("muddatli_tolov_rus_") || text.startsWith("muddatli_tolov_krill_")) {

            if (text.startsWith("muddatli_tolov_uz_")) {
                String numberOnly = text.replaceAll("[^0-9]", "");

                sendMessage.setText("Tariflardan birini tanlang: ");
                sendMessage.setReplyMarkup(InlineButtonUtilMuddatliTolov.menuSingleKeyboard(Integer.valueOf(numberOnly), ProfileBotLanguage.UZBEK));
                botManager.sendMessage(sendMessage);

                MuddatliTolovDTO muddatliTolov = new MuddatliTolovDTO();
                muddatliTolov.setProductId(Integer.valueOf(numberOnly));
                muddatliTolov.setStatus(MuddatliTolovStatus.TASDIQLANMAGAN);
                muddatliTolov.setUserId(user.getId());

                muddatliTolovService.createProfile(muddatliTolov);
            } else if (text.startsWith("muddatli_tolov_rus_")) {
                String numberOnly = text.replaceAll("[^0-9]", "");

                sendMessage.setText("Выберите один из тарифов: ");
                sendMessage.setReplyMarkup(InlineButtonUtilMuddatliTolov.menuSingleKeyboard(Integer.valueOf(numberOnly), ProfileBotLanguage.RUSSIAN));
                botManager.sendMessage(sendMessage);

                MuddatliTolovDTO muddatliTolov = new MuddatliTolovDTO();
                muddatliTolov.setProductId(Integer.valueOf(numberOnly));
                muddatliTolov.setStatus(MuddatliTolovStatus.TASDIQLANMAGAN);
                muddatliTolov.setUserId(user.getId());

                muddatliTolovService.createProfile(muddatliTolov);
            } else if (text.startsWith("muddatli_tolov_krill_")) {
                String numberOnly = text.replaceAll("[^0-9]", "");

                sendMessage.setText("Тарифлардан бирини танланг: ");
                sendMessage.setReplyMarkup(InlineButtonUtilMuddatliTolov.menuSingleKeyboard(Integer.valueOf(numberOnly), ProfileBotLanguage.KRILL));
                botManager.sendMessage(sendMessage);

                MuddatliTolovDTO muddatliTolov = new MuddatliTolovDTO();
                muddatliTolov.setProductId(Integer.valueOf(numberOnly));
                muddatliTolov.setStatus(MuddatliTolovStatus.TASDIQLANMAGAN);
                muddatliTolov.setUserId(user.getId());

                muddatliTolovService.createProfile(muddatliTolov);
            }
        } else if (text.startsWith("uch_oylik_uz_") || text.startsWith("uch_oylik_rus_") || text.startsWith("uch_oylik_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            if (text.startsWith("uch_oylik_uz_")) {
                muddatliTolovService.updateTypeByUserIdAndProductId(MuddatliTolovType.THREE_MONTH.name(), user.getId(), Integer.valueOf(numberOnly));

                ProductDTO productDTO = productService.getProductById(Integer.valueOf(numberOnly));

                double muddatliTolovSumma = productDTO.getPrice() * 1.2 / 3;
                Integer summa = (int) muddatliTolovSumma;

                muddatliTolovService.updatePriceByUserIdAndProductId(summa, user.getId(), Integer.valueOf(numberOnly));
                sendMessage.setText("Jami summa: " + summa + "\n" +
                        "\n" +
                        "Siz bu Buyurtmani tasdiqlaysizmi! ");
                sendMessage.setReplyMarkup(InlineButtonUtilMuddatliTolov.menuMuddatliTolovniTasdiqlashKeyboard(Integer.valueOf(numberOnly), ProfileBotLanguage.UZBEK));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("uch_oylik_rus_")) {
                muddatliTolovService.updateTypeByUserIdAndProductId(MuddatliTolovType.THREE_MONTH.name(), user.getId(), Integer.valueOf(numberOnly));

                ProductDTO productDTO = productService.getProductById(Integer.valueOf(numberOnly));

                double muddatliTolovSumma = productDTO.getPrice() * 1.2 / 3;
                Integer summa = (int) muddatliTolovSumma;

                muddatliTolovService.updatePriceByUserIdAndProductId(summa, user.getId(), Integer.valueOf(numberOnly));

                sendMessage.setText("Общая сумма: " + summa + "\n" +
                        "\n" + "Вы подтверждаете этот Заказ! ");
                sendMessage.setReplyMarkup(InlineButtonUtilMuddatliTolov.menuMuddatliTolovniTasdiqlashKeyboard(Integer.valueOf(numberOnly), ProfileBotLanguage.RUSSIAN));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("uch_oylik_krill_")) {
                muddatliTolovService.updateTypeByUserIdAndProductId(MuddatliTolovType.THREE_MONTH.name(), user.getId(), Integer.valueOf(numberOnly));

                ProductDTO productDTO = productService.getProductById(Integer.valueOf(numberOnly));

                double muddatliTolovSumma = productDTO.getPrice() * 1.2 / 3;
                Integer summa = (int) muddatliTolovSumma;

                muddatliTolovService.updatePriceByUserIdAndProductId(summa, user.getId(), Integer.valueOf(numberOnly));

                sendMessage.setText("Жами сумма: " + summa + "\n" +
                        "\n" + "Сиз бу Буюртмани тасдиқлайсизми! ");
                sendMessage.setReplyMarkup(InlineButtonUtilMuddatliTolov.menuMuddatliTolovniTasdiqlashKeyboard(Integer.valueOf(numberOnly), ProfileBotLanguage.KRILL));
                botManager.sendMessage(sendMessage);
            }

        } else if (text.startsWith("olti_oylik_uz_") || text.startsWith("olti_oylik_rus_") || text.startsWith("olti_oylik_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            if (text.startsWith("olti_oylik_uz_")) {
                muddatliTolovService.updateTypeByUserIdAndProductId(MuddatliTolovType.SIX_MONTH.name(), user.getId(), Integer.valueOf(numberOnly));

                ProductDTO productDTO = productService.getProductById(Integer.valueOf(numberOnly));

                double muddatliTolovSumma = productDTO.getPrice() * 1.3 / 6;
                Integer summa = (int) muddatliTolovSumma;

                muddatliTolovService.updatePriceByUserIdAndProductId(summa, user.getId(), Integer.valueOf(numberOnly));
                sendMessage.setText("Jami summa: " + summa + "\n" +
                        "\n" + "Siz bu Buyurtmani tasdiqlaysizmi! ");
                sendMessage.setReplyMarkup(InlineButtonUtilMuddatliTolov.menuMuddatliTolovniTasdiqlashKeyboard(Integer.valueOf(numberOnly), ProfileBotLanguage.UZBEK));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("olti_oylik_rus_")) {
                muddatliTolovService.updateTypeByUserIdAndProductId(MuddatliTolovType.SIX_MONTH.name(), user.getId(), Integer.valueOf(numberOnly));

                ProductDTO productDTO = productService.getProductById(Integer.valueOf(numberOnly));

                double muddatliTolovSumma = productDTO.getPrice() * 1.3 / 6;
                Integer summa = (int) muddatliTolovSumma;

                muddatliTolovService.updatePriceByUserIdAndProductId(summa, user.getId(), Integer.valueOf(numberOnly));
                sendMessage.setText("Общая сумма: " + summa + "\n" +
                        "\n" + "Вы подтверждаете этот заказ! ");
                sendMessage.setReplyMarkup(InlineButtonUtilMuddatliTolov.menuMuddatliTolovniTasdiqlashKeyboard(Integer.valueOf(numberOnly), ProfileBotLanguage.RUSSIAN));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("olti_oylik_krill_")) {
                muddatliTolovService.updateTypeByUserIdAndProductId(MuddatliTolovType.SIX_MONTH.name(), user.getId(), Integer.valueOf(numberOnly));

                ProductDTO productDTO = productService.getProductById(Integer.valueOf(numberOnly));

                double muddatliTolovSumma = productDTO.getPrice() * 1.3 / 6;
                Integer summa = (int) muddatliTolovSumma;

                muddatliTolovService.updatePriceByUserIdAndProductId(summa, user.getId(), Integer.valueOf(numberOnly));
                sendMessage.setText("Жами сумма: " + summa + "\n" +
                        "\n" + "Сиз бу Буюртмани тасдиқлайсизми! ");
                sendMessage.setReplyMarkup(InlineButtonUtilMuddatliTolov.menuMuddatliTolovniTasdiqlashKeyboard(Integer.valueOf(numberOnly), ProfileBotLanguage.KRILL));
                botManager.sendMessage(sendMessage);
            }
        } else if (text.startsWith("toqqiz_oylik_uz_") || text.startsWith("toqqiz_oylik_rus_") || text.startsWith("toqqiz_oylik_krill_")) {

            if (text.startsWith("toqqiz_oylik_uz_")) {
                String numberOnly = text.replaceAll("[^0-9]", "");

                muddatliTolovService.updateTypeByUserIdAndProductId(MuddatliTolovType.NINE_MONTH.name(), user.getId(), Integer.valueOf(numberOnly));

                ProductDTO productDTO = productService.getProductById(Integer.valueOf(numberOnly));

                double muddatliTolovSumma = productDTO.getPrice() * 1.4 / 9;
                Integer summa = (int) muddatliTolovSumma;

                muddatliTolovService.updatePriceByUserIdAndProductId(summa, user.getId(), Integer.valueOf(numberOnly));
                sendMessage.setText("Jami summa: " + summa + "\n" +
                        "\n" + "Siz bu Buyurtmani tasdiqlaysizmi! ");
                sendMessage.setReplyMarkup(InlineButtonUtilMuddatliTolov.menuMuddatliTolovniTasdiqlashKeyboard(Integer.valueOf(numberOnly), ProfileBotLanguage.UZBEK));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("toqqiz_oylik_rus_")) {
                String numberOnly = text.replaceAll("[^0-9]", "");

                muddatliTolovService.updateTypeByUserIdAndProductId(MuddatliTolovType.NINE_MONTH.name(), user.getId(), Integer.valueOf(numberOnly));

                ProductDTO productDTO = productService.getProductById(Integer.valueOf(numberOnly));

                double muddatliTolovSumma = productDTO.getPrice() * 1.4 / 9;
                Integer summa = (int) muddatliTolovSumma;

                muddatliTolovService.updatePriceByUserIdAndProductId(summa, user.getId(), Integer.valueOf(numberOnly));
                sendMessage.setText("Общая сумма: " + summa + "\n" +
                        "\n" + "Вы подтверждаете этот заказ! ");
                sendMessage.setReplyMarkup(InlineButtonUtilMuddatliTolov.menuMuddatliTolovniTasdiqlashKeyboard(Integer.valueOf(numberOnly), ProfileBotLanguage.RUSSIAN));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("toqqiz_oylik_krill_")) {
                String numberOnly = text.replaceAll("[^0-9]", "");

                muddatliTolovService.updateTypeByUserIdAndProductId(MuddatliTolovType.NINE_MONTH.name(), user.getId(), Integer.valueOf(numberOnly));

                ProductDTO productDTO = productService.getProductById(Integer.valueOf(numberOnly));

                double muddatliTolovSumma = productDTO.getPrice() * 1.4 / 9;
                Integer summa = (int) muddatliTolovSumma;

                muddatliTolovService.updatePriceByUserIdAndProductId(summa, user.getId(), Integer.valueOf(numberOnly));
                sendMessage.setText("Жами сумма: " + summa + "\n" +
                        "\n" + "Сиз бу Буюртмани тасдиқлайсизми! ");
                sendMessage.setReplyMarkup(InlineButtonUtilMuddatliTolov.menuMuddatliTolovniTasdiqlashKeyboard(Integer.valueOf(numberOnly), ProfileBotLanguage.KRILL));
                botManager.sendMessage(sendMessage);
            }
        } else if (text.startsWith("on_ikki_oylik_uz_") || text.startsWith("on_ikki_oylik_rus_") || text.startsWith("on_ikki_oylik_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            if (text.startsWith("on_ikki_oylik_uz_")) {
                muddatliTolovService.updateTypeByUserIdAndProductId(MuddatliTolovType.TWELVE_MONTH.name(), user.getId(), Integer.valueOf(numberOnly));

                ProductDTO productDTO = productService.getProductById(Integer.valueOf(numberOnly));

                double muddatliTolovSumma = productDTO.getPrice() * 1.5 / 12;
                Integer summa = (int) muddatliTolovSumma;

                muddatliTolovService.updatePriceByUserIdAndProductId(summa, user.getId(), Integer.valueOf(numberOnly));
                sendMessage.setText("Jami summa: " + summa + "\n" +
                        "\n" + "Siz bu Buyurtmani tasdiqlaysizmi! ");
                sendMessage.setReplyMarkup(InlineButtonUtilMuddatliTolov.menuMuddatliTolovniTasdiqlashKeyboard(Integer.valueOf(numberOnly), ProfileBotLanguage.UZBEK));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("on_ikki_oylik_rus_")) {
                muddatliTolovService.updateTypeByUserIdAndProductId(MuddatliTolovType.TWELVE_MONTH.name(), user.getId(), Integer.valueOf(numberOnly));

                ProductDTO productDTO = productService.getProductById(Integer.valueOf(numberOnly));

                double muddatliTolovSumma = productDTO.getPrice() * 1.5 / 12;
                Integer summa = (int) muddatliTolovSumma;

                muddatliTolovService.updatePriceByUserIdAndProductId(summa, user.getId(), Integer.valueOf(numberOnly));
                sendMessage.setText("Общая сумма: " + summa + "\n" +
                        "\n" + "Вы подтверждаете этот заказ! ");
                sendMessage.setReplyMarkup(InlineButtonUtilMuddatliTolov.menuMuddatliTolovniTasdiqlashKeyboard(Integer.valueOf(numberOnly), ProfileBotLanguage.RUSSIAN));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("on_ikki_oylik_krill_")) {
                muddatliTolovService.updateTypeByUserIdAndProductId(MuddatliTolovType.TWELVE_MONTH.name(), user.getId(), Integer.valueOf(numberOnly));

                ProductDTO productDTO = productService.getProductById(Integer.valueOf(numberOnly));

                double muddatliTolovSumma = productDTO.getPrice() * 1.5 / 12;
                Integer summa = (int) muddatliTolovSumma;

                muddatliTolovService.updatePriceByUserIdAndProductId(summa, user.getId(), Integer.valueOf(numberOnly));
                sendMessage.setText("Жами сумма: " + summa + "\n" +
                        "\n" + "Сиз бу Буюртмани тасдиқлайсизми! ");
                sendMessage.setReplyMarkup(InlineButtonUtilMuddatliTolov.menuMuddatliTolovniTasdiqlashKeyboard(Integer.valueOf(numberOnly), ProfileBotLanguage.KRILL));
                botManager.sendMessage(sendMessage);
            }
        } else if (text.startsWith("tasdiqlash_uz_") || text.startsWith("tasdiqlash_rus_") || text.startsWith("tasdiqlash_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            UserDetailDTO userDetail = new UserDetailDTO();
            userDetail.setUserId(user.getId());
            if (userDetailService.checkProfileById(user.getId())) {
                userDetailService.createProfile(userDetail);
            }

            sendMessage.setText("Qaysi takshillotda ishlaysiz? ");
            botManager.sendMessage(sendMessage);

            userStatusMap.put(user.getId(), UserDetailEnum.ORGANIZATION);
        } else if (text.startsWith("bekor_qilish_uz_") || text.startsWith("bekor_qilish_rus_") || text.startsWith("bekor_qilish_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");
            muddatliTolovService.deleteMuddatliTolov(user.getId(), Integer.valueOf(numberOnly));

            sendMessage.setText("Muvaffaqiyatli Bekor qilindi!");
            botManager.sendMessage(sendMessage);
        } else {
            adminBot.handlerCallbackQuery(user, text, messageId);
        }
    }

    public void handlerPhoto(User user, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));

        String image = message.getPhoto().get(message.getPhoto().size() - 1).getFileId();

        if (userStatusMap.containsKey(user.getId())) {
            if (userStatusMap.get(user.getId()).equals(UserDetailEnum.PASSPORT_PHOTO_SCANNER)) {
                userDetailService.updatePassportPhotoScannerProfile(image, user.getId());

                sendMessage.setText("Paspostdagi yashash joyini ham scanner yoki rasmini tashlang");
                botManager.sendMessage(sendMessage);

                userStatusMap.remove(user.getId());
                userStatusMap.put(user.getId(), UserDetailEnum.PASSPORT_PHOTO_ADDRESS);
            } else if (userStatusMap.get(user.getId()).equals(UserDetailEnum.PASSPORT_PHOTO_ADDRESS)) {
                userDetailService.updatePassportPhotoAddressProfile(image, user.getId());

                sendMessage.setText("Pasportni qo'lingizga ushlab o'zingiz bilan rasmga olib tashlang");
                botManager.sendMessage(sendMessage);

                userStatusMap.remove(user.getId());
                userStatusMap.put(user.getId(), UserDetailEnum.PASSPORT_AND_YOU);
            } else if (userStatusMap.get(user.getId()).equals(UserDetailEnum.PASSPORT_AND_YOU)) {
                userDetailService.updatePassportAndYouProfile(image, user.getId());

                sendMessage.setText("Telefon raqamingizni kiriting");
                botManager.sendMessage(sendMessage);

                userStatusMap.remove(user.getId());
                userStatusMap.put(user.getId(), UserDetailEnum.PHONE_NUMBER);
            }
        } else {
            adminBot.handlerPhoto(user, message);
        }
    }
}
