package io.miowlimiowli.exceptions;

public class UsernameEmpty extends Exception {
    @Override
    public String toString() {
        return "Username should not be empty!";
    }
}
