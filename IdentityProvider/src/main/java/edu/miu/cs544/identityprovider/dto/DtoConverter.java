package edu.miu.cs544.identityprovider.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class DtoConverter<D, E> {
    private ModelMapper mapper;

    @Autowired
    public DtoConverter(ModelMapper mp) {
        mapper = mp;
    }
}
