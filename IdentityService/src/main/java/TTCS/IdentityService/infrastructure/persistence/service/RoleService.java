package TTCS.IdentityService.infrastructure.persistence.service;

import TTCS.IdentityService.domain.model.Role;
import TTCS.IdentityService.infrastructure.persistence.repository.RoleRepository;
import TTCS.IdentityService.infrastructure.persistence.service.DTO.Request.RoleRequest;
import TTCS.IdentityService.infrastructure.persistence.service.DTO.Response.RoleResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;

    public RoleResponse create(RoleRequest request) {
        Role role = Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        role = roleRepository.save(role);
        return RoleResponse.builder()
                .name(role.getName())
                .description(request.getDescription())
                .build();
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(role ->
                RoleResponse.builder()
                        .name(role.getName())
                        .description(role.getDescription())
                        .build()
                ).toList();
    }

    public void delete(int id) {
        roleRepository.deleteById((long) id);
    }
}
