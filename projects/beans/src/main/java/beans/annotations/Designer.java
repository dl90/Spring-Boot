package beans.annotations;

import org.springframework.stereotype.Component;

@Component
public class Designer {

    private String name;

    public Designer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Designer{" + "name='" + name + '\'' + '}';
    }
}
