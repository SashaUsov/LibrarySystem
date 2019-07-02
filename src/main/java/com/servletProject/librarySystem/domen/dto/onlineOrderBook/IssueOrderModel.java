package com.servletProject.librarySystem.domen.dto.onlineOrderBook;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class IssueOrderModel {

    @NotNull
    private Long idCopy;

    @NotNull
    private Long idReader;

    @NotNull
    private Long idLibrarian;
}
