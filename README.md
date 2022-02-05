## Projeto

Para utilizar o projeto é necessário que alterações sejam feitas no arquivo `persistencia.xml`

> No arquivo altere os valores da `url`, `o banco de dados`, `o drive do banco`, e `senha do banco`

> Mantenha a linha 12 para que crie as tabelas, está como `drop and create`, em caso de já haver criado e for realizar mais teste é imporntante mantê-lo assim
```java
<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
```

