//package com.sparta.topster.domain.user;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import com.sparta.topster.domain.user.service.mail.MailService;
//import com.sparta.topster.global.util.RedisUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.StringRedisTemplate;
//
//@SpringBootTest
//public class Mailtest {
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @InjectMocks
//    private MailService mailService;
//
//    @BeforeEach
//    void setup(){
//        stringRedisTemplate.getConnectionFactory().getConnection().flushAll();
//    }
//
//    @Test
//    public void testSetDataExpire() throws Exception {
//        // Test data
//        String email = "test@example.com";
//        String ePw = "testPassword";
//        long expirationDuration = 60 * 5L; // 5 minutes
//
//        String sendMail = mailService.sendSimpleMessage(email);
//
//        redisUtil.setDataExpire(email, ePw, expirationDuration);
//
//        String retrievedValue = stringRedisTemplate.opsForValue().get(email);
//
//        assertEquals(ePw, retrievedValue);
//
//        assertTrue(stringRedisTemplate.hasKey(email), ePw);
//    }
//}
