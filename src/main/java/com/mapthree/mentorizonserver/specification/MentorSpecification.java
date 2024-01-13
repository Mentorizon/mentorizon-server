package com.mapthree.mentorizonserver.specification;

import com.mapthree.mentorizonserver.model.Domain;
import com.mapthree.mentorizonserver.model.Mentor;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class MentorSpecification {
    public static Specification<Mentor> hasDomain(UUID domainId) {
        return (root, query, criteriaBuilder) -> {
            Join<Mentor, Domain> domains = root.join("domains");
            return criteriaBuilder.equal(domains.get("id"), domainId);
        };
    }

    public static Specification<Mentor> hasYearsOfExperience(Integer yearsOfExperience) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("yearsOfExperience"), yearsOfExperience);
    }

    public static Specification<Mentor> hasRating(Integer rating) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), rating);
    }

    public static Specification<Mentor> isApproved() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isTrue(root.get("isApproved"));
    }
}
