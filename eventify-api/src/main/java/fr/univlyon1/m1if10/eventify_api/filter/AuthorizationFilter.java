package fr.univlyon1.m1if10.eventify_api.filter;

import fr.univlyon1.m1if10.eventify_api.entity.Evenement;
import fr.univlyon1.m1if10.eventify_api.repository.EvenementRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Order(2)
public class AuthorizationFilter extends HttpFilter {

    private final EvenementRepository evenementRepository;

    public AuthorizationFilter(EvenementRepository evenementRepository) {
        this.evenementRepository = evenementRepository;
    }

    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain chain)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            chain.doFilter(request, response);
            return;
        }
        String role = (String) session.getAttribute("role");
        Integer userId = (Integer) session.getAttribute("userId");
        String url = request.getRequestURI().replace(request.getContextPath(), "");
        String method = request.getMethod();
        if ("ADMIN".equals(role)) {
            chain.doFilter(request, response);
            return;
        }
        if (url.startsWith("/evenements")) {
            if (url.equals("/evenements/valides") && method.equals("GET")) {
                chain.doFilter(request, response);
                return;
            }
            if (url.equals("/evenements") && method.equals("GET")) {
                if ("ORGANISATEUR".equals(role)) {
                    chain.doFilter(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
                return;
            }
            if (url.equals("/evenements") && method.equals("POST")) {
                if ("ORGANISATEUR".equals(role)) {
                    chain.doFilter(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
                return;
            }
            String[] parts = url.split("/");

            if (parts.length >= 3) {
                try {
                    Integer eventId = Integer.parseInt(parts[2]);
                    Optional<Evenement> optEvent = evenementRepository.findById(eventId);
                    if (optEvent.isEmpty()) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }
                    Evenement event = optEvent.get();
                    if ("USER".equals(role)) {
                        if (!event.isValide()) {
                            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                                    "Événement non validé");
                            return;
                        }
                        chain.doFilter(request, response);
                        return;
                    }
                    if ("ORGANISATEUR".equals(role)) {
                        if (!event.getOrgaId().equals(userId)) {
                            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                                    "Pas votre événement");
                            return;
                        }
                        if (url.endsWith("/validate") && method.equals("PUT")) {
                            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                                    "Un organisateur ne peut pas valider");
                            return;
                        }
                        chain.doFilter(request, response);
                        return;
                    }
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }
}
