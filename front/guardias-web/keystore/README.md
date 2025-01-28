# Generar keystore pkcs12 aqui y extraer el certificado y key

keytool -genkeypair -alias guardiascampweb -keyalg RSA -keysize 2048 -validity 365 -keystore guardiascampweb.p12 -storetype PKCS12

keytool -exportcert -alias guardiascampweb -keystore guardiascampweb.p12 -rfc -file guardiascampweb.crt

openssl pkcs12 -in guardiascampweb.p12 -out guardiascampweb.pem -nodes
