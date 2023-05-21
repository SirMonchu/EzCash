package es.ezcash.controllers;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // Verificar si el usuario ha iniciado sesión
        if (!request.getRequestURI().equals("/login") && !request.getRequestURI().equals("/register")
                && request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/login"); // Redirigir a la página de login si no ha iniciado sesión
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // No se requiere ninguna acción después de manejar la solicitud
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        // No se requiere ninguna acción después de completar la solicitud
    }
}
