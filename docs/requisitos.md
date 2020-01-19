# Requisitos del proyecto

* Inicio
    * Splash screen mientras carga la lista de productos. Esta pantalla incluirá el logo de la aplicación.
    * Pantalla de autenticación con usuario y contraseña. Se creará un token para realizar las peticiones REST.
    * Uso de persistencia local para guardar el token y evitar tener que iniciar sesión cada vez que se abra la aplicación.

* Registro
    * En el proceso de registro, aparte del nombre de usuario y la contraseña, se solicitará datos como nombre, apellidos, ubicación, contacto y una foto.

* Pantalla principal
    * Menú de tipo Navigation Drawer para navegar por categorías de productos, ver información del perfil y consultar otras opciones (detalladas más abajo).
    * Listado con todos los productos en un CardView cada uno.
        * Con una pulsación sobre un producto de la lista, se mostrará el detalle de éste (dependiendo de las condiciones) ².
    * Para filtrar el listado, se incluirá un campo de búsqueda.
    * FAP para crear un producto.

* Navigation Drawer
    * Al pulsar sobre una categoría, se filtran los productos de la pantalla principal.
    * Incluirá la información del perfil ingresado.
    * Contará con la opción para ver los anuncios del usuario ingresado. ¹
    * Contará con la opción para ir a la pantalla de perfil.

* Productos
    * Un usuario registrado podrá crear un producto, incluyendo fotos, título, descripción, categoría y precio.
        * Dependiendo de la categoría, podrían añadirse más campos de texto, como kilómetros para vehículos o talla para ropa.
    * Un usuario registrado podrá ver, modificar y borrar sus productos creados.
    * ² Un usuario registrado podrá ver toda la información de los productos publicados.
    * ² Un usuario no registrado solo podrá ver la lista de productos, pero no podrá acceder al detalle, se le redirigirá a la pantalla de inicio de sesión / registro.
    * Un usuario registrado podrá marcar un producto publicado como vendido, desapareciendo de la lista pública pero no de la lista de los productos del usuario. ¹

* Detalle de un producto
    * Podrá verse la información del vendedor del producto. Se puede incluir en el mismo anuncio o pulsando sobre el autor, llevar a otra pantalla con toda la información de éste.
    * Se indicará el número de visitas que tiene el anuncio. Éstas se contaran por el número de peticiones al detalle del producto.

* Perfil
    * Podrán modificarse los datos del usuario ingresado (nombre, apellidos, ubicación, contacto y una foto), así como eliminar la cuenta.

¹ Falta especificar como se establecerá como vendido, podría ser un botón dentro del CardView o una pulsación prolongada, por ejemplo.
