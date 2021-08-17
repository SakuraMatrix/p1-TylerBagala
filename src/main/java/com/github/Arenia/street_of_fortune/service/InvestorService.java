package com.github.arenia.street_of_fortune.service;

import com.github.arenia.street_of_fortune.domain.Investor;
import com.github.arenia.street_of_fortune.repository.InvestorRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class InvestorService{
    private InvestorRepository investorRepository;

    public InvestorService(InvestorRepository investorRepository){
        this.investorRepository = investorRepository;
    }

    public Flux<Investor> getAll(){
        return investorRepository.getAll();
    }

    public Mono<Investor> get(String id){
        return investorRepository.get(Integer.parseInt(id));
    }
}