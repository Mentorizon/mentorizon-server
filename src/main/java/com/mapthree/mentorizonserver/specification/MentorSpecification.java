package com.mapthree.mentorizonserver.specification;

import com.mapthree.mentorizonserver.model.Domain;
import com.mapthree.mentorizonserver.model.MentorDetails;
import com.mapthree.mentorizonserver.model.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class MentorSpecification {
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
}
