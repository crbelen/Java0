# Java0
HUNDIR LA FLOTA
▸ Juego de tablero para dos jugadores
▸ Tablero con 10x10 posiciones (A-J, 1-10)
▸ Se colocan varios barcos en posición
horizonal o vertical
▸ Comienza un usuario diciendo unas
coordenadas, y el contrario le indica si la
bomba lanzada toca un barco o cae en el
agua.
▸ Colocamos 2 barcos de 5 casillas, 3 de 3
casillas y 5 de 1 casilla (24 en total)
▸ No vamos a detectar si un tiro hunde
completamente un barco (complejidad de
comprobar todas las casillas de alrededor).
▸ Por contra, contabilizamos (y vamos
restando) los PUNTOS del usuario
(inicialmente 24). 
▸ Aunque no podemos ver el mapa del
oponente (lógico) vamos llevando un
registro de dónde tiramos una bomba,
para no repetir. 
