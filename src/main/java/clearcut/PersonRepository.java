package clearcut;

import org.springframework.data.repository.CrudRepository;

import clearcut.Person;

// This will be AUTO IMPLEMENTED by Spring into a Bean called personRepository
// CRUD refers Create, Read, Update, Delete

public interface PersonRepository extends CrudRepository<Person, Integer> {

}
