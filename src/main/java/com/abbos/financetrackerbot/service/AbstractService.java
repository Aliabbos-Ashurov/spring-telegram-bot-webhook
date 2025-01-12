package com.abbos.financetrackerbot.service;


import com.abbos.financetrackerbot.mapper.Mapper;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  13:05
 **/
public abstract class AbstractService<R, M extends Mapper> {

    protected final R repository;
    protected final M mapper;

    protected AbstractService(R repository, M mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
}