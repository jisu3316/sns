package com.jisu.service;

import com.jisu.exeception.ErrorCode;
import com.jisu.exeception.SnsApplicationException;
import com.jisu.model.entity.PostEntity;
import com.jisu.model.entity.UserEntity;
import com.jisu.repository.PostEntityRepository;
import com.jisu.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;
    @Transactional
    public void create(String title, String body, String userName) {
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", userName)));

        postEntityRepository.save(PostEntity.of(title, body, userEntity));

    }
}
