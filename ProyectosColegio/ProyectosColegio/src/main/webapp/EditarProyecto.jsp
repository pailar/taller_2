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
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Editar proyecto</title>
    <link rel="stylesheet" href="css/estilo.css">
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

    <div class="barra">
        <h1>Editar proyecto</h1>
        <form action="ControladorProyecto" method="get">
            <input type="hidden" name="accion" value="listar">
            <input type="submit" value="Volver a proyectos">
        </form>
    </div>

    <% if (request.getParameter("ok") != null) { %>
        <div class="msg ok">Cambios guardados correctamente.</div>
    <% } %>
    <% if ("2".equals(request.getParameter("error"))) { %>
        <div class="msg error">La fecha de término no puede ser anterior a la fecha de inicio.</div>
    <% } else if ("3".equals(request.getParameter("error"))) { %>
        <div class="msg error">Esa línea de acción ya está asociada a este proyecto.</div>
    <% } else if (request.getParameter("error") != null) { %>
        <div class="msg error">No se pudo completar la operación.</div>
    <% } %>

    <!-- ===================== 1. PROYECTO ===================== -->
    <div class="seccion">
        <h2>1. PROYECTO</h2>

        <form action="ControladorProyecto" method="POST">
            <input type="hidden" name="accion" value="actualizar">
            <input type="hidden" name="txtId" value="<%= idProyecto %>">

            <table class="form-tabla">
                <tbody>
                    <tr>
                        <td>Nombre proyecto</td>
                        <td><input type="text" name="txtNombre" maxlength="100"
                                   value="<%= esc(proyectoEditar.getNombre()) %>" required></td>
                    </tr>
                    <tr>
                        <td>Descripción</td>
                        <td><textarea name="txtDescripcion" rows="3" cols="50" maxlength="500"><%= esc(proyectoEditar.getDescripcion()) %></textarea></td>
                    </tr>
                    <tr>
                        <td>Responsable</td>
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
                        <td>Objetivo proyecto</td>
                        <td><input type="text" name="txtObjetivo" maxlength="255"
                                   value="<%= esc(proyectoEditar.getObjetivo()) %>"></td>
                    </tr>
                    <tr>
                        <td>Fecha inicio</td>
                        <td><input type="date" name="txtFechaInicio" value="<%= fechaInicio %>" required></td>
                    </tr>
                    <tr>
                        <td>Fecha término</td>
                        <td><input type="date" name="txtFechaTermino" value="<%= fechaTermino %>" required></td>
                    </tr>
                </tbody>
            </table>

            <input type="submit" value="Guardar Datos Proyecto">
        </form>

        <h3>Integrantes</h3>
        <table>
            <thead>
                <tr>
                    <th>Integrante</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% if (integrantes == null || integrantes.isEmpty()) { %>
                    <tr>
                        <td colspan="2" class="vacio">Este proyecto aún no tiene integrantes.</td>
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
                                <input type="submit" value="Quitar" class="rojo">
                            </form>
                        </td>
                    </tr>
                <%     }
                   } %>
            </tbody>
        </table>

        <form action="ControladorProyecto" method="POST">
            <input type="hidden" name="accion" value="agregarIntegrante">
            <input type="hidden" name="idProyecto" value="<%= idProyecto %>">
            <b>Agregar integrante:</b>
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
    </div>

    <!-- ===================== 2. LINEAS DE ACCION ===================== -->
    <div class="seccion">
        <h2>2. LÍNEAS DE ACCIÓN</h2>

        <form action="ControladorProyecto" method="POST" class="formulario">
            <input type="hidden" name="accion" value="crearLinea">
            <input type="hidden" name="idProyecto" value="<%= idProyecto %>">

            <label>Seleccione Línea</label>
            <select name="txtIdLinea" required>
                <% if (lineasGenericas != null) {
                       for (Linea l : lineasGenericas) { %>
                    <option value="<%= l.getId() %>"><%= esc(l.getNombre()) %></option>
                <%     }
                   } %>
            </select>

            <label>Descripción de Línea Detallada</label>
            <textarea name="txtDescripcionLinea" rows="3" cols="50" maxlength="255" required></textarea>

            <input type="submit" value="Crear Nueva Línea Detallada">
        </form>

        <h3>Líneas del proyecto</h3>
        <form action="ControladorProyecto" method="get" class="buscador">
            <input type="hidden" name="accion" value="editar">
            <input type="hidden" name="id" value="<%= idProyecto %>">
            <input type="text" name="buscarLinea" placeholder="Buscar por descripción" value="<%= esc(buscarLinea) %>">
            <input type="submit" value="Buscar">
        </form>

        <div class="scroll">
            <table class="tabla-ancha">
                <thead>
                    <tr>
                        <th>Línea genérica</th>
                        <th>Descripción línea detallada</th>
                        <th>Editar / Eliminar</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (lineasProyecto == null || lineasProyecto.isEmpty()) { %>
                        <tr>
                            <td colspan="3" class="vacio">No hay líneas detalladas para este proyecto.</td>
                        </tr>
                    <% } else {
                           for (Linea lp : lineasProyecto) { %>
                        <tr>
                            <td><b><%= esc(lp.getNombre()) %></b></td>
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
                                    <input type="submit" value="Borrar" class="rojo">
                                </form>
                            </td>
                        </tr>
                    <%     }
                       } %>
                </tbody>
            </table>
        </div>
    </div>

    <!-- ===================== 3. OBJETIVOS ESPECIFICOS ===================== -->
    <div class="seccion" id="objetivos">
        <h2>3. OBJETIVOS ESPECÍFICOS</h2>

        <% if (lineasTodas == null || lineasTodas.isEmpty()) { %>
            <p>Primero crea una línea detallada (sección 2) para poder agregar objetivos.</p>
        <% } else { %>
            <form action="ControladorObjetivo" method="POST" class="formulario">
                <input type="hidden" name="accion" value="crear">
                <input type="hidden" name="idProyecto" value="<%= idProyecto %>">

                <label>Seleccione Línea Detallada</label>
                <select name="txtIdRelacion" required>
                    <% for (Linea lt : lineasTodas) { %>
                        <option value="<%= lt.getIdRelacion() %>"><%= esc(lt.getNombre()) %>: <%= esc(lt.getDescripcion()) %></option>
                    <% } %>
                </select>

                <label>Descripción</label>
                <textarea name="txtDescripcion" id="descObjetivo" rows="3" cols="50" maxlength="255"
                          oninput="contarCaracteres()"></textarea>
                <span class="contador">Caracteres restantes: <span id="restantes">255</span></span>

                <label>Alias (nombre corto, ej: MATERIALES)</label>
                <input type="text" name="txtAlias" maxlength="30" required>

                <label>Nombre de Objetivo (Verbo + Indicador cuantitativo + Función + Indicador temporal + Finalidad)</label>
                <input type="text" name="txtNombre" maxlength="255" required>

                <input type="submit" value="Crear Nuevo Objetivo Específico">
            </form>
            <script>
                function contarCaracteres() {
                    var texto = document.getElementById("descObjetivo");
                    document.getElementById("restantes").innerText = 255 - texto.value.length;
                }
            </script>
        <% } %>

        <h3>Objetivos del proyecto</h3>
        <div class="scroll">
            <table class="tabla-ancha">
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Alias</th>
                        <th>Descripción</th>
                        <th>Editar / Eliminar</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (objetivos == null || objetivos.isEmpty()) { %>
                        <tr>
                            <td colspan="4" class="vacio">Este proyecto aún no tiene objetivos específicos.</td>
                        </tr>
                    <% } else {
                           for (Objetivo o : objetivos) { %>
                        <tr>
                            <td><%= esc(o.getNombre()) %></td>
                            <td><b><%= esc(o.getAlias()) %></b></td>
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
                                    <input type="submit" value="Borrar" class="rojo">
                                </form>
                            </td>
                        </tr>
                    <%     }
                       } %>
                </tbody>
            </table>
        </div>
    </div>

    <!-- ===================== 4. ACTIVIDADES ===================== -->
    <div class="seccion" id="actividades">
        <h2>4. ACTIVIDADES</h2>

        <div class="barra">
            <form action="ControladorProyecto#actividades" method="get" class="buscador">
                <input type="hidden" name="accion" value="editar">
                <input type="hidden" name="id" value="<%= idProyecto %>">
                <input type="text" name="buscarActividad" placeholder="Buscar por nombre" value="<%= esc(buscarActividad) %>">
                <input type="submit" value="Buscar">
            </form>

            <form action="ControladorActividad" method="get">
                <input type="hidden" name="accion" value="nuevo">
                <input type="hidden" name="idProyecto" value="<%= idProyecto %>">
                <input type="submit" value="Crear Nueva Actividad">
            </form>
        </div>

        <div class="scroll">
            <table class="tabla-ancha">
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Descripción</th>
                        <th>Objetivo específico</th>
                        <th>Fecha de inicio</th>
                        <th>Fecha de término</th>
                        <th>PAO</th>
                        <th>Observación control gestión</th>
                        <th>Responsable</th>
                        <th>Editar / Eliminar</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (actividades == null || actividades.isEmpty()) { %>
                        <tr>
                            <td colspan="9" class="vacio">No hay actividades para mostrar.</td>
                        </tr>
                    <% } else {
                           for (Actividad a : actividades) { %>
                        <tr>
                            <td><b><%= esc(a.getNombre()) %></b></td>
                            <td><%= esc(a.getDescripcion()) %></td>
                            <td><b><%= esc(a.getAliasObjetivo()) %></b></td>
                            <td><%= fmtFH(a.getFechaHoraInicio()) %></td>
                            <td><%= fmtFH(a.getFechaHoraTermino()) %></td>
                            <td><%= a.isPao() ? "Si" : "No" %></td>
                            <td><%= esc(a.getObservacion()) %></td>
                            <td><%= esc(a.getFuncionario().getNombre()) %></td>
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
                                    <input type="submit" value="Borrar" class="rojo">
                                </form>
                            </td>
                        </tr>
                    <%     }
                       } %>
                </tbody>
            </table>
        </div>
    </div>

    <form action="ControladorProyecto" method="get">
        <input type="hidden" name="accion" value="listar">
        <input type="submit" value="Cancelar / Volver">
    </form>
</body>
</html>