package local.demo.error;

import javax.persistence.EntityNotFoundException;

public class DepartmentNotFound extends EntityNotFoundException {

    public DepartmentNotFound(Long id) {
        super("department not found: id=%d".formatted(id));
    }

    public DepartmentNotFound(String name) {
        super("department not found: name=%s".formatted(name));
    }
}
