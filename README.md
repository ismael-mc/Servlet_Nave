Aplicación web JPA con:
- Sistema de registro básico (Cookies).
- Registro de partidas jugadas (fecha y hora de inicio y fin de cada partida).
- Página del listado de jugadores con más partidas jugadas.
- Base de datos PostgreSQL.
- Posee war para subir a un servidor.

Ejecución del programa:
Tras ejecutar el programa tienes opción de hacer log in o registrarte. Si eliges registrarte debes rellenar los campos de nombre, alias y contraseña. En cambio, si eliges hacer log in rellenas los campos de alias y contraseña.
Tras acceder al juego aparece un menú con las opciones que ofrece el programa:
- Jugar una partida.
- Ver tu hora de inicio y fin de partida.
- Ver la hora de inicio y fin de partida de los usuarios registrados.
- Ver un ranking de los 10 usuarios con más partidas.

Ejecución y código relevante:
La base de datos la he realizado con Postgresql 9.4.1212 y la he implementado mediante pgAdmin4. El jar para conectar a la base de datos es postgresql-9.4.1212.jre6.jar.
La bases de datos recibe el nombre de Mimcer_Nave y las dos tablas que posee son Mimcer_Users y Mimcer_Game.
 - Mimcer_Users: id_user serial (primary key), alias varchar(10) unique, nameU varchar(20) not null, passwordU varchar(10).
 - Mimcer_Game: id_game serial(primary key), id_user int (foreign key), startDate timestamp, endDate timestamp, matches int.

La primera página que aparece al ejecutar el programa es index.jsp. Consiste en un formulario html normal con acceso a dos servlets. Servler_Cookie, el cual lleva el control del log in y log out mediante cookies y el control de las fechas y horas de las partidas, posee un trozo de session. Por otra parte, tenemos el acceso a Servlet_Nave, el cual lleva el control del registro del usuario y del redireccionamiento de las páginas.

Los dos botones, Sign up y Log in al igual que los demás botones de la aplicación se implementan con JavaScript. La ejecución es la siguiente:
Todos los formularios poseen un input de tipo hidden. Al presionar el input de tipo botón lleva a un método JavaScript del jsp que direcciona al usuario a un servlet (según el tipo de acción que realice) y este lo redirecciona a la página que implementa el botón.

Tras rellenar la página jsp de registro el botón nos lleva al Servlet_Nave en el cual mediante un else if identificamos que la acción es registro y se procede a almacenar al usuario en la base de datos.

Si el usuario ya está registrado procedemos a introducir el alias y la contraseña. El alias está definido como único en la base de datos, por esa razón lo uso para hacer log in.
Tras presionar el botón de log in el jsp nos direcciona a Servlet_Cookie. Este servlet identifica que la acción es log in, mediante la clase Date recoge la fecha y la hora del sistema y la guarda en session. Compruebo que el alias y la contraseña coinciden en la base de datos, si no es así aparece una página de error que te permite volver a intentar logearte, sino crea las cookies y redirecciona el servlet al menú. 
Los jsp de menú, playedMayches y friends poseen una comprobación de si existen las cookies con el fin de no poder acceder a ellas si no te has registrado. Las demás son públicas y no es necesario. 

Una vez en el menú tenemos los siguientes botones y acciones:
 - Play. Mediante el cual accedemos al juego.
 - PlayedMatches. Mediante el cual accedemos a un jsp donde podemos ver en una tabla las fechas y las horas en las que tu como usuario      has iniciado y cerrado sesión. 
   Implemento la tabla mediante un for de los datos startDate y endDate de la tabla Mimcer_Game.
 - Friends. Mediante el cual accedemos a un jsp donde podemos ver en una tabla los alias, las fechas y las horas en las que los demás      usuario han iniciado y cerrado sesión. 
   Implemento la tabla mediante un for de los datos startDate y endDate de la tabla Mimcer_Game y alias de la tabla Mimcer_Users.
 - Top 10. Posee los datos de los usuarios que han realizado más partidas (No está completo).
 - Log out. Este botón redirecciona el jsp al Servlet_Cookie el cual identifica que la acción es logout y mediante la clase Date recoge    la fecha y la hora, en ese momento inserto en la tabla Mimcer_Game el id_user del usuario que ha iniciado sesión, la fecha de inicio    de partida guardada anteriormente en session y la fecha de fin de partida. Seguidamente elimino las cookies de ese usuario y            finalmente redirecciono el servlet a la página de index.jsp nuevamente con opción de log in o sing up. 
 
El archivo persistence.xml y context.xml están modificados con el fin de poder crear las tablas en una base de datos de un servidor llamada webdb.
En el persistence en la zona de Design, he accedido a General y dentro en Table Generation Strategy he marcado el RadioButton de Create. Por otra parte en la zona de Source, he comentado todas las properties, menos la última que es la que genera El Radio Button seleccionado anteriormente. Además añado la siguiente linea debajo de persistence-unit:
Abro y cierro una etiqueta llamada "non-jta-data-source" con "java:/comp/env/jdbc/webdb" dentro.
