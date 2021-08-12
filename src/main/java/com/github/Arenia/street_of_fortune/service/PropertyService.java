package com.github.Arenia.street_of_fortune.service;

import com.github.Arenia.street_of_fortune.domain.Property;
import com.github.Arenia.street_of_fortune.repository.PropertyRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PropertyService{
    private PropertyRepository PropertyRepository;

    public PropertyService(PropertyRepository PropertyRepository){
        this.PropertyRepository = PropertyRepository;
    }

    public Flux<Property> getAll(){
        return PropertyRepository.getAll();
    }

    public Mono<Property> get(String id){
        return PropertyRepository.get(Integer.parseInt(id));
    }
}