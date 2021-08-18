package com.github.arenia.street_of_fortune.service;

import com.github.arenia.street_of_fortune.domain.Land;
import com.github.arenia.street_of_fortune.repository.LandRepository;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LandService{
    private LandRepository landRepository;

    public LandService(LandRepository landRepository){
        this.landRepository = landRepository;
    }

    public Flux<Land> getAll(){
        return landRepository.getAll();
    }

    public Mono<Land> get(String id){
        return landRepository.get(Integer.parseInt(id));
    }

    public Land newLand(Land land){
        return landRepository.create(land);
    }
}