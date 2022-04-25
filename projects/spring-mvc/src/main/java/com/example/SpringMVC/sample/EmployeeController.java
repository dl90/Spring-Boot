package com.example.SpringMVC.sample;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sample")
public class EmployeeController {

    private static final Map<Long, Employee> db = new HashMap<>();

    static {
        db.put(1L, new Employee(1L, "Foo", "a"));
        db.put(2L, new Employee(2L, "Bar", "b"));
        db.put(3L, new Employee(3L, "Baz", "c"));
    }

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public ResponseEntity<Object> paramEmployee(@RequestParam(defaultValue = "1") long id) {

        Employee e = db.get(id);
        ResponseEntity<Object> res;

        res = (e != null
                ? new ResponseEntity<>(e, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND));

        return res;
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST, consumes = "application/json")
    public Employee createEmployee(@RequestBody Employee employee) {
        db.put(employee.getId(), employee);
        return employee;
    }

    @GetMapping("/employee/{id}")
    public Employee idEmployee(@PathVariable(name = "id") long id) {
        return db.get(id);
    }

}
