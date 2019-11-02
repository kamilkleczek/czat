package taborski.kleczek.chat.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("userapp")
public interface IUserAppClient {
    @RequestMapping("/api/user/greeting")
    String greeting();
}

