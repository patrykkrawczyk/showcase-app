package pro.patrykkrawczyk.showcase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pro.patrykkrawczyk.showcase.domain.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("select person from Person person left join fetch person.professions where person.id =:id")
    Optional<Person> findOneWithEagerRelationships(@Param("id") Long id);
}
