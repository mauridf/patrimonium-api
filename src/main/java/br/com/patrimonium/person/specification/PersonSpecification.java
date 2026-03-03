package br.com.patrimonium.person.specification;

import br.com.patrimonium.person.entity.PersonEntity;
import br.com.patrimonium.person.enums.PersonType;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class PersonSpecification {

    public static Specification<PersonEntity> belongsToUser(UUID userId) {
        return (root, query, cb) ->
                cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<PersonEntity> hasType(PersonType type) {
        return (root, query, cb) ->
                type == null ? null :
                        cb.equal(root.get("type"), type);
    }

    public static Specification<PersonEntity> hasName(String name) {
        return (root, query, cb) ->
                name == null ? null :
                        cb.like(
                                cb.lower(root.get("fullName")),
                                "%" + name.toLowerCase() + "%"
                        );
    }

    public static Specification<PersonEntity> isActive(Boolean active) {
        return (root, query, cb) ->
                active == null ? null :
                        cb.equal(root.get("active"), active);
    }
}