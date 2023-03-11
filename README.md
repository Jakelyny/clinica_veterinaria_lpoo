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

## Interface Gráfica

### Tela de login

<hr />
#### Acesso por CPF e senha
![image](https://user-images.githubusercontent.com/85123013/224487879-38fe6daa-5c9f-4ba9-b4dc-b3b607b7be15.png)

#### Validação de acesso
![image](https://user-images.githubusercontent.com/85123013/224487960-482c75da-593d-46b1-b7b7-4a09ed75572d.png)

![image](https://user-images.githubusercontent.com/85123013/224487976-64a4fda5-0ae0-47db-a663-ec4f4ee47e92.png)

<hr />

## Tela da home
![image](https://user-images.githubusercontent.com/85123013/224488029-d8624db8-a3cf-40b7-9293-f68b3dd272c9.png)
<hr />

## Listagem de usuários
![image](https://user-images.githubusercontent.com/85123013/224488073-8b1edc4f-0963-4fa9-9d15-664f1d13a9de.png)

![image](https://user-images.githubusercontent.com/85123013/224488236-2ee151f0-378c-40ee-b53d-5640d426271a.png)


![image](https://user-images.githubusercontent.com/85123013/224488172-ddf37b2b-8273-431d-8ee0-16e6524928bd.png)

<hr />

## Criação e edição de usuários
![image](https://user-images.githubusercontent.com/85123013/224488110-b403d04d-4930-4e21-9037-c41d38b4c390.png)

![image](https://user-images.githubusercontent.com/85123013/224488208-3419660f-c2c4-4ddf-8a85-b877ab30823f.png)

![image](https://user-images.githubusercontent.com/85123013/224488137-479058f6-4269-49ca-bf8c-1f59d30ee6d1.png)









