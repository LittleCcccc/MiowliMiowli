package io.miowlimiowli.exceptions;

public class UsernameEmptyError extends Exception {
    @Override
    public String toString() {
        return "Username should not be empty!";
    }
}
