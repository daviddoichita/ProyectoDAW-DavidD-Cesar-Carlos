# Generar en esta carpeta un keystore en formato PKCS12

keytool -genkey -alias guardiascamp -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore guardiascamp.p12 -validity 3650
