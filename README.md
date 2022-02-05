## Configurações

Para utilizar o projeto é necessário que alterações sejam feitas no arquivo `persistencia.xml`

> No arquivo altere os valores da `url`, `o banco de dados`, `o drive do banco`, e `senha do banco`. Mantenha a linha 12 para que crie as tabelas, está como `drop and create`, em caso de já haver criado e for realizar mais teste é imporntante mantê-lo assim, além disso o `nome do projeto` também deve ser levado em consideração em caso de alteração
```java
<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
```
![image](https://user-images.githubusercontent.com/85123013/152620986-85c15554-ef4d-4e2f-b639-cae6ee80e3c0.png)

> Dados esses que devem ser mantidos iguais no arquivo `PersistenciaJDBC.java`

![image](https://user-images.githubusercontent.com/85123013/152621263-9fa6d8c1-8449-48cc-941d-40db8e0bb555.png)


## Gerando Tabelas

As tabelas são geradas apartir de um teste executado no arquivo `PersistenciaJPA.java`

![image](https://user-images.githubusercontent.com/85123013/152621396-ba9abd39-1c8f-4eee-8fd4-fb43576da809.png)


## Teste de persistência 

Aqui então é onde ocorrem os testes de persistência dos dados, onde ocorre as queries de selesct, update, insert e delete por meio de uma execução do teste de persistência

![image](https://user-images.githubusercontent.com/85123013/152621545-0e347e88-cb0e-4ccd-9bef-ba97e7908c22.png)


