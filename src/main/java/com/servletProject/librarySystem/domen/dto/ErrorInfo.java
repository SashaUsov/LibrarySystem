package com.servletProject.librarySystem.domen.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
@NoArgsConstructor
public class ErrorInfo {
    private String timestamp;
    private String message;
    private String developerMessage;

}
