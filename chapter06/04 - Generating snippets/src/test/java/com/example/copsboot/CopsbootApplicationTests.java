package com.example.copsboot;

import com.example.copsboot.infrastructure.SpringProfiles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(SpringProfiles.TEST)
public class CopsbootApplicationTests {

    @Test
    public void contextLoads() {
    }

}
