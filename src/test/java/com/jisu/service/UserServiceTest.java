package com.jisu.service;

import com.jisu.model.entity.UserEntity;
import com.jisu.exeception.SnsApplicationException;
import com.jisu.fixture.UserEntityFixture;
import com.jisu.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @Test
    void 회원가입이_정삭적으로_동작하는_경우() {
        String username = "username";
        String password = "password";

        //mocking
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of(UserEntityFixture.get(username, password)));

        Assertions.assertDoesNotThrow(() -> userService.join(username, password));
    }

    @Test
    void 회원가입이시_userName으로_회원가입한_유저가_이미_있는_경우() {
        String username = "username";
        String password = "password";
        UserEntity fixture = UserEntityFixture.get(username, password);

        //mocking
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(fixture));
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));

        Assertions.assertThrows(SnsApplicationException.class, () -> userService.join(username, password));
    }

    @Test
    void 로그인이_정삭적으로_동작하는_경우() {
        String username = "username";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(username, password);

        //mocking
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(fixture));
        Assertions.assertDoesNotThrow(() -> userService.login(username, password));
    }

    @Test
    void 로그인시_userName으로_회원가입한_유저가_없는_경우() {
        String username = "username";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(username, password);

        //mocking
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.empty());

        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(username, password));
    }

    @Test
    void 로그인시_패스워드가_틀린_경우() {
        String username = "username";
        String password = "password";
        String wrongPassword = "wrongPassword";

        UserEntity fixture = UserEntityFixture.get(username, password);

        //mocking
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(fixture));

        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(username, wrongPassword));
    }
}
