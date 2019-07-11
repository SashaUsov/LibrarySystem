package com.servletProject.librarySystem.domen.dto.onlineOrderBook;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
public class CreateCancelOrderModel {

    @NotNull
    private Long idCopy;

    @NotNull
    @NotEmpty
    private String nickName;
}
