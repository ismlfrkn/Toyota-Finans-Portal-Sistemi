package com.toyota.user_service.dto;

import java.util.List;

public record UserMeResponse(
        String username,
        List<String> roles
) {
}
