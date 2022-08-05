package io.github.huafoog.fir.auth;

import io.github.huafoog.fir.auth.mapper.GenMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Tests {
    @Autowired
    private GenMapper genMapper;

    @Test
    public void test1(){
        List<Map<String, String>> maps = genMapper.selectTableColumn();
        System.out.println(maps);
     }
}
