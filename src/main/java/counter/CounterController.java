package counter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutorService;

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
        return new ResponseEntity<>(CounterData.getNames(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<String> getName(@PathVariable String name) {
        if (CounterData.notContainsKey(name))
            return new ResponseEntity<>("Element not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(name.toUpperCase() + ": " + CounterData.getForName(name), HttpStatus.OK);
    }

    @PostMapping("/{name}")
    public ResponseEntity<String> add(@PathVariable String name) {
        if (CounterData.create(name) != null)
            return new ResponseEntity<>("Element already exist", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(name.toUpperCase() + " added", HttpStatus.OK);
    }

    @GetMapping("/sum")
    public ResponseEntity<String> getSum() {
        return new ResponseEntity<>(CounterData.getSum(), HttpStatus.OK);
    }

    @PutMapping("/{name}")
    public ResponseEntity<String> update(@PathVariable String name) {
        if (CounterData.notContainsKey(name))
            return new ResponseEntity<>("Element not found", HttpStatus.BAD_REQUEST);
        CounterData.increment(name);
        return new ResponseEntity<>(name.toUpperCase() + " updated", HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> del(@PathVariable String name) {
        if (CounterData.notContainsKey(name))
            return new ResponseEntity<>("Element not found", HttpStatus.BAD_REQUEST);
        CounterData.remove(name);
        return new ResponseEntity<>(name.toUpperCase() + " deleted", HttpStatus.OK);
    }

    @GetMapping("/shutdown")
    public void shutSown(){
        ((ConfigurableApplicationContext) context).close();
    }
}