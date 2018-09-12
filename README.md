# Arquitectura Android
Conjunto de artefactos para agilizar el desarrolo

![icon](https://github.com/vladymix/arch_ast/blob/master/AppTest/app/src/main/res/mipmap-xxhdpi/ic_launcher.png)

DateTools
-------------
### Métodos 

```groovy
Calendar getLastHourOfDay() 
Calendar getStartHourOfDay() 

Calendar createCalendar(int dayOfMonth, int monthOfYear, int year)
Date getCurrentDate()

Date getStartYear()
Date getEndYear()

Date getDateFromSqlite(String date)
Date getDateFromString(String date)

String getDateToSqlite(Date date)
String getDateCurrentUTC()
```

ImageTools
-------------

![icon](https://github.com/vladymix/arch_ast/blob/master/recursos/page_lib.png)

### Métodos 

```groovy
Bitmap getCircleBitmap(Resources res, Bitmap source)
Bitmap getCircleBitmap(Bitmap source)
Bitmap getCircleBitmap(Drawable source)

RoundedBitmapDrawable getCircleBitmapDrawable(Resources res, Bitmap source)
RoundedBitmapDrawable getRoundedCornerBitmapDrawable(Resources res, Bitmap source, float cornerRadius)

Bitmap getRoundedCornerBitmap(Drawable drawable, float cornerRadius)
Bitmap getRoundedCornerBitmap(Bitmap bitmap, float cornerRadius)

Bitmap getSquareBitmap(Bitmap bitmap)

Drawable getDrawableChangedColor(Drawable drawable, int color)

String encodeBitmapToBase64(Bitmap image)
Bitmap decodeBase64ToBitmap(String inputBase64)

```

DialogTools
-------------

![icon](https://github.com/vladymix/arch_ast/blob/master/recursos/dialogtools.png)

### Métodos 

```groovy

DialogTools  dialog  = new DialogTools().setTitle("Oh yeah! is time!")
                        .setMessage("Add this dialog to facilitate your application, collaborate in github.com/vladymix")
                        .setImageCenter(getResources().getDrawable(R.drawable.ic_circle_java))
                        .setImageTitle(getResources().getDrawable(R.drawable.ic_android_studio))
                        .setPrimaryButton("Ok", null)
                        .setSecundaryButton("Cancel", null);

             dialog.show(this.getFragmentManager(),"My customDialog");

```

OnBoardScreen
-------------
Pagina de bienvenida


## Como añadir la dependecia a nuestro proyecto?
-------------

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
	implementation 'com.github.vladymix:arch_ast:release_2.3'
}
```
O, puedes descargar manualmente `aar` y colocar en tus proyectos en el directorio `libs`
y añadir la dependencia

```groovy
dependencies {
	compile(name:'[arrFileName]', ext:'aar')
}
```

## License

```
Copyright 2014-2017 Vladymix

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
