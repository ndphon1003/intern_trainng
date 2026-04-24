package com.trainng.user_service.middlewares;

import java.util.Set;

public class RoleValidate {

    private static final Set<String> VALID_ROLES = Set.of(
            "CUSTOMER",
            "ADMIN",
            "MANAGER"
    );

    public static ValidateResponse validateRole(String role) {

        if (role == null || role.isBlank()) {
            return new ValidateResponse(400, "Role cannot be null or empty");
        }

        if (!VALID_ROLES.contains(role)) {
            return new ValidateResponse(
                    400,
                    "Invalid role. Must be CUSTOMER, ADMIN, or MANAGER"
            );
        }

        return new ValidateResponse(200, "Valid role");
    }
}