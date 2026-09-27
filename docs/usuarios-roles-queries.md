# Consultas de Usuarios

## Consulta simple por estado

La consulta simple filtra directamente la entidad `User` usando el campo
`statusUser`:

```http
GET /api/Users/estados?status=Activo
```

## Consulta con JOIN por rol

La consulta con `JOIN` relaciona `User` con `Role` mediante la asociación
`u.role` y filtra por `nameRole`:

```http
GET /api/Users/por-rol?nameRole=ADMIN
```

Ambos métodos están definidos en `IUserRepository`, expuestos por
`IUserService` y utilizados desde `UserController`.
