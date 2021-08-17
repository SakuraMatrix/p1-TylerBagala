package com.github.arenia.street_of_fortune.service;

import com.github.arenia.street_of_fortune.domain.Property;
import com.github.arenia.street_of_fortune.repository.PropertyRepository;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
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