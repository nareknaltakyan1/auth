package com.naltakyan.auth.domain.logintypes.service;

import com.naltakyan.auth.domain.logintypes.model.LoginType;
import com.naltakyan.auth.domain.logintypes.model.UserLoginTypes;
import com.naltakyan.auth.domain.system.service.SystemDateTimeService;
import com.naltakyan.auth.domain.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserLoginTypesService
{

	private final UserLoginTypesRepository repository;

	private final SystemDateTimeService systemDateTimeService;

	@Transactional
	public void create(final User user, final List<LoginType> loginTypeList, final String pin, final String question)
	{
		for (int i = 0; i < loginTypeList.size(); i++)
		{
			var userLoginType = new UserLoginTypes();
			var loginType = loginTypeList.get(i);
			userLoginType.setUser(user);
			userLoginType.setLoginType(loginType);
			if (loginType.isStatic())
			{
				if (loginType.equals(LoginType.PIN))
				{
					userLoginType.setValue(pin);
				}
				else
				{
					userLoginType.setValue(question);
				}
			}
			userLoginType.setPriority((long) i);
			repository.save(userLoginType);
		}
	}

	@Transactional
	public void updateValue(final String verificationCode, final User user, final LoginType loginType)
	{
		var userLoginTypeOpt = repository.findUserLoginTypesByUserAndLoginType(user, loginType);
		if (userLoginTypeOpt.isPresent())
		{
			var userLoginType = userLoginTypeOpt.get();
			userLoginType.setValue(verificationCode);
			userLoginType.setSetValueDate(systemDateTimeService.getCurrentDateTime());
		}
	}

	@Transactional
	public boolean checkVerificationCode(final String code, final User user, final LoginType email)
	{
		var userLoginTypeOpt = repository.findUserLoginTypesByUserAndLoginTypeAndValue(user, email, code);
		if (userLoginTypeOpt.isPresent())
		{
			var authLoginType = userLoginTypeOpt.get();
			var dateTime = systemDateTimeService.getCurrentDateTime();
			if (Objects.isNull(email.getExpirationMinutes()))
			{
				authLoginType.setLastLogin(dateTime);
				return true;
			}
			Duration duration = Duration.between(authLoginType.getSetValueDate(), dateTime);
			if (duration.toMinutes() <= email.getExpirationMinutes())
			{
				authLoginType.setLastLogin(dateTime);
			}
		}
		return false;
	}

	@Transactional
	public void updateStaticLogins(final User user, final String value, final LoginType loginType) {
		var userLoginTypeOpt = repository.findUserLoginTypesByUserAndLoginType(user, loginType);
		if (userLoginTypeOpt.isPresent()){
			var userLoginType = userLoginTypeOpt.get();
			userLoginType.setValue(value);
			userLoginType.setSetValueDate(systemDateTimeService.getCurrentDateTime());
			repository.save(userLoginType);
		}
	}

	public boolean checkUserLoginFlow(final User user)
	{
		var userLoginTypes = repository.getUserLoginTypesByUserOrderByPriority(user);
		return checkLoginFlowOrder(userLoginTypes);
	}

	private boolean checkLoginFlowOrder(List<UserLoginTypes> userLoginTypes)
	{
		for (int i = 1; i < userLoginTypes.size(); i++)
		{
			UserLoginTypes prev = userLoginTypes.get(i - 1);
			UserLoginTypes curr = userLoginTypes.get(i);
			if (prev.getLastLogin().isAfter(curr.getLastLogin()))
			{
				return false;
			}
		}
		return true;
	}
}
