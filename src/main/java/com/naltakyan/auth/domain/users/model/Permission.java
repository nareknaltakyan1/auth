package com.naltakyan.auth.domain.users.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum Permission
{
	READ_NON_SENSITIVE(PermissionType.GLOBAL), READ_USERS(PermissionType.GLOBAL), WRITE_USERS(PermissionType.GLOBAL, "READ_USERS"),
	READ_ORGS(PermissionType.GLOBAL), WRITE_ORGS(PermissionType.GLOBAL, "READ_ORGS"), READ_HUBS(PermissionType.GLOBAL),
	WRITE_HUBS(PermissionType.GLOBAL, "READ_HUBS"), READ_QUOTES(PermissionType.GLOBAL), WRITE_QUOTES(PermissionType.GLOBAL, "READ_QUOTES"),
	READ_BOOKINGS(PermissionType.GLOBAL), WRITE_BOOKINGS(PermissionType.GLOBAL, "READ_BOOKINGS"), READ_TRANSCOS(PermissionType.GLOBAL),
	WRITE_TRANSCOS(PermissionType.GLOBAL, "READ_TRANSCOS"), READ_PARTNERS(PermissionType.GLOBAL),
	WRITE_PARTNERS(PermissionType.GLOBAL, "READ_PARTNERS"), READ_INVOICES(PermissionType.GLOBAL),
	WRITE_INVOICES(PermissionType.GLOBAL, "READ_INVOICES"), SEND_EMAIL(PermissionType.GLOBAL), SEND_SMS(PermissionType.GLOBAL),
	VIEW_SENT_EMAIL(PermissionType.GLOBAL), TRIGGER_DATA_SYNC(PermissionType.GLOBAL), MANAGE_DATA_SYNC(PermissionType.GLOBAL, "TRIGGER_DATA_SYNC"),
	GEO_SEARCH(PermissionType.GLOBAL), READ_JOURNEY_OFFERINGS(PermissionType.GLOBAL),
	WRITE_JOURNEY_OFFERINGS(PermissionType.GLOBAL, "READ_JOURNEY_OFFERINGS"), READ_AUDIT_LOGS(PermissionType.GLOBAL),
	WRITE_AUDIT_LOGS(PermissionType.GLOBAL, "READ_AUDIT_LOGS"), IMPERSONATE_USERS(PermissionType.GLOBAL),
	WRITE_PAYMENTS(PermissionType.GLOBAL, "READ_PAYMENTS", "PERFORM_PAYMENTS"), READ_PAYMENTS(PermissionType.GLOBAL),
	PERFORM_PAYMENTS(PermissionType.GLOBAL), CANCEL_JOURNEYS_OUTSIDE_WINDOW(PermissionType.GLOBAL), WRITE_FILES(PermissionType.GLOBAL, "READ_FILES"),
	READ_FILES(PermissionType.GLOBAL), WRITE_CDN_FILES(PermissionType.GLOBAL), CHANGE_JOURNEYS_WITHOUT_PAYMENT(PermissionType.GLOBAL),
	READ_DRIVERS(PermissionType.GLOBAL), WRITE_DRIVERS(PermissionType.GLOBAL, "READ_DRIVERS"), READ_EVENTS(PermissionType.GLOBAL),
	WRITE_EVENTS(PermissionType.GLOBAL, "READ_EVENTS"), SHORTEN_URIS(PermissionType.GLOBAL), CREATE_BOOKINGS(PermissionType.GLOBAL),
	WRITE_COMMENTS(PermissionType.GLOBAL, "READ_COMMENTS"), READ_COMMENTS(PermissionType.GLOBAL), RESEND_EMAIL(PermissionType.GLOBAL),
	WRITE_FLIGHT_NOTIFICATIONS(PermissionType.GLOBAL), WRITE_REVIEWS(PermissionType.GLOBAL), READ_REVIEWS(PermissionType.GLOBAL),
	READ_ACTIONS(PermissionType.GLOBAL), WRITE_CS_TICKETS(PermissionType.GLOBAL), READ_CS_TICKETS(PermissionType.GLOBAL, "READ_CS_TICKETS"),

	CREATE_OWN_BOOKINGS(PermissionType.ORGANIZATIONAL), VIEW_OWN_BOOKINGS(PermissionType.ORGANIZATIONAL),
	ACCEPT_JOURNEYS(PermissionType.ORGANIZATIONAL), PLAN_JOURNEYS(PermissionType.ORGANIZATIONAL), PERFORM_JOURNEYS(PermissionType.ORGANIZATIONAL),
	READ_ASSIGNED_JOURNEYS(PermissionType.ORGANIZATIONAL), READ_OWN_INVOICES(PermissionType.ORGANIZATIONAL),
	READ_COMPANY_DATA(PermissionType.ORGANIZATIONAL), WRITE_COMPANY_DATA(PermissionType.ORGANIZATIONAL, "READ_COMPANY_DATA"),
	CANCEL_JOURNEYS(PermissionType.ORGANIZATIONAL), EDIT_JOURNEYS_MINOR_DETAILS(PermissionType.ORGANIZATIONAL),
	EDIT_JOURNEYS_MAJOR_DETAILS(PermissionType.ORGANIZATIONAL), READ_COMPANY_EVENTS(PermissionType.ORGANIZATIONAL),
	WRITE_COMPANY_EVENTS(PermissionType.ORGANIZATIONAL, "READ_COMPANY_EVENTS"), READ_CORRECTIONS(PermissionType.GLOBAL),
	WRITE_CORRECTIONS(PermissionType.GLOBAL, "READ_CORRECTIONS");

	private final PermissionType type;
	private final Set<String> aliases;

	Permission(final PermissionType roleType)
	{
		this.type = roleType;
		this.aliases = null;
	}

	Permission(final PermissionType roleType, final String... aliases)
	{
		this.type = roleType;
		final Set<String> tmp = new HashSet<>();
		Collections.addAll(tmp, aliases);
		this.aliases = Collections.unmodifiableSet(tmp);
	}

	public PermissionType getType()
	{
		return type;
	}

	public Set<String> getAliases()
	{
		return aliases;
	}

	public boolean hasAliases()
	{
		return aliases != null;
	}
}
