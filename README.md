# Arquitectura Android
Conjunto de artefactos para agilizar el desarrolo

![icon](https://github.com/vladymix/arch_ast/blob/master/AppTest/app/src/main/res/mipmap-xxhdpi/ic_launcher.png)


DialogTools
-------------

![icon](https://github.com/vladymix/arch_ast/blob/master/recursos/dialogs.png)

### Métodos 


```groovy
  val dialog = ColorPickerDialog()
      dialog.colorInit = lastColor

      dialog.onColorChangeListener = { color ->
                color?.getAsColor()?.let {
                    view.setBackgroundColor(it)
                }
                lastColor = color
      }

     dialog.show(supportFragmentManager, "cache")
```

```groovy
   val dialog = PasswordDialog(this, "1234", R.style.AppThemePassword)

      dialog.onResultDialogListener = object : DialogResultListener{
                override fun onCorrectPassword(dialog: PasswordDialog, numberIntentsBefore: Int) {
                }

                override fun onErrorPassword(dialog: PasswordDialog, numberIntents: Int) {
                    Toast.makeText(this@DialogsActivity,"Error password", Toast.LENGTH_SHORT).show()
                }
      }
      dialog.show()
```

```groovy
DialogTools  dialog  = new DialogTools().setTitle("Oh yeah! is time!")
                        .setMessage("Add this dialog to facilitate your application, collaborate in github.com/vladymix")
                        .setImageCenter(getResources().getDrawable(R.drawable.ic_circle_java))
                        .setImageTitle(getResources().getDrawable(R.drawable.ic_android_studio))
                        .setPrimaryButton("Ok", null)
                        .setSecundaryButton("Cancel", null);

             dialog.show(this.getFragmentManager(),"My customDialog");

```


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
OnBoardScreen
-------------
Pagina de bienvenida

```groovy
    <com.ast.widgets.OnBoardScreen
        android:id="@+id/onBoardScreen"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

```groovy
        val list = ArrayList<OnBoardItem>()
        list.add(OnBoardItem(R.drawable.ic_bubble_chart,R.string.title1,R.string.description1, R.color.mdtp_accent_color,
            R.color.colorWhite))
        list.add(OnBoardItem(R.drawable.ic_acces_media,R.string.title2,R.string.description1, R.color.light_blue_A700, R.color.mdtp_numbers_text_color))
        this.onBoardScreen.setAdapterDefault(list)

```

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
	implementation 'com.github.vladymix:arch_ast:release_4.5'
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
Copyright 2014-2020 Vladymix

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
