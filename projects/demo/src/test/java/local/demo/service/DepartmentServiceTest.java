package local.demo.service;

import local.demo.entity.Department;
import local.demo.error.DepartmentNotFound;
import local.demo.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;

    private Department foo;
    private Department bar;

    @BeforeEach
    void setUp() {
        foo = new Department(1L, "Foo", "FO-1");
        bar = new Department(2L, "Bar", "BO-1");
        Mockito.when(departmentRepository.findByDepartmentNameIgnoreCase("foo"))
                .thenReturn(java.util.Optional.of(foo));
        Mockito.when(departmentRepository.findByDepartmentNameIgnoreCase("bar"))
                .thenReturn(java.util.Optional.of(bar));
        Mockito.when(departmentRepository.findByDepartmentNameIgnoreCase("foobar"))
                .thenThrow(new DepartmentNotFound("foobar"));
    }

    @Test
    @DisplayName("Get department by name")
    void findByDepartmentName() {
        assertEquals(foo.getDepartmentName(),
                departmentService.findByDepartmentName("foo").getDepartmentName());
        assertNotEquals(bar.getDepartmentName(),
                departmentService.findByDepartmentName("foo").getDepartmentName());

        Exception ex = assertThrows(DepartmentNotFound.class, () -> departmentService.findByDepartmentName("foobar"));
        assertEquals("department not found: name=foobar", ex.getMessage());
    }
}
