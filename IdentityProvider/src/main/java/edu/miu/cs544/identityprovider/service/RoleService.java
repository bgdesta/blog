package edu.miu.cs544.identityprovider.service;

import edu.miu.cs544.identityprovider.dto.RoleDto;
import edu.miu.cs544.identityprovider.dto.ScopeDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {
    List<RoleDto> findAll();
    List<RoleDto> findAll(Pageable pageable);
    RoleDto findById(Long id);
    RoleDto save(RoleDto dto);
    void update(Long id, RoleDto dto);
    void delete(Long id);

    List<ScopeDto> getRoleScopes(Long id);

    boolean addScope(Long roleId, Long scopeId);

    boolean removeScope(Long roleId, Long scopeId);
}
