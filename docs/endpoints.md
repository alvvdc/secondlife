| URL                       | Verbo HTTP| Parámetros                                    | Resultado |
| -------------             | --------- | -----                                         | --------------- |
| /api/product              | GET       |                                               | Obtiene todos los productos en la base de datos |
| /api/product/:id          | PUT       | Id y JSON del producto                        | Actualiza el producto con el id pasado |
| /api/category             | GET       |                                               | Obtiene todas las categorías de productos |
| /api/category:id          | GET       | Id de una categoría                           | Obtiene una categoría de productos |
| /api/category/:id/product | GET       | Id de una categoría                           | Obtiene todos los productos de una categoría |
| /api/category/:id/product | POST      | Id de una categoría y JSON de un producto     | Inserta un producto en una categoría |
| /api/user                 | POST      | JSON de un usuario                            | Inserta un usuario |
| /api/user/:id             | GET       | Id de un usuario                              | Obtiene el usuario con el id pasado
| /api/user/:id             | PUT       | Id y JSON de un usuario                       | Actualiza un usuario |
| /api/user/:id             | DELETE    | Id de un usuario                              | Elimina un usuario |
| /api/user/:id/product     | GET       |                                               | Obtiene todos los productos de un usuario |