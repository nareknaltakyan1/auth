package com.naltakyan.auth.domain.logintypes.service;

import com.naltakyan.auth.domain.logintypes.model.LoginType;
import com.naltakyan.auth.domain.logintypes.model.UserLoginTypes;
import com.naltakyan.auth.domain.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLoginTypesRepository extends JpaRepository<UserLoginTypes, Long>
{
	Optional<UserLoginTypes> findUserLoginTypesByUserAndLoginTypeAndValue(final User user, final LoginType loginType, final String value);

	Optional<UserLoginTypes> findUserLoginTypesByUserAndLoginType(final User user, final LoginType loginType);

	List<UserLoginTypes> getUserLoginTypesByUserOrderByPriority(final User user);
}
