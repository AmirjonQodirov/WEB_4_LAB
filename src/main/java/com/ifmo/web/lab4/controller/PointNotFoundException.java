package com.ifmo.web.lab4.controller;

public class PointNotFoundException extends RuntimeException {
    PointNotFoundException(Long id) {
        super("Could not find point " + id);
    }
}
