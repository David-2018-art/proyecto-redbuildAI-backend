# Ejemplos para Swagger

## Usuarios por estado

En Swagger, abre `GET /api/Users/estados` y envía el parámetro `status`.

Ejemplo:

```text
/api/Users/estados?status=Activo
```

## Usuarios por rol

Abre `GET /api/Users/por-rol` y envía el nombre del rol.

Ejemplo:

```text
/api/Users/por-rol?nameRole=ADMIN
```

La primera operación usa el filtro directo por estado y la segunda usa la
relación `User`–`Role` mediante `JOIN`.
