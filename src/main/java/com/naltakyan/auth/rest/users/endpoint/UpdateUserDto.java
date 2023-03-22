package com.naltakyan.auth.rest.users.endpoint;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateUserDto {
    String email;
    String phoneNumber;
    String tgUsername;
    String pin;
    String question;
}
