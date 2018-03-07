# Arquitectura Android
Conjunto de artefactos para agilizar la el desarrolo

DateTools
-------------

![icon](https://github.com/vladymix/arch_ast/blob/master/AppTest/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png)

## How to add dependency?

Esta libreria no esta creada en Maven Central, pero podemos hacer uso con  [JitPack](https://jitpack.io)

Añdir maven remoto con la url en `allprojects.repositories`

```groovy
allprojects {
	repositories {
		maven { url "https://jitpack.io" }
	}
}
```
Despues añadimos la dependencia de la libreria

```groovy
dependencies {
	compile 'com.github.vladymix:arch_ast:release_1.1'
}
```
O, puedes descargar manualmente `aar` y colocar en tus proyectos en el directorio `libs`
y añadir la dependencia

```groovy
dependencies {
	compile(name:'[arrFileName]', ext:'aar')
}
```
