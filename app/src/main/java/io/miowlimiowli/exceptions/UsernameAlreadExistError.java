package io.miowlimiowli.exceptions;

public class UsernameAlreadExistError extends Exception {
    @Override
    public String toString() {
        return "Username already exists!";
    }
}
