package local.demo.service;

import local.demo.entity.Department;
import local.demo.error.DepartmentNotFound;
import local.demo.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Override
    public Department saveDepartment(Department department) {
        Department saved = departmentRepository.save(department);
        logger.info("%s new department: %d %s".formatted(
                LocalDateTime.now(), saved.getDepartmentId(), saved.getDepartmentName()));
        return saved;
    }

    @Override
    public List<Department> findAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findOne(Long id) throws DepartmentNotFound {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFound(id));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        departmentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public Department updateOne(Long id, Department department) throws DepartmentNotFound {
        Department existing = findOne(id);
        if (stringCheck(department.getDepartmentName())) {
            existing.setDepartmentName(department.getDepartmentName());
        }
        if (stringCheck(department.getDepartmentName())) {
            existing.setDepartmentCode(department.getDepartmentCode());
        }
        return saveDepartment(department);
    }

    @Override
    public Department findByDepartmentName(String name) {
        return departmentRepository.findByDepartmentNameIgnoreCase(name)
                .orElseThrow(() -> new DepartmentNotFound(name));
    }

    private static boolean stringCheck(String string) {
        return string != null && !string.isBlank();
    }
}
