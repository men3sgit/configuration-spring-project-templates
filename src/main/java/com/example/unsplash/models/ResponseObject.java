package com.example.unsplash.models;

public record ResponseObject(Integer code,
                             String message,
                             Object data) {}
