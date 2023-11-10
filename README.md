# ChatApp

    - Minima versión de Android: 33
    - SDK: 34

## Instrucciones de Instalación
Para instalar el APK directamente en un dispositivo Android conectado a tu computadora o en un emulador, sigue estos pasos:

### En un Dispositivo Android:

1. Habilita la Depuración USB:

    - En el dispositivo Android, ve a "Configuración" > "Acerca del teléfono" > "Número de compilación".
    - Pulsa repetidamente sobre "Número de compilación" hasta que se active el "Modo desarrollador".
    - Regresa a "Configuración" y encontrarás una nueva opción llamada "Opciones de desarrollador". Ingresa y habilita "Depuración USB".

2. Conecta el Dispositivo a la Computadora:

    - Usa un cable USB para conectar el dispositivo Android a tu computadora.

3. Verifica la Conexión:
    - Abre una terminal o el símbolo del sistema y ejecuta el siguiente comando para verificar que tu dispositivo esté conectado:

        adb devices

    - Asegúrate de que tu dispositivo aparezca en la lista.

2. Instala el APK:

    - Navega a la ubicación donde se encuentra tu APK usando la terminal.
    - Ejecuta el siguiente comando para instalar el APK:

        adb install app-debug.apk


### En un Emulador:

1. Abre el Emulador:

    - Asegúrate de que tu emulador esté en funcionamiento. Puedes iniciar un emulador desde Android Studio si aún no lo has hecho.

2. Instala el APK:

    - Navega a la ubicación donde se encuentra tu APK.
    - Arrastra y suelta el archivo APK en la ventana del emulador.

3. Verifica la Instalación:

    - Verifica en el emulador que tu aplicación se haya instalado correctamente.

## <a href="https://github.com/di-saldana/ChatApp/blob/main/app/build/outputs/apk/debug/app-debug.apk" target="_blank">APK</a>

## <a href="https://youtu.be/i8VWWJFBdWc?feature=shared" target="_blank">Video</a>
