package com.servletProject.librarySystem.domen.dto.onlineOrderBook;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateOnlineOrderBookModel {

    @NotNull
    @NotEmpty
    private long idUser;

    @NotNull
    @NotEmpty
    private long idBookCopy;
}
