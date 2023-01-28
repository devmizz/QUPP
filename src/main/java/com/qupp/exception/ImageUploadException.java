package com.qupp.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ImageUploadException extends RuntimeException {
    private final String msg;
}