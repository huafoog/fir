package io.github.huafoog.fir.generator.controller;

import io.github.huafoog.fir.common.core.R;
import io.github.huafoog.fir.generator.mapper.GenMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class TestController {
    private final GenMapper genMapper;

    @GetMapping("/test1")
    public R getPage() {
        return R.ok(genMapper.selectTableColumn());
    }
}
