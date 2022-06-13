package yashirin.suhbat.yashirinsuhbatbot.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import yashirin.suhbat.yashirinsuhbatbot.controller.MyBotService;
import yashirin.suhbat.yashirinsuhbatbot.repository.UserRepository;


@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final MyBotService myBotService;
    private final UserRepository userRepository;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) throws Exception {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this.myBotService);

        if (ddl.equalsIgnoreCase("create") || ddl.equalsIgnoreCase("create-drop")){
            System.out.println("Hello World");
        }
    }




}
