<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Menú Principal</title>
    <!-- Bootstrap 5 CSS CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6 text-center">
                <div class="card shadow p-4">
                    <h2 class="mb-3">¡Bienvenido, Administrador!</h2>
                    <p class="text-muted mb-4">Selecciona una opción para continuar:</p>
                    
                    <div class="d-grid gap-3">
                        <!-- Botón para ir a tu tabla de proyectos -->
                        <a href="proyectos.jsp" class="btn btn-primary btn-lg">
                            Ir a Proyectos
                        </a>
                        
                        <!-- Botón para retroceder al login -->
                        <a href="login.jsp" class="btn btn-outline-secondary">
                            Cerrar Sesión / Cancelar
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>