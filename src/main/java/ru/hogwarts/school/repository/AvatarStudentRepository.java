package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.AvatarStudent;

import java.util.Optional;

@Repository
public interface AvatarStudentRepository extends JpaRepository<AvatarStudent, Long> {

    Optional<AvatarStudent> findByStudentId(long id);
}
