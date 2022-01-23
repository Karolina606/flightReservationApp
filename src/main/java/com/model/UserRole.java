package com.model;

public enum UserRole {
    ADMIN{
        @Override
        public String toString() {
            return "ADMIN";
        }
    },
    EMPLOYEE{
        @Override
        public String toString() {
            return "EMPLOYEE";
        }
    },
    PASSENGER{
        @Override
        public String toString() {
            return "PASSENGER";
        }
    }
}
