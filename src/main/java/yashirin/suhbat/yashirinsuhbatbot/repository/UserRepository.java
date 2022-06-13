package yashirin.suhbat.yashirinsuhbatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yashirin.suhbat.yashirinsuhbatbot.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByChatId(String chatId);

}
