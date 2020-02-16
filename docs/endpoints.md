| URL                       | Verbo HTTP| Parámetros                                    | Resultado |
| -------------             | --------- | -----                                         | --------------- |
| /api/product              | GET       |                                               | Obtiene todos los productos no vendidos de la base de datos |
| /api/product              | POST      | Id de una categoría y JSON de un producto     | Inserta un producto en una categoría |
| /api/product/:id          | PUT       | Id y JSON del producto                        | Actualiza el producto |
| /api/product/:category    | GET       | Id de una categoría                           | Obtiene todos los productos no vendidos de una categoría |
| /api/product/:id/visit    | GET       | Id de un producto                             | Suma una visita al producto y devuelve el total |
| /api/user                 | POST      | JSON de un usuario                            | Inserta un usuario |
| /api/user/:id             | GET       | Id de un usuario                              | Obtiene el usuario con el id pasado
| /api/user/:id             | PUT       | Id y JSON de un usuario                       | Actualiza un usuario |
| /api/user/:id             | DELETE    | Id de un usuario                              | Elimina un usuario |
| /api/user/:id/product     | GET       |                                               | Obtiene todos los productos de un usuario (vendidos y no) |