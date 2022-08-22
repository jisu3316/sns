package com.jisu.service;

import com.jisu.exeception.ErrorCode;
import com.jisu.model.entity.UserEntity;
import com.jisu.exeception.SnsApplicationException;
import com.jisu.model.User;
import com.jisu.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;

    public User join(String username, String password) {
        userEntityRepository.findByUserName(username).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", username));
        });

        UserEntity userEntity = userEntityRepository.save(UserEntity.of(username, password));

        return User.fromEntity(userEntity);
    }

    public String login(String username, String password) {
        UserEntity userEntity = userEntityRepository.findByUserName(username).orElseThrow(() -> new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, ""));

        if (userEntity.getPassword().equals(password)) {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, "");
        }
        return "";
    }
}
