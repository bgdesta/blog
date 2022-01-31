package edu.miu.cs544.identityprovider.service;

import edu.miu.cs544.identityprovider.domain.Scope;
import edu.miu.cs544.identityprovider.dto.ScopeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ScopeService {
    List<ScopeDto> findAll();
    List<ScopeDto> findAll(Pageable pageable);
    ScopeDto findById(Long id);
    ScopeDto save(ScopeDto dto);
    void update(Long id, ScopeDto dto);
    void delete(Long id);
}
