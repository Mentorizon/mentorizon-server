package com.mapthree.mentorizonserver.specification;

import com.mapthree.mentorizonserver.model.*;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class MentorSpecification {
    public static Specification<User> isMentor() {
        return (root, query, criteriaBuilder) -> {
            Join<User, Role> rolesJoin = root.join("roles");
            return criteriaBuilder.equal(rolesJoin.get("name"), RoleName.ROLE_MENTOR);
        };
    }

    public static Specification<User> hasDomain(UUID domainId) {
        return (root, query, criteriaBuilder) -> {
            if (root.get("mentorDetails") == null)
                return criteriaBuilder.disjunction();
            Join<MentorDetails, Domain> domainJoin = root.join("mentorDetails").join("domains");
            return criteriaBuilder.equal(domainJoin.get("id"), domainId);
        };
    }

    public static Specification<User> hasYearsOfExperience(Integer yearsOfExperience) {
        return (root, query, criteriaBuilder) -> {
            if (root.get("mentorDetails") == null)
                return criteriaBuilder.disjunction();
            return criteriaBuilder.greaterThanOrEqualTo(root.get("mentorDetails").get("yearsOfExperience"), yearsOfExperience);
        };
    }

    public static Specification<User> hasRating(Integer rating) {
        return (root, query, criteriaBuilder) -> {
            if (root.get("mentorDetails") == null)
                return criteriaBuilder.disjunction();
            return criteriaBuilder.greaterThanOrEqualTo(root.get("mentorDetails").get("rating"), rating);
        };
    }

    public static Specification<User> isApproved() {
        return (root, query, criteriaBuilder) -> {
            if (root.get("mentorDetails") == null)
                return criteriaBuilder.disjunction();
            return criteriaBuilder.isTrue(root.get("mentorDetails").get("isApproved"));
        };
    }

    public static Specification<User> isNotApproved() {
        return (root, query, criteriaBuilder) -> {
            if (root.get("mentorDetails") == null)
                return criteriaBuilder.disjunction();
            return criteriaBuilder.isFalse(root.get("mentorDetails").get("isApproved"));
        };
    }

}
