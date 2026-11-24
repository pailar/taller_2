package controlador;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// Se ejecuta antes de cada pagina: solo deja pasar a quien inicio sesion.
// Las paginas publicas (portada, login, css, img, js) se pueden ver sin sesion.
@WebFilter(filterName = "FiltroSesion", urlPatterns = {"/*"})
public class FiltroSesion implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (esPublica(request.getServletPath())) {
            chain.doFilter(req, res);
            return;
        }

        HttpSession sesion = request.getSession(false);
        boolean haySesion = (sesion != null && sesion.getAttribute("usuario") != null);

        if (haySesion) {
            // evita que el boton "atras" muestre paginas guardadas despues de cerrar sesion
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            chain.doFilter(req, res);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    private boolean esPublica(String ruta) {
        return ruta == null
                || ruta.isEmpty()
                || ruta.equals("/")
                || ruta.equals("/index.jsp")
                || ruta.equals("/login.jsp")
                || ruta.equals("/LoginServlet")
                || ruta.startsWith("/css/")
                || ruta.startsWith("/img/")
                || ruta.startsWith("/js/");
    }

    @Override
    public void destroy() {
    }
}