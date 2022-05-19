package local.demo.service;

import local.demo.entity.Department;
import local.demo.error.DepartmentNotFound;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DepartmentService {

    Department saveDepartment(Department department);

    List<Department> findAllDepartments();

    Department findOne(Long id) throws DepartmentNotFound;

    ResponseEntity<Void> delete(Long id);

    Department updateOne(Long id, Department department);

    Department findByDepartmentName(String name);
}
