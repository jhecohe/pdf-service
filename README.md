* PDF SERVICE

** Execute program

*** Run the program
./mvnw spring-boot:run

*** Make the query

curl --location 'http://localhost:8080/generate-pdf' \
--header 'Content-Type: application/json' \
--data '{
    "nombre": "Camilo Salguero",
    "cedula": 3453234,
    "ciudad": "bogota",
    "entidadDemandada": "ONU",
    "diagnostico": "Negacion de servicios",
    "tratamiento": "Proceso de aplicacion"
}'