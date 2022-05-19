package local.demo.repository;

import local.demo.entity.Guardian;
import local.demo.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@SpringBootTest // want to store data, ddl-auto set to create, db drops table every run
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private static List<Student> students;
    private static List<Guardian> guardians;

    @BeforeAll
    static void init(@Autowired StudentRepository studentRepository) {
        students = new ArrayList<>(List.of(
                Student.builder().email("foo@test.com").firstName("Foo").lastName("Foo").build(),
                Student.builder().email("bar@test.com").firstName("Bar").lastName("Bar").build(),
                Student.builder().email("baz@test.com").firstName("Baz").lastName("Baz").build(),
                Student.builder().email("foo2@test.com").firstName("Foo").build()));
        guardians = new ArrayList<>(List.of(
                new Guardian("Foobo", "foobo@test.com", "111-111-1111"),
                new Guardian("Barbo", "barbo@test.com", "111-111-2222"),
                new Guardian("Bazbo", "bazbo@test.com", "111-111-3333")
        ));
        for(int i = 0, len = Math.min(students.size(), guardians.size()); i < len; i++) {
            students.get(i).setGuardian(guardians.get(i));
        }
        studentRepository.saveAll(students);
    }

    @Test
    void saveStudent() {
        Student student = Student.builder().email("test@test.com").firstName("test").build();
        studentRepository.save(student);
        Student found = studentRepository.findOne(Example.of(student)).orElseThrow();
        Assertions.assertNotNull(found);
        Assertions.assertEquals(student.getEmail(), found.getEmail());
        studentRepository.delete(student);
    }

    @Test
    void findAllStudents() {
        List<Student> result = studentRepository.findAll();
        Assertions.assertIterableEquals(students, result);
    }

    @Test
    void findByFirstNameIgnoreCase() {
        List<Student> result = studentRepository
                .findByFirstNameIgnoreCase(students.get(0).getFirstName().toUpperCase(Locale.ROOT));
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void findByFirstNameContainingIgnoreCase() {
        List<Student> result = studentRepository.findByFirstNameContainingIgnoreCase("oo");
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void findByLastNameNotNull() {
        List<Student> result = studentRepository.findByLastNameNotNull();
        Assertions.assertEquals(3, result.size());
    }

    @Test
    void findByGuardianNameIgnoreCase() {
        List<Student> results = studentRepository
                .findByGuardianNameIgnoreCase(guardians.get(0).getName().toUpperCase(Locale.ROOT));
        Assertions.assertEquals(1, results.size());
    }

    @Test
    void getByEmailJpql() {
        Optional<Student> fooOptional = studentRepository.getStudentByEmailJpql(students.get(0).getEmail());
        Assertions.assertTrue(fooOptional.isPresent());
        Assertions.assertEquals(students.get(0), fooOptional.get());
    }

    @Test
    void getAllStudentEmailsJpql() {
        Set<String> emails = studentRepository.getAllStudentEmailsJpql();
        Assertions.assertEquals(students.size(), emails.size());
    }

    @Test
    void getStudentsByGuardianFirstAndLastNameNative() {
        List<Student> result = studentRepository
                .getStudentsByGuardianFirstAndLastNameNative(guardians.get(0).getName());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(students.get(0), result.get(0));
    }

    @Test
    void getStudentByEmailNative() {
        Optional<Student> barOptional = studentRepository.getStudentByEmailNative(students.get(1).getEmail());
        Assertions.assertTrue(barOptional.isPresent());
        Assertions.assertEquals(students.get(1), barOptional.get());
    }

    @Test
    void updateGuardianPhoneNumberByStudentEmail() {
        String newPhoneNumber = "999-999-9999";
        int rowsChanged = studentRepository.updateGuardianPhoneNumberByStudentEmail(
                students.get(0).getEmail(), newPhoneNumber);
        Assertions.assertEquals(1, rowsChanged);

        Student student = studentRepository.findByEmail(students.get(0).getEmail());
        Assertions.assertEquals(newPhoneNumber, student.getGuardian().getPhoneNumber());
    }

    @Test
    void getAllStudentsPaginate() {
        Pageable pg1 = PageRequest.of(0, 2);
        Pageable pg2 = PageRequest.of(1, 3);

        Page<Student> pg1Page = studentRepository.findAll(pg1);
        Page<Student> pg2Page = studentRepository.findAll(pg2);

        List<Student> pg1Results = pg1Page.getContent();
        Assertions.assertEquals(2, pg1Results.size());
        Assertions.assertIterableEquals(students.subList(0, 2), pg1Results);

        List<Student> pg2Results = pg2Page.getContent();
        Assertions.assertEquals(1, pg2Results.size());
        Assertions.assertIterableEquals(students.subList(3, 4), pg2Results);

        long pg1TotalElements = pg1Page.getTotalElements();
        int pg1TotalPages = pg1Page.getTotalPages();
        Assertions.assertEquals(students.size(), pg1TotalElements);
        Assertions.assertEquals(Math.ceil(students.size() / 2.0), pg1TotalPages);

        int pg2TotalPages = pg2Page.getTotalPages();
        Assertions.assertEquals(Math.ceil(students.size() / 3.0), pg2TotalPages);
    }

    @Test
    void getAllStudentsPaginateSorted() {
        Pageable sortByEmailId = PageRequest.of(0, 2,
                Sort.by("email").and(Sort.by("studentId")).ascending());

        List<Student> sortByEmailIdResults = studentRepository.findAll(sortByEmailId).getContent();
        Assertions.assertEquals(2, sortByEmailIdResults.size());
        Assertions.assertIterableEquals(List.of(students.get(1), students.get(2)), sortByEmailIdResults);
    }

    @Test
    void findByFirstNameContainingIgnoreCasePage() {
        Page<Student> page = studentRepository.findByFirstNameContainingIgnoreCase("ba",
                PageRequest.of(0, 1));
        List<Student> result = page.getContent();
        Assertions.assertEquals(1, result.size());
    }
}
