<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Menú Principal</title>
</head>
<body>
    <h1>¡Bienvenido!</h1>
    <p>Selecciona una opción para continuar:</p>
    
    
    <form action="ControladorProyecto" method="get">
        <button type="submit">Ir a Proyectos</button>
    </form>
    
    <form action="login.jsp" method="get">
        <button type="submit">Cerrar Sesión / Cancelar</button>
    </form>
</body>
</html>