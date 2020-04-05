package com.belogrudov.counter.controller;

import com.belogrudov.counter.data.CounterData;
import com.belogrudov.counter.exceptions.BadRequestException;
import com.belogrudov.counter.exceptions.NotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Описание задачи:
 * Необходимо реализовать spring-boot приложение на JDK 8+, счетчик.
 * <p>
 * Требования:
 * Посредством RestApi должна быть возможность:
 * Создать счетчик с уникальным именем;
 * Изменить значение счетчика с указанным именем;
 * Получить значения счетчика с указанным именем;
 * Удалить счетчик с указанным именем;
 * Получить суммарное значение всех счетчиков;
 * Получить уникальные имена счетчиков в виде списка.
 * Значения всех счетчиков должны храниться в памяти приложения.
 */

@RestController
@RequestMapping("/counter")
public class CounterController {

    private final ApplicationContext context;

    public CounterController(ApplicationContext context) {
        this.context = context;
    }

    @GetMapping
    public ResponseEntity<List<String>> getNames() {
        return ResponseEntity.ok(CounterData.getNames());
    }

    @GetMapping("/{name}")
    public ResponseEntity<String> getName(@PathVariable String name) {
        if (CounterData.notContainsKey(name))
            throw new NotFoundException("Element not found");
        return ResponseEntity.ok(name.toUpperCase() + ": " + CounterData.getForName(name));
    }

    @PostMapping("/{name}")
    public ResponseEntity<String> add(@PathVariable String name) {
        if (CounterData.create(name) != null)
            throw new BadRequestException("Element already exist");
        return ResponseEntity.ok(name.toUpperCase() + " added");
    }

    @GetMapping("/sum")
    public ResponseEntity<String> getSum() {
        return ResponseEntity.ok(CounterData.getSum());
    }

    @PutMapping("/{name}")
    public ResponseEntity<String> update(@PathVariable String name) {
        if (CounterData.notContainsKey(name))
            throw new NotFoundException("Element not found");
        CounterData.increment(name);
        return ResponseEntity.ok(name.toUpperCase() + " updated");
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> del(@PathVariable String name) {
        if (CounterData.notContainsKey(name))
            throw new NotFoundException("Element not found");
        CounterData.remove(name);
        return ResponseEntity.ok(name.toUpperCase() + " deleted");
    }

    @GetMapping("/shutdown")
    public void shutSown() {
        ((ConfigurableApplicationContext) context).close();
    }
}