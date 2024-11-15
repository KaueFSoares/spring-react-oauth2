package kauesoares.oauth2.backend.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
    public static String getUserName() {
        if (SecurityContextHolder.getContext() == null
                || SecurityContextHolder.getContext().getAuthentication() == null
                || "anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            return null;
        }
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
