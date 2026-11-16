<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Proyectos" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestion Proyectos</title>
</head>
<body>
    <h1>Gestion Proyectos</h1>

    <p>
        <a href="menu.jsp"><button type="button">Volver al Menú</button></a>
    </p>

    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Fecha Inicio</th>
            <th>Fecha Término</th>
            <th>Acciones</th>
        </tr>
        <%
            List<Proyectos> lista = (List<Proyectos>) request.getAttribute("proyectos");
            if (lista != null && !lista.isEmpty()) {
                for (Proyectos p : lista) {
        %>
        <tr>
            <td><%= p.getId() %></td>
            <td><%= p.getNombre() %></td>
            <td><%= p.getDescripcion() %></td>
            <td><%= p.getFechainicio() != null ? p.getFechainicio() : "" %></td>
            <td><%= p.getFechatermino() != null ? p.getFechatermino() : "" %></td>
            <td>
                <!-- Botón Editar -->
                <form action="EditarProyecto.jsp" method="get" style="display:inline;">
                    <input type="hidden" name="id" value="<%= p.getId() %>">
                    <button type="submit">Editar</button>
                </form>
                
                <!-- Botón Eliminar conectado al Controlador -->
                <form action="ControladorProyecto" method="post" style="display:inline; margin-left: 5px;">
                    <input type="hidden" name="accion" value="eliminar">
                    <input type="hidden" name="id" value="<%= p.getId() %>">
                    <button type="submit" onclick="return confirm('¿Estás seguro de que quieres eliminar este proyecto, con ID <%= p.getId() %>?');">Eliminar</button>
                </form>
            </td>
        </tr>
        <% 
                }
            } else { 
        %>
        <tr>
            <td colspan="6" style="text-align: center;">No hay proyectos registrados.</td>
        </tr>
        <% } %>
    </table>
</body>
</html>