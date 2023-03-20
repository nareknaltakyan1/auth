package com.naltakyan.auth.domain.organization.service;

import com.naltakyan.auth.domain.organization.model.Organization;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Long>
// , QuerydslPredicateExecutor<Organization>
{
	Optional<Organization> findByName(final String name);
}
