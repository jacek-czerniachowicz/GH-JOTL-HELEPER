package com.gloomhaven.helper.configuration.auditing;

import com.gloomhaven.helper.model.entities.UserEntity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<UserEntity> {
    @Override
    public Optional<UserEntity> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken
        ) {
            return Optional.empty();
        }

        UserEntity userPrincipal = (UserEntity) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal);
    }
}
