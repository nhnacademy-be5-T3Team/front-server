package com.t3t.frontserver.index;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class TestController {

    @PostMapping("/test1")
    public String test(@ModelAttribute OrderFormRequest request){

        log.info("'바로 주문 하기' 요청 ");
        log.info(String.valueOf(request.getBookId()));
        log.info(String.valueOf(request.getOrderQuantity()));
        log.info(String.valueOf(request.getPackageId()));

        return "index/index.html";
    }

    @PostMapping("/test2")
    public String test2(@ModelAttribute OrderFormRequest request){

        log.info("'장바구니 담기' 요청 ");
        log.info(String.valueOf(request.getBookId()));
        log.info(String.valueOf(request.getOrderQuantity()));
        log.info(String.valueOf(request.getPackageId()));

        return "index/index.html";
    }
}

