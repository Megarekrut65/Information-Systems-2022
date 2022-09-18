package ua.boa.smartlibrary;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestServlet {
    @GetMapping("/")
    String getPage(){

        return "Hi, everybody";
    }
    @GetMapping("/hello")
    String getHello(){
        return "Hello, world!";
    }
}
