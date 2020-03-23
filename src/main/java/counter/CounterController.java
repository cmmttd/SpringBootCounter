package counter;

import org.springframework.http.HttpStatus;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<String> getNames(){
        return CounterData.getNames();
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public String getName(@PathVariable String name){
        if (CounterData.notContainsKey(name)) throw new NameNotFoundException();
        return CounterData.getForName(name);
    }

    @PostMapping("/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public String add(@PathVariable String name){
        CounterData.create(name);
        return name;
    }

    @GetMapping("/sum")
    @ResponseStatus(HttpStatus.OK)
    public String getSum(){
        return CounterData.getSum();
    }

    @PutMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public String update(@PathVariable String name){
        if (CounterData.notContainsKey(name)) throw new NameNotFoundException();
        CounterData.increment(name);
        return name;
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public String del(@PathVariable String name){
        if (CounterData.notContainsKey(name)) throw new NameNotFoundException();
        CounterData.remove(name);
        return name;
    }
}