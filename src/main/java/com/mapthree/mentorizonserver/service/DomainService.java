package com.mapthree.mentorizonserver.service;

import com.mapthree.mentorizonserver.model.Domain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DomainService {
    List<Domain> findAllDomains();
}
