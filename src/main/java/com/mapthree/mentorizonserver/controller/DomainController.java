package com.mapthree.mentorizonserver.controller;

import com.mapthree.mentorizonserver.model.Domain;
import com.mapthree.mentorizonserver.service.DomainService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DomainController {

    private final DomainService domainService;

    public DomainController(DomainService domainService) {
        this.domainService = domainService;
    }

    @GetMapping("/domains")
    public ResponseEntity<List<Domain>> getDomains() {
        List<Domain> domainList = domainService.findAllDomains();
        return ResponseEntity.ok(domainList);
    }
}
