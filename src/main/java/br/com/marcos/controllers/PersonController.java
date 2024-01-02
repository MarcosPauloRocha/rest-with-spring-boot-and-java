package br.com.marcos.controllers;

import br.com.marcos.data.vo.v1.PersonVO;
import br.com.marcos.services.PersonServices;
import br.com.marcos.util.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

    @Autowired
    private PersonServices service;

    @GetMapping(
        produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public List<PersonVO> findAll(){
        return service.findAll();
    }

    @GetMapping(
        value = "/{id}",
        produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public PersonVO findById(@PathVariable(value = "id") Long id){
        return service.findById(id);
    }

    @PostMapping(
        produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
        consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public PersonVO create(@RequestBody PersonVO person){
        return service.create(person);
    }

    @PutMapping(
        produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
        consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public PersonVO update(@RequestBody PersonVO person){
        return service.update(person);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
