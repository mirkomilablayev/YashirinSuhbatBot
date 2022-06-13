package yashirin.suhbat.yashirinsuhbatbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import yashirin.suhbat.yashirinsuhbatbot.configuration.BotConfiguration;
import yashirin.suhbat.yashirinsuhbatbot.entity.User;
import yashirin.suhbat.yashirinsuhbatbot.exception.UserNotFoundException;
import yashirin.suhbat.yashirinsuhbatbot.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MyBotService extends TelegramLongPollingBot {

    private static final String filePath = "/home/shoh/d_doc/backUp/database/";

    private final BotConfiguration botConfiguration;
    private final UserRepository userRepository;


    @Override
    public String getBotUsername() {
        return this.botConfiguration.getUsername();
    }

    @Override
    public String getBotToken() {
        return this.botConfiguration.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        String chatId = getChatId(update);
        User currentUser = getCurrentUser(update, chatId);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        if (update.hasMessage()) {

        } else {
            int languageCode = currentUser.getLanguageCode();
            if (languageCode == 1)
                sendMessage.setText("Sizning oxirgi xabaringiz qo'llab quvvatlanmaydi");
            else if (languageCode == 2)
                sendMessage.setText("Your last message is not supported");
            else if (languageCode == 3)
                sendMessage.setText("Ваше последнее сообщение не поддерживается");
            else
                sendMessage.setText("This language doesn't exist");

        }
    }


    public String getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId().toString();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId().toString();
        } else
            return "";
    }


    public User getCurrentUser(Update update, String chatId) {
        Optional<User> userOptional = userRepository.findByChatId(chatId);
        try {
            return userOptional.orElseThrow(UserNotFoundException::new);
        } catch (UserNotFoundException e) {
            User user = new User();
            user.setChatId(chatId);
            org.telegram.telegrambots.meta.api.objects.User from = update.getMessage().getFrom();
            String lastName = from.getLastName().length() == 0 ? "" : from.getLastName();
            String fullName = from.getFirstName() + " " + lastName;
            user.setFullName(fullName);
            user.setUsername(from.getUserName().length() == 0 ? "" : from.getUserName());
            user.setRealId(from.getId().toString());
            user.setStep(1);
            return userRepository.save(user);
        }
    }


    public ReplyKeyboardMarkup getButton(int step) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        switch (step) {
            case 1:
                List<KeyboardRow> keyboardRowList = new ArrayList<>();
                KeyboardRow keyboardButtons = new KeyboardRow();
                KeyboardButton keyboardButton = new KeyboardButton();
                keyboardButton.setText("Raqamni Yuborish");
                keyboardButton.setRequestContact(true);
                keyboardButtons.add(keyboardButton);
                keyboardRowList.add(keyboardButtons);
                keyboardMarkup.setKeyboard(keyboardRowList);
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            default:

                break;
        }
        return keyboardMarkup;
    }


}
