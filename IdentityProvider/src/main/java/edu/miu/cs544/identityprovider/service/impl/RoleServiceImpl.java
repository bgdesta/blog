package edu.miu.cs544.identityprovider.service.impl;

import edu.miu.cs544.identityprovider.domain.Role;
import edu.miu.cs544.identityprovider.domain.Scope;
import edu.miu.cs544.identityprovider.domain.User;
import edu.miu.cs544.identityprovider.dto.RoleDto;
import edu.miu.cs544.identityprovider.dto.ScopeDto;
import edu.miu.cs544.identityprovider.repository.RoleRepository;
import edu.miu.cs544.identityprovider.repository.ScopeRepository;
import edu.miu.cs544.identityprovider.service.RoleService;
import edu.miu.cs544.identityprovider.service.UserSecurityService;
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
public class RoleServiceImpl implements RoleService {
    private final static Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;
    private final ScopeRepository scopeRepository;
    private final UserSecurityService userSecurityService;
    private final ModelMapper mapper;

    @Override
    public List<RoleDto> findAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(r -> mapper.map(r, RoleDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<RoleDto> findAll(Pageable pageable) {
        Page<Role> roles = roleRepository.findAll(pageable);
        return roles.stream().map(r -> mapper.map(r, RoleDto.class)).collect(Collectors.toList());
    }

    @Override
    public RoleDto findById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow();
        return mapper.map(role, RoleDto.class);
    }

    @Override
    public RoleDto save(RoleDto dto) {
        User user = userSecurityService.currentUser();
        Role role = mapper.map(dto, Role.class);
        role.setCreatedAt(LocalDateTime.now());
        role.setCreatedBy(user);
        roleRepository.save(role);
        log.info("New role created: " + role);
        return mapper.map(role, RoleDto.class);
    }

    @Override
    public void update(Long id, RoleDto dto) {
        Role roleDb = roleRepository.findById(id).orElseThrow();
        Role role = mapper.map(dto, Role.class);
        mapper.map(role, roleDb);
        roleRepository.save(roleDb);
        log.info("Role updated: " + role);
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
        log.info("Role deleted id: " + id);
    }

    @Override
    public List<ScopeDto> getRoleScopes(Long id) {
        Role role = roleRepository.findById(id).orElseThrow();
        return role.getScopes().stream().map(scope -> mapper.map(scope, ScopeDto.class)).collect(Collectors.toList());
    }

    @Override
    public boolean addScope(Long roleId, Long scopeId) {
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        Optional<Scope> optionalScope = scopeRepository.findById(scopeId);
        if(optionalRole.isEmpty() || optionalScope.isEmpty()) {
            //TODO: add exception
            return false;
        }
        Role role = optionalRole.get();
        role.addScope(optionalScope.get());
        return true;
    }

    @Override
    public boolean removeScope(Long roleId, Long scopeId) {
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        Optional<Scope> optionalScope = scopeRepository.findById(scopeId);
        if(optionalRole.isEmpty() || optionalScope.isEmpty()) {
            //TODO: add exception
            return false;
        }
        Role role = optionalRole.get();
        Scope scope = optionalScope.get();
        if(role.containsScope(scope)) {
            role.removeScope(scope);
            return true;
        }
        return false;
    }
}
