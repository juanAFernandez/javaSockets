# Un simple makefile para compilar clases de java

# Definición del compilador de JAVA
JCC = javac
# Definimos alguna variable para la compilación como la depuración
JFLAGS = -g
MAIN = ServidorPuntuaciones
JVM = java

###########################################

#Compilación de los fuentes .java

default: baseDatos.class ServidorPuntuaciones.class ServidorPuntuaciones.jar

baseDatos.class: baseDatos.java
	$(JCC) $(JFLAGS)   baseDatos.java ServidorPuntuaciones.java

ServidorPuntuaciones.class: ServidorPuntuaciones.java
	$(JCC) $(JFLAGS) ServidorPuntuaciones.java

ServidorPuntuaciones.jar: baseDatos.class ServidorPuntuaciones.class
	jar cmf MANIFEST.MF ServidorPuntuaciones.jar baseDatos.class ServidorPuntuaciones.class

###########################################

#Para correr:
run: $(MAIN).class
	$(JVM) -jar ServidorPuntuaciones.jar

#Para eliminar los archivos intermedios de la compilación (.class):
clean:
	$(RM) *.class
	$(RM) *.jar
