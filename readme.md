# Proyecto SecondLife 2020
![Logo](docs/Logotipo/Logo-min.png)

---

[Requisitos](docs/requisitos.md) -
[Resumen](docs/resumen.md) - 
[Endpoints](docs/endpoints.md) 

---
  
## Esquema de la base de datos:

![Esquema](docs/Esquema&#32;DB/baseDatosEsquema.png)

## Diagrama de clases :

![Diagrama](docs/DiagramaClases/DiagramaClases.png)

## Tipos de usuarios:

- Tipo 1: Usuario estandar
- Tipo 2: Usuario administrador

## Docker:

#### Generar los contenedores:
```bash
docker-compose up
```

#### Iniciar / Parar:
```bash
docker-compose start
docker-compose stop
```

#### Borrar:
```bash
docker-compose down
```

#### Limpiar cache:
Si realizamos ajustes en la configuración de docker-compose y no se aplican deberemos limpiar el cache con el siguiente comando.
```bash
docker system prune -a
```

## Paleta de colores:

<h3 style="color: #2e6070">#2e6070</h3>
<h3 style="color: #202c39">#202c39</h3>
<h3 style="color: #B8B08D">#B8B08D</h3>
<h3 style="color: #F2D492">#F2D492</h3>
<h3 style="color: #F29559">#F29559</h3>

## Participantes:

- Alberto Torres Cruz
- Álvaro García Muñoz 
