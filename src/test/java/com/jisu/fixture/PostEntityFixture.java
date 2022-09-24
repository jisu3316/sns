package com.jisu.fixture;

import com.jisu.model.entity.PostEntity;
import com.jisu.model.entity.UserEntity;

public class PostEntityFixture {

    public static PostEntity get(String username, Integer postId, Integer userId) {
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUserName(username);

        PostEntity result = new PostEntity();
        result.setUser(user);
        result.setId(postId);

        return result;
    }
}
