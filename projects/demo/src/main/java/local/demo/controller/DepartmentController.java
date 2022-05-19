package local.demo.controller;

import local.demo.entity.Department;
import local.demo.error.DepartmentNotFound;
import local.demo.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @PostMapping
    public ResponseEntity<Department> saveDepartment(@Valid @RequestBody Department department, HttpServletRequest request) {
        logger.info(request.getRemoteAddr());
        Department saved = departmentService.saveDepartment(department);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(saved);
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.findAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable("id") Long id) throws DepartmentNotFound {
        return departmentService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        return departmentService.delete(id);
    }

    @PutMapping("/{id}")
    public Department updateById(@PathVariable("id") Long id, @RequestBody Department department) {
        return departmentService.updateOne(id, department);
    }

    @GetMapping("/name/{name}")
    public Department getDepartmentByName(@PathVariable("name") String name) {
        return departmentService.findByDepartmentName(name);
    }
}
