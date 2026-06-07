package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class Application {

    private final List<byte[]> allocations = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void startup() {

        Runtime runtime = Runtime.getRuntime();

        System.out.println("=================================");
        System.out.println("Processors : " + runtime.availableProcessors());
        System.out.println("Max Heap   : "
                + runtime.maxMemory()/1024/1024 + " MB");
        System.out.println("=================================");
    }

    @GetMapping("/")
    public String health() {
        return "Application Running";
    }

    @GetMapping("/allocate")
    public String allocate(@RequestParam(defaultValue = "100") int mb) {

        allocations.add(new byte[mb * 1024 * 1024]);

        return "Allocated " + mb + " MB";
    }

    @GetMapping("/heap")
    public String heap() {

        Runtime rt = Runtime.getRuntime();

        return String.format(
                "max=%dMB total=%dMB free=%dMB",
                rt.maxMemory()/1024/1024,
                rt.totalMemory()/1024/1024,
                rt.freeMemory()/1024/1024
        );
    }
}