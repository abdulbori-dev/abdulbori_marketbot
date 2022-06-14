package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AbdulboriMarketbotApplication {

	public static void main(String[] args) {

		SpringApplication.run(AbdulboriMarketbotApplication.class, args);

//		try {
//			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
//			botsApi.registerBot(new BotManager());
//		} catch (TelegramApiException e) {
//			e.printStackTrace();
//		}
	}

}
