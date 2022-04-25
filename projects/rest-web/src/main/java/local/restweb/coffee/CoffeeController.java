package local.restweb.coffee;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/coffee")
public class CoffeeController {

    private final CoffeeRepository coffeeRepository;

    public CoffeeController(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    @GetMapping()
    public Iterable<Coffee> getCoffees() {
        return this.coffeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Coffee> getCoffeeById(@PathVariable String id) {
        return this.coffeeRepository.findById(id);
    }

    @PostMapping()
    public Coffee postCoffee(@RequestBody Coffee coffee) {
        return this.coffeeRepository.save(coffee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee) {
        return (this.coffeeRepository.existsById(id)
                ? new ResponseEntity<>(this.coffeeRepository.save(coffee), HttpStatus.OK)
                : new ResponseEntity<>(this.coffeeRepository.save(coffee), HttpStatus.CREATED));
    }

    @DeleteMapping("/{id}")
    public void deleteCoffee(@PathVariable String id) {
        this.coffeeRepository.deleteById(id);
    }

}
