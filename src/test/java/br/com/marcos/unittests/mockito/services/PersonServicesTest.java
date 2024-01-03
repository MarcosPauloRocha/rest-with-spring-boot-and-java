package br.com.marcos.unittests.mockito.services;

import br.com.marcos.data.vo.v1.PersonVO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import br.com.marcos.exceptions.RequiredObjectIsNullException;
import br.com.marcos.model.Person;
import br.com.marcos.repositories.PersonRepository;
import br.com.marcos.services.PersonServices;
import br.com.marcos.unittests.mapper.mocks.MockPerson;
import ch.qos.logback.core.net.SyslogOutputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PersonServicesTest {

    MockPerson input;

    @InjectMocks
    private PersonServices services;
    @Mock
    PersonRepository personRepository;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Person person = input.mockEntity(1);

        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        var result = services.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testCreate() {
        Person person = input.mockEntity(1);
        Person persisted = person;
        PersonVO vo = input.mockVO(1);

        when(personRepository.save(person)).thenReturn(persisted);

        var result = services.create(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testUpdate() {
        Person person = input.mockEntity(1);
        Person persisted = person;
        PersonVO vo = input.mockVO(1);

        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(persisted);

        var result = services.update(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Addres Test1", result.getAddress());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testDelete() {
        Person person = input.mockEntity(1);

        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        services.delete(1L);
    }

    @Test
    void testFindAll() {
        List<Person> list = input.mockEntityList();

        when(personRepository.findAll()).thenReturn(list);

        var people = services.findAll();
        assertNotNull(people);
        assertEquals(14,people.size());

        var personOne = people.get(1);
        assertNotNull(personOne);
        assertNotNull(personOne.getKey());
        assertNotNull(personOne.getLinks());
        assertTrue(personOne.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("First Name Test1", personOne.getFirstName());
        assertEquals("Last Name Test1", personOne.getLastName());
        assertEquals("Addres Test1", personOne.getAddress());
        assertEquals("Female", personOne.getGender());

        var personFour = people.get(4);
        assertNotNull(personFour);
        assertNotNull(personFour.getKey());
        assertNotNull(personFour.getLinks());
        assertTrue(personFour.toString().contains("links: [</api/person/v1/4>;rel=\"self\"]"));
        assertEquals("First Name Test4", personFour.getFirstName());
        assertEquals("Last Name Test4", personFour.getLastName());
        assertEquals("Addres Test4", personFour.getAddress());
        assertEquals("Male", personFour.getGender());
    }

    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            services.create(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            services.update(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
