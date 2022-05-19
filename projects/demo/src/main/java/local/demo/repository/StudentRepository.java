package local.demo.repository;

import local.demo.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByEmail(String email);

    List<Student> findByFirstNameIgnoreCase(String firstName);

    List<Student> findByFirstNameContainingIgnoreCase(String firstNameContaining);

    Page<Student> findByFirstNameContainingIgnoreCase(String firstName, Pageable pageable);

    List<Student> findByLastNameNotNull();

    List<Student> findByGuardianNameIgnoreCase(String guardianName);

    // JPQL query based on class, not table
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> getStudentByEmailJpql(String email);

    @Query("SELECT s.email FROM Student s")
    Set<String> getAllStudentEmailsJpql();

    @Query(nativeQuery = true,
            value = "SELECT * FROM " + Student.TABLE_NAME + " s WHERE s.guardian_name = ?1")
    List<Student> getStudentsByGuardianFirstAndLastNameNative(String guardianName);

    // native query assumed case-insensitive, check db SHOW VARIABLES LIKE '%collation%';
    @Query(nativeQuery = true,
            value = "SELECT * FROM " + Student.TABLE_NAME + " s WHERE s.email = :email")
    Optional<Student> getStudentByEmailNative(@Param("email") String email);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "UPDATE " + Student.TABLE_NAME
                    + " s SET s.guardian_phone_number = :phoneNumber WHERE s.email = :email")
    int updateGuardianPhoneNumberByStudentEmail(
            @Param("email") String email, @Param("phoneNumber") String phoneNumber);

}
