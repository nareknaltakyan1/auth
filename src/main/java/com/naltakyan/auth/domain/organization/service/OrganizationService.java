package com.naltakyan.auth.domain.organization.service;

import com.naltakyan.auth.domain.organization.model.GetAllOrganizationsDetails;
import com.naltakyan.auth.domain.organization.model.MutateOrganizationDetails;
import com.naltakyan.auth.domain.organization.model.Organization;
import com.naltakyan.auth.domain.system.service.SystemDateTimeService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationService
{
	private static final String LIKE_PATTERN = "%%%s%%";

	private final OrganizationRepository repository;

	private final SystemDateTimeService systemDateTimeService;

	// @Transactional(readOnly = true)
	// public Page<Organization> findAll(final GetAllOrganizationsDetails getAllOrganizationsDetails,
	// final Pageable pageable)
	// {
	// final Predicate filter = buildFilter(getAllOrganizationsDetails);
	// return repository.findAll(filter, pageable);
	// }

	@Transactional
	public Organization createOrganization(final MutateOrganizationDetails details)
	{
		final Organization o = new Organization();
		o.setName(details.getName());
		o.setCreated(systemDateTimeService.getCurrentDateTime());
		o.setUpdated(systemDateTimeService.getCurrentDateTime());
		repository.save(o);
		return o;
	}

	@Transactional
	public void updateOrganization(final Long id, final MutateOrganizationDetails details)
	{
		final Organization org = findById(id);
		org.setName(details.getName());
		org.setUpdated(systemDateTimeService.getCurrentDateTime());
		repository.save(org);
	}

	@Transactional(readOnly = true)
	public Organization findById(final Long id)
	{
		final Optional<Organization> org = repository.findById(id);
		if (org.isEmpty())
		{
			throw new EntityNotFoundException(String.format("Could not find organization with id '%d'", id));
		}
		return org.get();
	}

	@Transactional(readOnly = true)
	public Organization findByName(final String name)
	{
		final Optional<Organization> org = repository.findByName(name);
		if (org.isEmpty())
		{
			throw new EntityNotFoundException(String.format("Could not find organization with name '%s'", name));
		}
		return org.get();
	}

	private Predicate buildFilter(final GetAllOrganizationsDetails getAllUsersPojo)
	{
		Assert.notNull(getAllUsersPojo, "Argument getAllUsersPojo should not be null");
		final BooleanBuilder filter = new BooleanBuilder();
		// Optional.ofNullable(getAllUsersPojo.getName()).filter(StringUtils::hasText)
		// .ifPresent(name ->
		// filter.and(QOrganization.organization.name.likeIgnoreCase(String.format(LIKE_PATTERN, name))));
		// Optional.ofNullable(getAllUsersPojo.getCreatedFrom())
		// .ifPresent(createdFrom -> filter.and(QOrganization.organization.created.goe(createdFrom)));
		// Optional.ofNullable(getAllUsersPojo.getCreatedTo()).ifPresent(createdTo ->
		// filter.and(QOrganization.organization.created.loe(createdTo)));
		// Optional.ofNullable(getAllUsersPojo.getUpdatedAfter())
		// .ifPresent(updatedAfter -> filter.and(QOrganization.organization.created.gt(updatedAfter)));
		return filter;
	}
}
