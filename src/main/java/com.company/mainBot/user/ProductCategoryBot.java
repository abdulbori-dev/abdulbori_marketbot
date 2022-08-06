package com.company.mainBot.user;

import com.company.dto.BuyurtmalarDTO;
import com.company.dto.ProductDTO;
import com.company.dto.SavatchaDTO;
import com.company.enums.BuyurtmalarStatus;
import com.company.enums.ProfileBotLanguage;
import com.company.manager.BotManager;
import com.company.service.*;
import com.company.util.InlineButtonUtilManager;
import com.company.util.InlineButtonUtilProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

@Component
public class ProductCategoryBot {
    @Autowired
    private BotManager botManager;
    @Autowired
    private ProductService productService;
    @Autowired
    private BuyurtmalarService buyurtmalarService;
    @Autowired
    private SavatchaService savatchaService;
    @Autowired
    private MuddatliTolovBot muddatliTolovBot;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProductCategoryService productCategoryService;

    public void handlerCallbackQuery(User user, String text, Integer messageId, CallbackQuery callback) {

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(user.getId()));

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(user.getId()));
        String language = profileService.getProfileLanguage(user.getId());

        if (!productCategoryService.checkCategory(text)) {
            String numberOnly = text.replaceAll("[^0-9]", "");
            List<ProductDTO> productList = productService.getAllById(text);

            // Bu yerga DTO ga kochirganman kelgan ma'lumotlarni har biri alohida for yordamida jonatiladi
            for (ProductDTO product : productList) {
                // product rasmi project turgan filega yuklanmaydi Telegram yuklagan bazadan olinadi ya'ni bu project xotirasi
                // kam bolishiga yordam beradi. Input file yordamida rasm olinadi.
                InputFile inputFile = new InputFile();
                inputFile.setMedia(product.getPhotoId());

                // send Photo ga rasm yuklanadi
                sendPhoto.setPhoto(inputFile);

                // malumotlar text sifatida berib yuboriladi
                sendPhoto.setCaption("Title: " + product.getTitle() + "\n" +
                        "Description: " + product.getDescription() + "\n" +
                        "Price: " + product.getPrice());

                // if da murojat qilgan user tiliga qarab sozlab olamiz.
                if (language.equals(ProfileBotLanguage.UZBEK.name()))
                    // Bu yerga tagiga Uzbekcha keyboard tushirish uchun ishlatiladi.
                    sendPhoto.setReplyMarkup(InlineButtonUtilProductCategory.menuSingleKeyboard(product.getId(), ProfileBotLanguage.UZBEK));

                else if (language.equals(ProfileBotLanguage.RUSSIAN.name()))
                    // Bu yerga tagiga Ruscha keyboard tushirish uchun ishlatiladi.
                    sendPhoto.setReplyMarkup(InlineButtonUtilProductCategory.menuSingleKeyboard(product.getId(), ProfileBotLanguage.RUSSIAN));
                else
                    // Bu yerga tagiga Krillcha keyboard tushirish uchun ishlatiladi.
                    sendPhoto.setReplyMarkup(InlineButtonUtilProductCategory.menuSingleKeyboard(product.getId(), ProfileBotLanguage.KRILL));

                // malumotlar jonatiladi toki for tugagungacha
                botManager.send(sendPhoto);
            }
        }  else if (text.startsWith("buyurtma_berish_uz_") || text.startsWith("buyurtma_berish_rus_") || text.startsWith("buyurtma_berish_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            BuyurtmalarDTO buyurtma = new BuyurtmalarDTO();
            buyurtma.setProductId(Integer.valueOf(numberOnly));
            buyurtma.setStatus(BuyurtmalarStatus.NOT_ACTIVE);
            buyurtma.setUserId(user.getId());

            buyurtmalarService.createProfile(buyurtma);

            Integer buyurtmaId = buyurtmalarService.getAllById(Integer.valueOf(numberOnly), user.getId());

            if (text.startsWith("buyurtma_berish_uz_")) {
                sendMessage.setText("Sizga yetkazib beraylikmi yoki ozingiz olib ketasizmi! ");
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuBuyurtmaTypeKeyboard(buyurtmaId, ProfileBotLanguage.UZBEK));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("buyurtma_berish_rus_")) {
                sendMessage.setText("Доставим ли мы вам или возьмем немного! ");
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuBuyurtmaTypeKeyboard(buyurtmaId, ProfileBotLanguage.RUSSIAN));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("buyurtma_berish_krill_")) {
                sendMessage.setText("Сизга етказиб берайликми ёки озингиз олиб кетасизми! ");
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuBuyurtmaTypeKeyboard(buyurtmaId, ProfileBotLanguage.KRILL));
                botManager.sendMessage(sendMessage);
            }

        } else if (text.startsWith("savatchaga_qoshish_uz_") || text.startsWith("savatchaga_qoshish_rus_") || text.startsWith("savatchaga_qoshish_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            SavatchaDTO savatcha = new SavatchaDTO();
            savatcha.setProductId(Integer.valueOf(numberOnly));
            savatcha.setUserId(user.getId());

            savatchaService.createProfile(savatcha);

            if (text.startsWith("savatchaga_qoshish_uz_")) {
                sendMessage.setText("Qoshildi");
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("savatchaga_qoshish_rus_")) {
                sendMessage.setText("Добавлен");
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("savatchaga_qoshish_krill_")) {
                sendMessage.setText("қўшилди");
                botManager.sendMessage(sendMessage);
            }

        } else if (text.startsWith("yetkazib_berish_uz_") || text.startsWith("yetkazib_berish_ru_") || text.startsWith("yetkazib_berish_krill_")) {
            if (text.startsWith("yetkazib_berish_uz_")) {
                sendMessage.setText("Joylashuvingizni yuboring");
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuSingleKeyboardsUzb());
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("yetkazib_berish_ru_")) {
                sendMessage.setText("Отправьте свое местоположение");
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuSingleKeyboardsRus());
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("yetkazib_berish_krill_")) {
                sendMessage.setText("Жойлашувингизни юборинг");
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuSingleKeyboardsKrill());
                botManager.sendMessage(sendMessage);
            }
        } else if (text.startsWith("buyurtmalarni_korish_uz_") || text.startsWith("buyurtmalarni_korish_rus_") || text.startsWith("buyurtmalarni_korish_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            List<BuyurtmalarDTO> buyurtmalarList = buyurtmalarService.getAllByUserId(Long.valueOf(numberOnly));

            for (BuyurtmalarDTO buyurtma : buyurtmalarList) {
                if (buyurtma.getStatus().equals(BuyurtmalarStatus.SAVATCHADA) || buyurtma.getStatus().equals(BuyurtmalarStatus.NOT_ACTIVE)) {
                    sendMessage.setText("Product Id: " + buyurtma.getProductId() + "\n" +
                            "Status: " + buyurtma.getStatus() + "\n" +
                            "CreatedDate: " + buyurtma.getCreatedDate() + "\n" +
                            "userId: " + numberOnly + "\n" +
                            "\n" +
                            "-----------------------");

                    botManager.sendMessage(sendMessage);
                }
            }
        } else if (text.startsWith("buyurtmalarni_tasdiqlash_uz_") || text.startsWith("buyurtmalarni_tasdiqlash_rus_") || text.startsWith("buyurtmalarni_tasdiqlash_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            buyurtmalarService.updateBuyurtmaStatus(BuyurtmalarStatus.DELIVERY.name(), Long.valueOf(numberOnly), BuyurtmalarStatus.NOT_ACTIVE.name());
            sendMessage.setChatId(numberOnly);

            if (text.startsWith("buyurtmalarni_tasdiqlash_uz_")) {
                sendMessage.setText("Buyurtmangiz yetib borsa bosing: ");
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuStatusBuyurtmaKeyboardUZB(Long.valueOf(numberOnly)));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("buyurtmalarni_tasdiqlash_rus_")) {
                sendMessage.setText("Нажмите, когда ваш заказ прибудет: ");
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuStatusBuyurtmaKeyboardRUS(Long.valueOf(numberOnly)));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("buyurtmalarni_tasdiqlash_krill_")) {
                sendMessage.setText("Буюртмангиз етиб борса босинг: ");
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuStatusBuyurtmaKeyboardKRILL(Long.valueOf(numberOnly)));
                botManager.sendMessage(sendMessage);
            }
        } else if (text.startsWith("buyurma_yetib_keldi_uz_") || text.startsWith("buyurma_yetib_keldi_ru_") || text.startsWith("buyurma_yetib_keldi_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            buyurtmalarService.updateBuyurtmaStatus(BuyurtmalarStatus.DELIVERY.name(), Long.valueOf(numberOnly), BuyurtmalarStatus.NOT_ACTIVE.name());
            savatchaService.deleteSavatcha(Long.valueOf(numberOnly));

            if (text.startsWith("buyurma_yetib_keldi_uz_")) {
                sendMessage.setText("Xizmatimizdan foydalanganingizdan mamnunmiz");
                sendMessage.setReplyMarkup(InlineButtonUtilManager.menuNextLogin(ProfileBotLanguage.UZBEK));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("buyurma_yetib_keldi_ru_")) {
                sendMessage.setText("Мы рады, что вы воспользовались нашим сервисом");
                sendMessage.setReplyMarkup(InlineButtonUtilManager.menuNextLogin(ProfileBotLanguage.RUSSIAN));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("buyurma_yetib_keldi_krill_")) {
                sendMessage.setText("Хизматимиздан фойдаланганингиздан мамнунмиз");
                sendMessage.setReplyMarkup(InlineButtonUtilManager.menuNextLogin(ProfileBotLanguage.KRILL));
                botManager.sendMessage(sendMessage);
            }
        } else if (text.startsWith("olib_ketish_uz_") || text.startsWith("olib_ketish_ru_") || text.startsWith("olib_ketish_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            buyurtmalarService.updateBuyurtmaStatus(BuyurtmalarStatus.DELIVERY.name(), Long.valueOf(numberOnly), BuyurtmalarStatus.NOT_ACTIVE.name());
            savatchaService.deleteSavatcha(Long.valueOf(numberOnly));

            if (text.startsWith("olib_ketish_uz_")) {
                sendMessage.setText("Xizmatimizdan foydalanganingizdan mamnunmiz. Productlarni +998904091478 nomerlari orqali borib " +
                        "harid qilishingiz mumkin");
                sendMessage.setReplyMarkup(InlineButtonUtilManager.menuNextLogin(ProfileBotLanguage.UZBEK));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("olib_ketish_ru_")) {
                sendMessage.setText("Мы рады, что вы воспользовались нашим сервисом. Купить продукцию можно по телефону +998904091478");
                sendMessage.setReplyMarkup(InlineButtonUtilManager.menuNextLogin(ProfileBotLanguage.RUSSIAN));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("olib_ketish_krill_")) {
                sendMessage.setText("Хизматимиздан фойдаланганингиздан хурсандмиз. Маҳсулотларни +998904091478 телефон рақами орқали" +
                        " харид қилишингиз мумкин");
                sendMessage.setReplyMarkup(InlineButtonUtilManager.menuNextLogin(ProfileBotLanguage.KRILL));
                botManager.sendMessage(sendMessage);
            }
        } else if (text.startsWith("yetkazib_berish_savatcha_uz_") || text.startsWith("yetkazib_berish_savatcha_ru_") || text.startsWith("yetkazib_berish_savatcha_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            buyurtmalarService.updateBuyurtmaStatus(BuyurtmalarStatus.NOT_ACTIVE.name(), Long.valueOf(numberOnly), BuyurtmalarStatus.SAVATCHADA.name());

            if (text.startsWith("yetkazib_berish_savatcha_uz_")) {
                sendMessage.setText("Joylashuvingizni yuboring");
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuSingleKeyboardsUzb());
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("yetkazib_berish_savatcha_ru_")) {
                sendMessage.setText("Отправьте свое местоположение");
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuSingleKeyboardsRus());
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("yetkazib_berish_savatcha_krill_")) {
                sendMessage.setText("Жойлашувингизни юборинг");
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuSingleKeyboardsKrill());
                botManager.sendMessage(sendMessage);
            }
        } else if (text.startsWith("olib_ketish_savatcha_uz_") || text.startsWith("olib_ketish_savatcha_ru_") || text.startsWith("olib_ketish_savatcha_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            savatchaService.deleteSavatcha(Long.valueOf(numberOnly));

            if (text.startsWith("olib_ketish_savatcha_uz_")) {
                sendMessage.setText("Xizmatimizdan foydalanganingizdan mamnunmiz. Productlarni +998904091478 nomerlari orqali borib " +
                        "harid qilishingiz mumkin. Savatchangiz Tozalab Tashlandi");
                sendMessage.setReplyMarkup(InlineButtonUtilManager.menuNextLogin(ProfileBotLanguage.UZBEK));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("olib_ketish_savatcha_ru_")) {
                sendMessage.setText("Мы рады, что вы воспользовались нашим сервисом. Купить продукцию можно по телефону +998904091478" +
                        " Ваша корзина очищена");
                sendMessage.setReplyMarkup(InlineButtonUtilManager.menuNextLogin(ProfileBotLanguage.RUSSIAN));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("olib_ketish_savatcha_krill_")) {
                sendMessage.setText("Хизматимиздан фойдаланганингиздан хурсандмиз. Маҳсулотларни +998904091478 телефон рақами орқали" +
                        " харид қилишингиз мумкин. Саватчангиз Тозалаб Ташланди");
                sendMessage.setReplyMarkup(InlineButtonUtilManager.menuNextLogin(ProfileBotLanguage.KRILL));
                botManager.sendMessage(sendMessage);
            }
        } else if (text.startsWith("tozalash_uz_") || text.startsWith("tozalash_ru_") || text.startsWith("tozalash_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            savatchaService.deleteSavatcha(Long.valueOf(numberOnly));

            if (text.startsWith("tozalash_uz_")) {
                sendMessage.setText("Muvaffaqiyatli Tozalandi");
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("tozalash_ru_")) {
                sendMessage.setText("Успешно очищено");
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("tozalash_krill_")) {
                sendMessage.setText("Муваффақиятли Тозаланди");
                botManager.sendMessage(sendMessage);
            }
        } else {
            muddatliTolovBot.handlerCallbackQuery(user, text, messageId);
        }
    }
}
