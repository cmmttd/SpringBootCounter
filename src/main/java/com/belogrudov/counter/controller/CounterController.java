package com.belogrudov.counter.controller;

import com.belogrudov.counter.data.CounterData;
import com.belogrudov.counter.exceptions.BadRequestException;
import com.belogrudov.counter.exceptions.NotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Описание задачи:
 * Необходимо реализовать spring-boot приложение на JDK 8+, счетчик.
 *
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
    private final CounterData counterData;

    public CounterController(ApplicationContext context, CounterData counterData) {
        this.context = context;
        this.counterData = counterData;
    }

    @GetMapping
    public ResponseEntity<List<String>> getNames() {
        return ResponseEntity.ok(counterData.readKeys());
    }

    @GetMapping("/{name}")
    public ResponseEntity<String> getName(@PathVariable String name) {
        if (counterData.notContainsKey(name))
            throw new NotFoundException("Element not found");
        return ResponseEntity.ok(name.toUpperCase() + ": " + counterData.read(name));
    }

    @PostMapping("/{name}")
    public ResponseEntity<String> add(@PathVariable String name) {
        if (counterData.notContainsKey(name))
            return ResponseEntity.ok(name.toUpperCase() + " added");
        else
            throw new BadRequestException("Element already exist");
    }

    @GetMapping("/sum")
    public ResponseEntity<String> getSum() {
        return ResponseEntity.ok(counterData.sum());
    }

    @PutMapping("/{name}")
    public ResponseEntity<String> update(@PathVariable String name) {
        if (counterData.notContainsKey(name))
            throw new NotFoundException("Element not found");
        counterData.update(name);
        return ResponseEntity.ok(name.toUpperCase() + " updated");
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> del(@PathVariable String name) {
        if (counterData.notContainsKey(name))
            throw new NotFoundException("Element not found");
        counterData.delete(name);
        return ResponseEntity.ok(name.toUpperCase() + " deleted");
    }

    @GetMapping("/shutdown")
    public void shutSown() {
        ((ConfigurableApplicationContext) context).close();
    }
}