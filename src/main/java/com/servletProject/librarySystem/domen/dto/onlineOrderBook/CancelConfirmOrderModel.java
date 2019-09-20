package com.servletProject.librarySystem.domen.dto.onlineOrderBook;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
public class CancelConfirmOrderModel {

    @NotNull
    private Long idCopy;

    @NotNull
    private Long idUser;
}
