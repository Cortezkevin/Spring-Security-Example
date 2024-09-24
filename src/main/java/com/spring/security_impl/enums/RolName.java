package com.spring.security_impl.enums;

public enum RolName {
    ROLE_ADMIN {
        @Override
        public String toString() {
            return "Role Admin";
        }
    },
    ROLE_USER {
        @Override
        public String toString() {
            return "Role User";
        }
    }
}
