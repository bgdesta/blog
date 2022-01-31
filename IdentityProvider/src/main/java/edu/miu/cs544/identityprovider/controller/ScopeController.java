package edu.miu.cs544.identityprovider.controller;

import edu.miu.cs544.identityprovider.dto.ScopeDto;
import edu.miu.cs544.identityprovider.service.ScopeService;
import edu.miu.cs544.identityprovider.service.impl.ScopeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scopes")
@RequiredArgsConstructor
public class ScopeController {
    private final static Logger log = LoggerFactory.getLogger(ScopeController.class);

    private final ScopeService scopeService;

    @GetMapping("/")
    public ResponseEntity<List<ScopeDto>> getAll() {
        return ResponseEntity.ok(scopeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScopeDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(scopeService.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<ScopeDto> create(@RequestBody ScopeDto scopeDto) {
        log.info("Scope dto received: " + scopeDto);
        return ResponseEntity.ok(scopeService.save(scopeDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody ScopeDto scopeDto) {
        scopeService.update(id, scopeDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        scopeService.delete(id);
        return ResponseEntity.ok().build();
    }

}
