# Recipe Rest Api

Recipe rest api that includes CRUD (**C**reate, **R**ead, **U**pdate, **D**elete) and getByMultipleCriteria operations using spring boot and mongo DB.


## Prerequisites
- Java 11
- [Maven](https://maven.apache.org/guides/index.html)
- [Mongo DB](https://docs.mongodb.com/guides/)


###  Build and Run application
_GOTO >_ **~/absolute-path-to-directory/recipe-service**  
and try below command in terminal
> **```mvn spring-boot:run```** it will run application as spring boot application

or
> **```mvn clean install```** it will build application and create **jar** file under target directory

Run jar file from below path with given command
> **```java -jar ~/path-to-recipe-service/target/recipe-service-0.0.1-SNAPSHOT.jar```**

### Model class
   Below are the model classes which we will store in MongoDB and perform database operations.  

   ```
   public class Recipe {
      @Id
      private String id;
      private String name;
      private String instructions;
      private int numberOfServing;
      private List<Ingredient> ingredients;
   }

   public class Ingredient {
      private String name;
      boolean isVegetarian;
   }
   ```

### Endpoints

#### HTML

|HTTP Method|URL|Description|
|---|---|---|
|`GET`|http://localhost:8080/ | Root page |
|`GET`|http://localhost:8080/swagger-ui/index.html | Swagger UI page |

#### Recipe Service

|HTTP Method|URL|Description|
|---|---|---|
|`POST`|http://localhost:8080/api/v1/recipes | Create new Recipe |
|`PUT`|http://localhost:8080/api/v1/recipes/{id} | Update Recipe by ID |
|`GET`|http://localhost:8080/api/v1/recipes/{id} | Get Recipe by ID |
|`DELETE`|http://localhost:8080/api/v1//recipes/{id} | Delete Recipe by ID |
|`GET`|http://localhost:8080/api/v1/recipes?instructionsLike={instructionsLike}&numberOfServing={numberOfServing}&ingredientIncluded={ingredientIncluded}&ingredientExcluded={ingredientExcluded}&isVegetarian={isVegetarian}&pageNumber={pageNumber}&pageSize={pageSize} | Get Recipes by criteria with Paging.If there is no criteria then all recipes will be returned.|

#### Request Body sample for post and put operations  
    ```
    {
      "name": "Chicken with the onion",
      "instructions": "Bake in the oven",
      "numberOfServing": 2,
      "ingredients": [
            {
            "name": "chicken",
            "vegetarian": false
            },
            {
            "name": "onion",
            "vegetarian": true
            },
            {
            "name": "potatoes",
            "vegetarian": true
            }
         ]
   }
    ``` 
