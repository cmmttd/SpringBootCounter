package counter;

import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/counters")
public class CounterController {

    @GetMapping("/create")
    @ResponseBody
    public String create(@RequestParam String name) {
        if (CounterData.containsKey(name)) {
            return "Failed: \"" + name + "\" already exists";
        } else {
            CounterData.create(name);
            return "Done: \"" + name + "\" successful create";
        }
    }

    @GetMapping("/increment")
    @ResponseBody
    public String increment(@RequestParam String name) {
        if (CounterData.containsKey(name)) {
            CounterData.increment(name);
            return "Done: \"" + name + "\" successfully increased";
        } else {
            return "Failed: \"" + name + "\" does not exist";
        }
    }

    @GetMapping("/get")
    @ResponseBody
    public String get(@RequestParam String name) {
        if (CounterData.containsKey(name)) {
            return name + ": " + CounterData.getForName(name).toString();
        } else {
            return "Failed: \"" + name + "\" does not exist";
        }
    }

    @GetMapping("/remove")
    @ResponseBody
    public String remove(@RequestParam String name) {
        if (CounterData.containsKey(name)) {
            CounterData.remove(name);
            return "Done: \"" + name + "\" successfully deleted";
        } else {
            return "Failed: \"" + name + "\" does not exist";
        }
    }

    @GetMapping("/list")
    @ResponseBody
    public String list() {
        return CounterData.getNames().toString();
    }

    @GetMapping("/sum")
    @ResponseBody
    public String sum(){
        return CounterData.getSum().toString();
    }

}