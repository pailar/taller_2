<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Proyectos"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Proyectos</title>
</head>
<body>
    <a href="menu.jsp">← Volver al Menú</a>
    <h1>Proyectos</h1>

    <%
        Proyectos proyectoEditar = (Proyectos) request.getAttribute("proyectoEditar");
        boolean editando = (proyectoEditar != null);
    %>

    <form action="ControladorProyecto" method="POST">
        <input type="hidden" name="accion" value="<%= editando ? "actualizar" : "guardar" %>">
        
        <% if (editando) { %>
            <input type="hidden" name="txtId" value="<%= proyectoEditar.getId() %>">
        <% } %>

        <div>
            <label>Nombre del Proyecto:</label>
            <input type="text" name="txtNombre" value="<%= editando ? proyectoEditar.getNombre() : "" %>" required>
        </div>

        <div>
            <label>Funcionario Responsable:</label>
            <select name="txtIdFuncionario" required>
                <option value="1" <%= (editando && proyectoEditar.getIdFuncionarioResponsable() == 1) ? "selected" : "" %>>1 - Camila Soto</option>
                <option value="2" <%= (editando && proyectoEditar.getIdFuncionarioResponsable() == 2) ? "selected" : "" %>>2 - Jorge Muñoz</option>
            </select>
        </div>

        <br>
        <button type="submit"><%= editando ? "Actualizar" : "Guardar" %></button>
        <% if (editando) { %>
            <a href="ControladorProyecto?accion=listar">Cancelar</a>
        <% } %>
    </form>

    <hr>

    <table border="1" cellpadding="5" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Funcionario</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Proyectos> lista = (List<Proyectos>) request.getAttribute("proyectos");
                if (lista != null) {
                    for (Proyectos p : lista) {
            %>
                <tr>
                    <td><%= p.getId() %></td>
                    <td><%= p.getNombre() %></td>
                    <td><%= p.getIdFuncionarioResponsable() %></td>
                    <td>
                        <a href="ControladorProyecto?accion=editar&id=<%= p.getId() %>">Editar</a> | 
                        <a href="ControladorProyecto?accion=eliminar&id=<%= p.getId() %>" onclick="return confirm('¿Eliminar este proyecto?');">Eliminar</a>
                    </td>
                </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>
</body>
</html>