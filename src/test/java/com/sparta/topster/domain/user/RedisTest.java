package com.sparta.topster.domain.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    void testStrings() {
        //given
        ValueOperations<String, String> valueOperations =  redisTemplate.opsForValue();
        String key = "testKey";

        //when
        valueOperations.set(key,"testvalue");

        //then
        String value = valueOperations.get(key);
        Assertions.assertThat(value).isEqualTo("testvalue");
    }
}
