package ua.boa.smartlibrary;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.annotation.WebServlet;

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
