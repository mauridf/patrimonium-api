package br.com.patrimonium.person.service;

import br.com.patrimonium.person.dto.*;
import br.com.patrimonium.person.entity.PersonEntity;
import br.com.patrimonium.person.repository.PersonRepository;
import br.com.patrimonium.user.entity.UserEntity;
import br.com.patrimonium.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final UserRepository userRepository;

    private UserEntity getAuthenticatedUser() {
        var email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow();
    }

    public PersonResponse create(PersonCreateRequest request) {

        var user = getAuthenticatedUser();

        var person = PersonEntity.builder()
                .id(UUID.randomUUID())
                .user(user)
                .type(request.type())
                .fullName(request.fullName())
                .document(request.document())
                .email(request.email())
                .phone(request.phone())
                .birthDate(request.birthDate())
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        personRepository.save(person);

        return toResponse(person);
    }

    public PersonResponse findById(UUID id) {

        var user = getAuthenticatedUser();

        var person = personRepository.findById(id)
                .orElseThrow();

        if (!person.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        return toResponse(person);
    }

    public PersonResponse update(UUID id, PersonCreateRequest request) {

        var user = getAuthenticatedUser();

        var person = personRepository.findById(id)
                .orElseThrow();

        if (!person.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        person.setType(request.type());
        person.setFullName(request.fullName());
        person.setDocument(request.document());
        person.setEmail(request.email());
        person.setPhone(request.phone());
        person.setBirthDate(request.birthDate());
        person.setUpdatedAt(LocalDateTime.now());

        personRepository.save(person);

        return toResponse(person);
    }

    public void delete(UUID id) {

        var user = getAuthenticatedUser();

        var person = personRepository.findById(id)
                .orElseThrow();

        if (!person.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        person.setActive(false);
        person.setUpdatedAt(LocalDateTime.now());

        personRepository.save(person);
    }

    private PersonResponse toResponse(PersonEntity person) {
        return new PersonResponse(
                person.getId(),
                person.getType(),
                person.getFullName(),
                person.getDocument(),
                person.getEmail(),
                person.getPhone(),
                person.getBirthDate(),
                person.getActive()
        );
    }
}