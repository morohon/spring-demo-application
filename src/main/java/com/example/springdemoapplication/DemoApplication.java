package com.example.springdemoapplication;

import com.example.springdemoapplication.entity.Message;
import com.example.springdemoapplication.repos.MessageRepo;
import org.aspectj.bridge.IMessage;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Kirill Verevkin
 */
@SpringBootApplication
@RestController
public class DemoApplication {

    @Autowired
    private MessageRepo messageRepo;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/world")
    public String world(@RequestParam(value = "name", defaultValue = "Hello") String name) {
        return String.format("%s world!", name);
    }

    @GetMapping
    public ModelAndView main(){
        Iterable<Message> messages = messageRepo.findAll();

        ModelAndView modelAndView = new ModelAndView("main");
        modelAndView.addObject("messages", messages);

        return modelAndView;
    }

    @PostMapping
    public ModelAndView add(@RequestParam String text, @RequestParam String tag){
        Message message = new Message(text, tag);
        messageRepo.save(message);

        return main();
    }

    @PostMapping("/filter")
    public ModelAndView filter(@RequestParam String tag){

        if (tag == null || tag.isEmpty()){
            return main();
        }

        Iterable<Message> messages = messageRepo.findByTag(tag);
        ModelAndView modelAndView = new ModelAndView("main");
        modelAndView.addObject("messages", messages);

        return modelAndView;
    }

}
