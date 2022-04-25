package local.restweb.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/property")
public class PropertyController {

    @Value("${custom-property: default}")
    private String customProperty;

    @Value("${custom-property-extended: ${custom-property} value}")
    private String customPropertyExtended;

    @Autowired
    private Greeting greeting;

//    public PropertyController(Greeting greeting) {
//        this.greeting = greeting;
//    }

    @GetMapping()
    public String getCustomProperty(
            @RequestParam(required = false, defaultValue = "false") Boolean extended) {
        return extended ? this.customPropertyExtended : this.customProperty;
    }

    @GetMapping("/greeting")
    public Greeting getGreeting() {
        return this.greeting;
    }
}
