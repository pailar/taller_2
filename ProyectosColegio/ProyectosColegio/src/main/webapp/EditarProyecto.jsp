<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Actividad"%>
<%@page import="modelo.Funcionario"%>
<%@page import="modelo.Linea"%>
<%@page import="modelo.Objetivo"%>
<%@page import="modelo.Proyecto"%>
<%!
    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    private String fmtFH(LocalDateTime d) {
        if (d == null) return "-";
        return d.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar proyecto</title>
</head>
<body>
    <%
        Proyecto proyectoEditar = (Proyecto) request.getAttribute("proyectoEditar");
        List<Funcionario> funcionarios = (List<Funcionario>) request.getAttribute("funcionarios");
        List<Funcionario> integrantes = (List<Funcionario>) request.getAttribute("integrantes");
        List<Funcionario> disponibles = (List<Funcionario>) request.getAttribute("disponibles");
        List<Linea> lineasGenericas = (List<Linea>) request.getAttribute("lineasGenericas");
        List<Linea> lineasProyecto = (List<Linea>) request.getAttribute("lineasProyecto");
        List<Linea> lineasTodas = (List<Linea>) request.getAttribute("lineasTodas");
        List<Objetivo> objetivos = (List<Objetivo>) request.getAttribute("objetivos");
        List<Actividad> actividades = (List<Actividad>) request.getAttribute("actividades");
        String buscarLinea = (String) request.getAttribute("buscarLinea");
        String buscarActividad = (String) request.getAttribute("buscarActividad");
        String fechaInicio = (proyectoEditar.getFechaInicio() != null) ? proyectoEditar.getFechaInicio().toString() : "";
        String fechaTermino = (proyectoEditar.getFechaTermino() != null) ? proyectoEditar.getFechaTermino().toString() : "";
        int idProyecto = proyectoEditar.getId();
    %>

    <big><b>EDITAR PROYECTO</b></big>
    <form action="ControladorProyecto" method="get">
        <input type="hidden" name="accion" value="listar">
        <input type="submit" value="Volver a proyectos">
    </form>

    <% if (request.getParameter("ok") != null) { %>
        <p><b>Cambios guardados correctamente.</b></p>
    <% } %>
    <% if ("2".equals(request.getParameter("error"))) { %>
        <p><b>La fecha de término no puede ser anterior a la fecha de inicio.</b></p>
    <% } else if ("3".equals(request.getParameter("error"))) { %>
        <p><b>Esa línea de acción ya está asociada a este proyecto.</b></p>
    <% } else if (request.getParameter("error") != null) { %>
        <p><b>No se pudo completar la operación.</b></p>
    <% } %>

    <!-- ===================== 1. PROYECTO ===================== -->
    <big><b>1. PROYECTO</b></big>
    <br>
    <br>

    <form action="ControladorProyecto" method="POST">
        <input type="hidden" name="accion" value="actualizar">
        <input type="hidden" name="txtId" value="<%= idProyecto %>">

        <table style="text-align: left;" border="1" cellpadding="2" cellspacing="2">
            <tbody>
                <tr>
                    <td><b>NOMBRE PROYECTO</b></td>
                    <td><input type="text" name="txtNombre" size="60" maxlength="100"
                               value="<%= esc(proyectoEditar.getNombre()) %>" required></td>
                </tr>
                <tr>
                    <td><b>DESCRIPCIÓN</b></td>
                    <td><textarea name="txtDescripcion" rows="3" cols="50" maxlength="500"><%= esc(proyectoEditar.getDescripcion()) %></textarea></td>
                </tr>
                <tr>
                    <td><b>RESPONSABLE</b></td>
                    <td>
                        <select name="txtIdFuncionario" required>
                            <% if (funcionarios != null) {
                                   for (Funcionario f : funcionarios) { %>
                                <option value="<%= f.getId() %>"
                                    <%= (f.getId() == proyectoEditar.getFuncionario().getId()) ? "selected" : "" %>>
                                    <%= esc(f.getNombre()) %>
                                </option>
                            <%     }
                               } %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><b>OBJETIVO PROYECTO</b></td>
                    <td><input type="text" name="txtObjetivo" size="60" maxlength="255"
                               value="<%= esc(proyectoEditar.getObjetivo()) %>"></td>
                </tr>
                <tr>
                    <td><b>FECHA INICIO</b></td>
                    <td><input type="date" name="txtFechaInicio" value="<%= fechaInicio %>" required></td>
                </tr>
                <tr>
                    <td><b>FECHA TÉRMINO</b></td>
                    <td><input type="date" name="txtFechaTermino" value="<%= fechaTermino %>" required></td>
                </tr>
            </tbody>
        </table>
        <br>
        <input type="submit" value="Guardar Datos Proyecto">
    </form>
    <br>

    <b>Integrantes:</b>
    <table style="text-align: left;" border="1" cellpadding="2" cellspacing="2">
        <tbody>
            <% if (integrantes == null || integrantes.isEmpty()) { %>
                <tr>
                    <td colspan="2">Este proyecto aún no tiene integrantes.</td>
                </tr>
            <% } else {
                   for (Funcionario i : integrantes) { %>
                <tr>
                    <td><b><%= esc(i.getNombre()) %></b></td>
                    <td>
                        <form action="ControladorProyecto" method="get">
                            <input type="hidden" name="accion" value="confirmarQuitarIntegrante">
                            <input type="hidden" name="idProyecto" value="<%= idProyecto %>">
                            <input type="hidden" name="idFuncionario" value="<%= i.getId() %>">
                            <input type="submit" value="Quitar">
                        </form>
                    </td>
                </tr>
            <%     }
               } %>
        </tbody>
    </table>
    <br>

    <form action="ControladorProyecto" method="POST">
        <input type="hidden" name="accion" value="agregarIntegrante">
        <input type="hidden" name="idProyecto" value="<%= idProyecto %>">
        <b>Agregar:</b>
        <% if (disponibles == null || disponibles.isEmpty()) { %>
            (no quedan funcionarios por agregar)
        <% } else { %>
            <select name="idFuncionario">
                <% for (Funcionario d : disponibles) { %>
                    <option value="<%= d.getId() %>"><%= esc(d.getNombre()) %></option>
                <% } %>
            </select>
            <input type="submit" value="Agregar">
        <% } %>
    </form>
    <br>
    <br>

    <!-- ===================== 2. LINEAS DE ACCION ===================== -->
    <big><b>2. LÍNEAS DE ACCIÓN</b></big>
    <br>
    <br>

    <form action="ControladorProyecto" method="POST">
        <input type="hidden" name="accion" value="crearLinea">
        <input type="hidden" name="idProyecto" value="<%= idProyecto %>">

        Seleccione Línea:
        <select name="txtIdLinea" required>
            <% if (lineasGenericas != null) {
                   for (Linea l : lineasGenericas) { %>
                <option value="<%= l.getId() %>"><%= esc(l.getNombre()) %></option>
            <%     }
               } %>
        </select>
        <br>
        <br>

        Descripción de Línea Detallada:
        <br>
        <textarea name="txtDescripcionLinea" rows="3" cols="50" maxlength="255" required></textarea>
        <br>
        <input type="submit" value="Crear Nueva Línea Detallada">
    </form>
    <br>

    <form action="ControladorProyecto" method="get">
        <input type="hidden" name="accion" value="editar">
        <input type="hidden" name="id" value="<%= idProyecto %>">
        Buscar por Descripción:
        <input type="text" name="buscarLinea" value="<%= esc(buscarLinea) %>">
        <input type="submit" value="Buscar">
    </form>
    <br>

    <table style="text-align: left;" border="1" cellpadding="2" cellspacing="2">
        <tbody>
            <tr>
                <td><b>LINEA GENERICA</b></td>
                <td><b>DESCRIPCION LINEA GENERICA PARA ESTE PROYECTO (LINEA DETALLADA)</b></td>
                <td><b>EDITAR / ELIMINAR</b></td>
            </tr>
            <% if (lineasProyecto == null || lineasProyecto.isEmpty()) { %>
                <tr>
                    <td colspan="3">No hay líneas detalladas para este proyecto.</td>
                </tr>
            <% } else {
                   for (Linea lp : lineasProyecto) { %>
                <tr>
                    <td><%= esc(lp.getNombre()) %></td>
                    <td><%= esc(lp.getDescripcion()) %></td>
                    <td>
                        <form action="ControladorProyecto" method="get">
                            <input type="hidden" name="accion" value="editarLinea">
                            <input type="hidden" name="idProyecto" value="<%= idProyecto %>">
                            <input type="hidden" name="idRelacion" value="<%= lp.getIdRelacion() %>">
                            <input type="submit" value="Editar Línea">
                        </form>
                        <form action="ControladorProyecto" method="POST"
                              onsubmit="return confirm('¿Borrar esta línea? Se borrarán también sus objetivos y actividades.');">
                            <input type="hidden" name="accion" value="borrarLinea">
                            <input type="hidden" name="idProyecto" value="<%= idProyecto %>">
                            <input type="hidden" name="idRelacion" value="<%= lp.getIdRelacion() %>">
                            <input type="submit" value="Borrar">
                        </form>
                    </td>
                </tr>
            <%     }
               } %>
        </tbody>
    </table>
    <br>
    <br>

    <!-- ===================== 3. OBJETIVOS ESPECIFICOS ===================== -->
    <a name="objetivos"></a>
    <big><b>3. OBJETIVOS ESPECÍFICOS</b></big>
    <br>
    <br>

    <% if (lineasTodas == null || lineasTodas.isEmpty()) { %>
        <p>Primero crea una línea detallada (sección 2) para poder agregar objetivos.</p>
    <% } else { %>
        <form action="ControladorObjetivo" method="POST">
            <input type="hidden" name="accion" value="crear">
            <input type="hidden" name="idProyecto" value="<%= idProyecto %>">

            Seleccione Línea Detallada:
            <select name="txtIdRelacion" required>
                <% for (Linea lt : lineasTodas) { %>
                    <option value="<%= lt.getIdRelacion() %>"><%= esc(lt.getNombre()) %>: <%= esc(lt.getDescripcion()) %></option>
                <% } %>
            </select>
            <br>
            <br>

            <b>Descripción:</b>
            <br>
            <textarea name="txtDescripcion" id="descObjetivo" rows="3" cols="50" maxlength="255"
                      oninput="contarCaracteres()"></textarea>
            <br>
            <b>Caracteres restantes: <span id="restantes">255</span></b>
            <br>
            <br>

            Alias (nombre corto, ej: MATERIALES):
            <br>
            <input type="text" name="txtAlias" size="30" maxlength="30" required>
            <br>
            <br>

            Nombre de Objetivo (Verbo + Indicador cuantitativo + Función + Indicador temporal + Finalidad):
            <br>
            <input type="text" name="txtNombre" size="60" maxlength="255" required>
            <br>
            <br>

            <input type="submit" value="Crear Nuevo Objetivo Específico">
        </form>
        <script>
            function contarCaracteres() {
                var texto = document.getElementById("descObjetivo");
                document.getElementById("restantes").innerText = 255 - texto.value.length;
            }
        </script>
    <% } %>
    <br>

    <table style="text-align: left;" border="1" cellpadding="2" cellspacing="2">
        <tbody>
            <tr>
                <td><b>NOMBRE</b></td>
                <td><b>ALIAS</b></td>
                <td><b>DESCRIPCION</b></td>
                <td><b>EDITAR / ELIMINAR</b></td>
            </tr>
            <% if (objetivos == null || objetivos.isEmpty()) { %>
                <tr>
                    <td colspan="4">Este proyecto aún no tiene objetivos específicos.</td>
                </tr>
            <% } else {
                   for (Objetivo o : objetivos) { %>
                <tr>
                    <td><%= esc(o.getNombre()) %></td>
                    <td><%= esc(o.getAlias()) %></td>
                    <td><%= esc(o.getDescripcion()) %></td>
                    <td>
                        <form action="ControladorObjetivo" method="get">
                            <input type="hidden" name="accion" value="editar">
                            <input type="hidden" name="idProyecto" value="<%= idProyecto %>">
                            <input type="hidden" name="idObjetivo" value="<%= o.getId() %>">
                            <input type="submit" value="Editar Objetivo">
                        </form>
                        <form action="ControladorObjetivo" method="POST"
                              onsubmit="return confirm('¿Borrar este objetivo? Se borrarán también sus actividades.');">
                            <input type="hidden" name="accion" value="eliminar">
                            <input type="hidden" name="idProyecto" value="<%= idProyecto %>">
                            <input type="hidden" name="idObjetivo" value="<%= o.getId() %>">
                            <input type="submit" value="Borrar">
                        </form>
                    </td>
                </tr>
            <%     }
               } %>
        </tbody>
    </table>

    <hr>

    <!-- ===================== 4. ACTIVIDADES ===================== -->
    <a name="actividades"></a>
    <big><b>4. ACTIVIDADES</b></big>
    <br>
    <br>

    <form action="ControladorActividad" method="get">
        <input type="hidden" name="accion" value="nuevo">
        <input type="hidden" name="idProyecto" value="<%= idProyecto %>">
        <input type="submit" value="Crear Nueva Actividad">
    </form>
    <br>

    <form action="ControladorProyecto#actividades" method="get">
        <input type="hidden" name="accion" value="editar">
        <input type="hidden" name="id" value="<%= idProyecto %>">
        <b>Buscar por Nombre:</b>
        <input type="text" name="buscarActividad" value="<%= esc(buscarActividad) %>">
        <input type="submit" value="Buscar">
    </form>
    <br>

    <table style="text-align: left;" border="1" cellpadding="2" cellspacing="2">
        <tbody>
            <tr>
                <td><b>NOMBRE</b></td>
                <td><b>DESCRIPCION</b></td>
                <td><b>OBJETIVO ESPECIFICO ASOCIADO</b></td>
                <td><b>FECHA DE INICIO</b></td>
                <td><b>FECHA DE TERMINO</b></td>
                <td><b>PAO</b></td>
                <td><b>OBSERVACION CONTROL GESTION</b></td>
                <td><b>RESPONSABLE</b></td>
                <td><b>EDITAR / ELIMINAR</b></td>
            </tr>
            <% if (actividades == null || actividades.isEmpty()) { %>
                <tr>
                    <td colspan="9">No hay actividades para mostrar.</td>
                </tr>
            <% } else {
                   for (Actividad a : actividades) { %>
                <tr>
                    <td><b><%= esc(a.getNombre()) %></b></td>
                    <td><b><%= esc(a.getDescripcion()) %></b></td>
                    <td><b><%= esc(a.getAliasObjetivo()) %></b></td>
                    <td><b><%= fmtFH(a.getFechaHoraInicio()) %></b></td>
                    <td><b><%= fmtFH(a.getFechaHoraTermino()) %></b></td>
                    <td><b><%= a.isPao() ? "Si" : "No" %></b></td>
                    <td><b><%= esc(a.getObservacion()) %></b></td>
                    <td><b><%= esc(a.getFuncionario().getNombre()) %></b></td>
                    <td>
                        <form action="ControladorActividad" method="get">
                            <input type="hidden" name="accion" value="editar">
                            <input type="hidden" name="idProyecto" value="<%= idProyecto %>">
                            <input type="hidden" name="idActividad" value="<%= a.getId() %>">
                            <input type="submit" value="Editar">
                        </form>
                        <form action="ControladorActividad" method="POST"
                              onsubmit="return confirm('¿Borrar esta actividad?');">
                            <input type="hidden" name="accion" value="eliminar">
                            <input type="hidden" name="idProyecto" value="<%= idProyecto %>">
                            <input type="hidden" name="idActividad" value="<%= a.getId() %>">
                            <input type="submit" value="Borrar">
                        </form>
                    </td>
                </tr>
            <%     }
               } %>
        </tbody>
    </table>

    <hr>
    <form action="ControladorProyecto" method="get">
        <input type="hidden" name="accion" value="listar">
        <input type="submit" value="Cancelar / Volver">
    </form>
</body>
</html>