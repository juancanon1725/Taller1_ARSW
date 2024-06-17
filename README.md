## Author

Juan Pablo Poveda Cañon

# Taller 1 - INTRODUCTION TO JAVA, MVN, AND GIT: LOC Counting


## Prerequisitos

Maven: Automatiza y estandariza el flujo de vida de la construcción de software
Git: Administrador descentralizado de configuraciones

## Instalación

Para crear el proyecto maven se usa el siguiente comando:

```
mvn archetype:generate -DgroupId=edu.escuelaing.arsw.ASE.app -DartifactId=LOC_Counting_Taller1 -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

![mvn_create](https://github.com/juancanon1725/firstproyect/assets/98672541/966a3cb0-eb91-48a8-ab75-c85bf0f02ac7)

## Ejecución

Para compilar el proyecto se debe usar mvn package.

![image](https://github.com/juancanon1725/Taller1_ARSW/assets/98672541/e2ffcfc2-4adb-427a-a4b8-a8620d6839a1)

Para ejecutar el proyecto utilizamos los comandos:

```
 java -cp src/main/java org.example.CountLines loc "**/*.java"
```

![image](https://github.com/juancanon1725/Taller1_ARSW/assets/98672541/68162998-df61-4d7b-beaa-4498aab91c32)

```
 java -cp src/main/java org.example.CountLines loc "**/*.java"
```

![image](https://github.com/juancanon1725/Taller1_ARSW/assets/98672541/9b64b308-6b3a-46a2-9b5c-187cb03805d0)


## POM actualizado

![image](https://github.com/juancanon1725/firstproyect/assets/98672541/f54e3d1e-01c2-46f3-9315-f9c21d154a61)


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
