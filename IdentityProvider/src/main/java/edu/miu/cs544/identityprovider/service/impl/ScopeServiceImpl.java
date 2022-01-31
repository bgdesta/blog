package edu.miu.cs544.identityprovider.service.impl;

import edu.miu.cs544.identityprovider.domain.Scope;
import edu.miu.cs544.identityprovider.domain.Scope;
import edu.miu.cs544.identityprovider.domain.User;
import edu.miu.cs544.identityprovider.dto.ScopeDto;
import edu.miu.cs544.identityprovider.repository.ScopeRepository;
import edu.miu.cs544.identityprovider.repository.ScopeRepository;
import edu.miu.cs544.identityprovider.service.ScopeService;
import edu.miu.cs544.identityprovider.service.UserSecurityService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@RequiredArgsConstructor
public class ScopeServiceImpl implements ScopeService {
    private final static Logger log = LoggerFactory.getLogger(ScopeServiceImpl.class);

    private final ScopeRepository scopeRepository;
    private final UserSecurityService userSecurityService;
    private final ModelMapper mapper;

    @Override
    public List<ScopeDto> findAll() {
        List<Scope> scopes = scopeRepository.findAll();
        return scopes.stream().map(scope -> mapper.map(scope, ScopeDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ScopeDto> findAll(Pageable pageable) {
        Page<Scope> scopes = scopeRepository.findAll(pageable);
        return scopes.stream().map(scope -> mapper.map(scope, ScopeDto.class)).collect(Collectors.toList());
    }

    @Override
    public ScopeDto findById(Long id) {
        Scope scope = scopeRepository.findById(id).orElseThrow();
        return mapper.map(scope, ScopeDto.class);
    }

    @Override
    public ScopeDto save(ScopeDto dto) {
        User user = userSecurityService.currentUser();

        Scope scope = mapper.map(dto, Scope.class);
        scope.setCreatedAt(LocalDateTime.now());
        scope.setCreatedBy(user);

        scopeRepository.save(scope);
        log.info("New scope created: " + scope);
        return mapper.map(scope, ScopeDto.class);
    }

    @Override
    public void update(Long id, ScopeDto dto) {
        Scope scopeDb = scopeRepository.findById(id).orElseThrow();
        Scope scope = mapper.map(dto, Scope.class);
        mapper.map(scope, scopeDb);
        scopeRepository.save(scopeDb);
        log.info("Scope updated: " + scope);
    }

    @Override
    public void delete(Long id) {
        scopeRepository.deleteById(id);
        log.info("Scope deleted id: " + id);
    }
}
