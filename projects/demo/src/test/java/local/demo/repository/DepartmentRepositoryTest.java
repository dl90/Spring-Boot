package local.demo.repository;

import local.demo.entity.Department;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.EntityNotFoundException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        Department foo = Department.builder()
                .departmentName("Foo")
                .departmentCode("F0-1").build();
        testEntityManager.persist(foo);

        Department bar = Department.builder()
                .departmentName("Bar")
                .departmentCode("B0-1").build();
        testEntityManager.persist(bar);
    }


    @Test
    void findById() {
        Department foo = departmentRepository.getById(1L);
        Assertions.assertEquals("Foo", foo.getDepartmentName());
        Department bar = departmentRepository.getById(2L);
        Assertions.assertEquals("Bar", bar.getDepartmentName());
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> Assertions.assertNull(departmentRepository.getById(3L)));
    }
}
