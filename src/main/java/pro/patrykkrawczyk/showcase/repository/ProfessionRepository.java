package pro.patrykkrawczyk.showcase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.patrykkrawczyk.showcase.domain.Profession;

@Repository
public interface ProfessionRepository extends JpaRepository<Profession, Long> {

    long countAllByIdIn(Iterable<Long> ids);
}
