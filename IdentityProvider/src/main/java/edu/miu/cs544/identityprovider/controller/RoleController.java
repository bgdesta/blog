package edu.miu.cs544.identityprovider.controller;

import edu.miu.cs544.identityprovider.dto.IdDto;
import edu.miu.cs544.identityprovider.dto.RoleDto;
import edu.miu.cs544.identityprovider.dto.ScopeDto;
import edu.miu.cs544.identityprovider.service.RoleService;
import edu.miu.cs544.identityprovider.service.impl.RoleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final static Logger log = LoggerFactory.getLogger(RoleController.class);

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService rs) {
        roleService = rs;
    }

    @GetMapping("/")
    public ResponseEntity<List<RoleDto>> getAll() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<RoleDto> create(@RequestBody RoleDto roleDto) {
        log.info("Role dto received: " + roleDto);
        return ResponseEntity.ok(roleService.save(roleDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        roleService.update(id, roleDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{roleId}/scopes/")
    public ResponseEntity<List<ScopeDto>> getRoleScopes(@PathVariable Long roleId) {
        return ResponseEntity.ok(roleService.getRoleScopes(roleId));
    }

    @PostMapping("/{roleId}/scopes/")
    public ResponseEntity addScope(@PathVariable Long roleId, @RequestBody IdDto scopeId) {
        if(roleService.addScope(roleId, scopeId.getId())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{roleId}/scopes/{scopeId}")
    public ResponseEntity removeScope(@PathVariable Long roleId, @PathVariable Long scopeId) {
        if(roleService.removeScope(roleId, scopeId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
