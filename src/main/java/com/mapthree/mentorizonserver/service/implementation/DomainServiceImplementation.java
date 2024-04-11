package com.mapthree.mentorizonserver.service.implementation;

import com.mapthree.mentorizonserver.model.Domain;
import com.mapthree.mentorizonserver.repository.DomainRepository;
import com.mapthree.mentorizonserver.service.DomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomainServiceImplementation implements DomainService {
    private final DomainRepository domainRepository;

    public DomainServiceImplementation(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }

    @Override
    public List<Domain> findAllDomains() {
        return domainRepository.findAll();
    }
}
