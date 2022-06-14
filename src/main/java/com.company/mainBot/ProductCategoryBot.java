package com.company.mainBot;

import com.company.dto.BuyurtmalarDTO;
import com.company.dto.ProductDTO;
import com.company.dto.SavatchaDTO;
import com.company.enums.BuyurtmalarStatus;
import com.company.manager.BotManager;
import com.company.service.BuyurtmalarService;
import com.company.service.ProductService;
import com.company.service.SavatchaService;
import com.company.util.InlineButtonUtilProductCategory;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.LinkedList;
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

    public void handlerCallbackQuery(User user, String text, Integer messageId) {

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(user.getId()));

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(user.getId()));

        if (text.equals("yumshoq_mebellar_uz") || text.equals("yumshoq_mebellar_rus") || text.equals("yumshoq_mebellar_krill")) {
            // Bu yerda 1 beryapman sababi Categoriyasi 1 ga teng bolgan degan maqsadda ikkinchi 1 esa ProductCategory uchun
            List<ProductDTO> productList = productService.getAllById(1, 1);

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
                if (text.equals("yumshoq_mebellar_uz"))
                    // Bu yerga tagiga Uzbekcha keyboard tushirish uchun ishlatiladi.
                    sendPhoto.setReplyMarkup(InlineButtonUtilProductCategory.menuSingleKeyboardUZB(product.getId()));

                else if (text.equals("yumshoq_mebellar_rus"))
                    // Bu yerga tagiga Ruscha keyboard tushirish uchun ishlatiladi.
                    sendPhoto.setReplyMarkup(InlineButtonUtilProductCategory.menuSingleKeyboardRUS(product.getId()));

                else
                    // Bu yerga tagiga Krillcha keyboard tushirish uchun ishlatiladi.
                    sendPhoto.setReplyMarkup(InlineButtonUtilProductCategory.menuSingleKeyboardKRILL(product.getId()));

                // malumotlar jonatiladi toki for tugagungacha
                botManager.send(sendPhoto);
            }
        } else if (text.startsWith("buyurtma_berish_uz_") || text.startsWith("buyurtma_berish_rus_") || text.startsWith("buyurtma_berish_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            BuyurtmalarDTO buyurtma = new BuyurtmalarDTO();
            buyurtma.setProductId(Integer.valueOf(numberOnly));
            buyurtma.setStatus(BuyurtmalarStatus.NOT_ACTIVE);
            buyurtma.setUserId(Math.toIntExact(user.getId()));

            buyurtmalarService.createProfile(buyurtma);

            Integer buyurtmaId = buyurtmalarService.getAllById(Integer.valueOf(numberOnly), Math.toIntExact(user.getId()));

            if (text.startsWith("buyurtma_berish_uz_")) {
                sendMessage.setText("Sizga yetkazib beraylikmi yoki ozingiz olib ketasizmi! ");
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuBuyurtmaTypeKeyboardUZB(buyurtmaId));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("buyurtma_berish_rus_")) {
                sendMessage.setText("Доставим ли мы вам или возьмем немного! ");
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuBuyurtmaTypeKeyboardRUS(buyurtmaId));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("buyurtma_berish_krill_")) {
                sendMessage.setText("Сизга етказиб берайликми ёки озингиз олиб кетасизми! ");
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuBuyurtmaTypeKeyboardKRILL(buyurtmaId));
                botManager.sendMessage(sendMessage);
            }

        } else if (text.startsWith("savatchaga_qoshish_uz_") || text.startsWith("savatchaga_qoshish_rus_") || text.startsWith("savatchaga_qoshish_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            SavatchaDTO savatcha = new SavatchaDTO();
            savatcha.setProductId(Integer.valueOf(numberOnly));
            savatcha.setUserId(Math.toIntExact(user.getId()));

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

            List<BuyurtmalarDTO> buyurtmalarList = buyurtmalarService.getAllByUserId(Integer.valueOf(numberOnly));

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

            buyurtmalarService.updateBuyurtmaStatus(BuyurtmalarStatus.DELIVERY.name(), Integer.valueOf(numberOnly), BuyurtmalarStatus.NOT_ACTIVE.name());


            if (text.startsWith("buyurtmalarni_tasdiqlash_uz_")) {
                sendMessage.setText("Buyurtmangiz yetib borsa bosing: ");
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuStatusBuyurtmaKeyboardUZB(Integer.valueOf(numberOnly)));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("buyurtmalarni_tasdiqlash_rus_")) {
                sendMessage.setText("Нажмите, когда ваш заказ прибудет: ");
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuStatusBuyurtmaKeyboardRUS(Integer.valueOf(numberOnly)));
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("buyurtmalarni_tasdiqlash_krill_")) {
                sendMessage.setText("Буюртмангиз етиб борса босинг: ");
                sendMessage.setReplyMarkup(InlineButtonUtilProductCategory.menuStatusBuyurtmaKeyboardKRILL(Integer.valueOf(numberOnly)));
                botManager.sendMessage(sendMessage);
            }
        } else if (text.startsWith("buyurma_yetib_keldi_uz_") || text.startsWith("buyurma_yetib_keldi_ru_") || text.startsWith("buyurma_yetib_keldi_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            buyurtmalarService.updateBuyurtmaStatus(BuyurtmalarStatus.DELIVERY.name(), Integer.valueOf(numberOnly), BuyurtmalarStatus.NOT_ACTIVE.name());
            savatchaService.deleteSavatcha(Integer.valueOf(numberOnly));

            if (text.startsWith("buyurma_yetib_keldi_uz_")) {
                sendMessage.setText("Xizmatimizdan foydalanganingizdan mamnunmiz");
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("buyurma_yetib_keldi_ru_")) {
                sendMessage.setText("Мы рады, что вы воспользовались нашим сервисом");
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("buyurma_yetib_keldi_krill_")) {
                sendMessage.setText("Хизматимиздан фойдаланганингиздан мамнунмиз");
                botManager.sendMessage(sendMessage);
            }
        } else if (text.startsWith("olib_ketish_uz_") || text.startsWith("olib_ketish_ru_") || text.startsWith("olib_ketish_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            buyurtmalarService.updateBuyurtmaStatus(BuyurtmalarStatus.DELIVERY.name(), Integer.valueOf(numberOnly), BuyurtmalarStatus.NOT_ACTIVE.name());
            savatchaService.deleteSavatcha(Integer.valueOf(numberOnly));

            if (text.startsWith("olib_ketish_uz_")) {
                sendMessage.setText("Xizmatimizdan foydalanganingizdan mamnunmiz. Productlarni +998904091478 nomerlari orqali borib " +
                        "harid qilishingiz mumkin");
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("olib_ketish_ru_")) {
                sendMessage.setText("Мы рады, что вы воспользовались нашим сервисом. Купить продукцию можно по телефону +998904091478");
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("olib_ketish_krill_")) {
                sendMessage.setText("Хизматимиздан фойдаланганингиздан хурсандмиз. Маҳсулотларни +998904091478 телефон рақами орқали" +
                        " харид қилишингиз мумкин");
                botManager.sendMessage(sendMessage);
            }
        } else if (text.startsWith("yetkazib_berish_savatcha_uz_") || text.startsWith("yetkazib_berish_savatcha_ru_") || text.startsWith("yetkazib_berish_savatcha_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            buyurtmalarService.updateBuyurtmaStatus(BuyurtmalarStatus.NOT_ACTIVE.name(), Integer.valueOf(numberOnly), BuyurtmalarStatus.SAVATCHADA.name());

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

            savatchaService.deleteSavatcha(Integer.valueOf(numberOnly));

            if (text.startsWith("olib_ketish_savatcha_uz_")) {
                sendMessage.setText("Xizmatimizdan foydalanganingizdan mamnunmiz. Productlarni +998904091478 nomerlari orqali borib " +
                        "harid qilishingiz mumkin. Savatchangiz Tozalab Tashlandi");
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("olib_ketish_savatcha_ru_")) {
                sendMessage.setText("Мы рады, что вы воспользовались нашим сервисом. Купить продукцию можно по телефону +998904091478" +
                        " Ваша корзина очищена");
                botManager.sendMessage(sendMessage);
            } else if (text.startsWith("olib_ketish_savatcha_krill_")) {
                sendMessage.setText("Хизматимиздан фойдаланганингиздан хурсандмиз. Маҳсулотларни +998904091478 телефон рақами орқали" +
                        " харид қилишингиз мумкин. Саватчангиз Тозалаб Ташланди");
                botManager.sendMessage(sendMessage);
            }
        } else if (text.startsWith("tozalash_uz_") || text.startsWith("tozalash_ru_") || text.startsWith("tozalash_krill_")) {
            String numberOnly = text.replaceAll("[^0-9]", "");

            savatchaService.deleteSavatcha(Integer.valueOf(numberOnly));

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
