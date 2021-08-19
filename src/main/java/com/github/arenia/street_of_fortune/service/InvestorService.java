package com.github.arenia.street_of_fortune.service;

import java.util.Map;

import com.github.arenia.street_of_fortune.domain.Investor;
import com.github.arenia.street_of_fortune.repository.InvestorRepository;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
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

    public Investor newInvestor(Investor investor){
        return investorRepository.create(investor);
    }

    public Mono<Integer> deleteInvestor(String id){
        return investorRepository.deleteInvestor(Integer.parseInt(id));
    }

    public Double updateWorth(Map<String, String> params) {
        return investorRepository.updateWorth(Integer.parseInt(params.get("id")), Double.parseDouble(params.get("newWorth")));
    }
}