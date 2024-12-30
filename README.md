# Recipe Project

## Description

This project is a collection of delicious recipes which allows users to manage their favorite recipes. Each recipe includes a list of ingredients,number of servings,receipe is vegetarian,ingredients, instructions.

## Recipe Management API YAML

Within the resources/yaml folder, you'll find the RecipeAPI_v1.yaml file, which details all the endpoints provided by the Recipe Application.

![image](https://github.com/user-attachments/assets/1be330ac-7770-4f58-9dff-92fc1906bf0a)



## Features

- Whether or not the dish is vegetarian
- The number of servings
- Specific ingredients (either include or exclude)
- Text search within the instructions.
- All vegetarian recipes
- Recipes that can serve 4 persons and have “potatoes” as an ingredient
- Recipes without “salmon” as an ingredient that has “oven” in the instructions. 


## Requirements

- Eclipe/Intellij
- Java17
- Maven

## Steps to Run the Application

1. **Clone the repository:**
   ```bash
   git clone https://github.com/SakshiMahajan899/recipe.git
   cd recipe

2. **Build the project:**
   mvn clean install

3. **Run the application:**
   mvn spring-boot:run

4. **Access the application:**
   http://localhost:8080

## Using H2 Database 
 
  This project uses an H2 in-memory database for development and testing purposes. 

  ### Accessing H2 Database Console

   - Go to http://localhost:8080/h2-console.
   - JDBC Url is jdbc:h2:mem:recipedb
   - User Name: recipe
   - Password: recipe
   - Click on "Connect" to access the H2 database console.

## Spring Security  
   
   - This project uses Spring Security to secure the application and manage user authentication and authorization.
   - The securityFilterChain method configures HTTP security and allowing HTTP Basic authentication for /api/recipes/**                endpoints.
   - To access the secure endpoints, you'll need to provide authentication credentials (username and password).
   - For production environments, consider using a more robust authentication mechanism. 

 

## API Endpoints

### Get All Recipes

- **URL:** `/api/recipes`
- **Method:** `GET`
- **Description:** Retrieve a list of all recipes.
- **Response:**
  ```json
  [
     {
        "id": 1,
        "name": "Chicken Alfredo",
        "ingredients": [
            "chicken, fettuccine, cream, parmesan, garlic"
        ],
        "servings": 2,
        "instructions": "Cook fettuccine. In a separate pan, sauté garlic, then add cream and cheese. Add cooked chicken and fettuccine.",
        "vegetarian": false
    }
  ]

### Add New Recipe

- **URL:** `/api/recipes`
- **Method:** `POST`
- **Description:** Add a new recipe to the collection..
- **Request Body:**
  ```json
  {
        "name": "Spaghetti Bolognese",
        "ingredients": [
            "spaghetti",
           "tomato sauce"
           ],
        "servings": 2,
        "instructions": "Cook spaghetti. Mix with tomato sauce. Combine and serve",
        "vegetarian": true
    }
- **Response:**
  ```json
  {
    "id": 4,
    "name": "Spaghetti Bolognese",
    "ingredients": [
        "spaghetti",
        "tomato sauce"
    ],
    "servings": 2,
    "instructions": "Cook spaghetti. Mix with tomato sauce. Combine and serve",
    "vegetarian": true
   }


### Update Recipe

- **URL:** `/api/recipes/:id`
- **Method:** `PUT`
- **Description:** Update an existing recipe by its ID.
- **Request Body:**
  ```json
  {
    "name": "Potato Gratin",
    "ingredients": [
        "potatoes, cream, cheese, garlic, thyme"
    ],
    "servings": 4,
    "instructions": "Layer potatoes with cream and cheese, bake until golden and bubbly.",
    "vegetarian": true
   }
- **Response:**
  ```json
  {
    "id": 1,
    "name": "Potato Gratin",
    "ingredients": [
        "potatoes, cream, cheese, garlic, thyme"
    ],
    "servings": 4,
    "instructions": "Layer potatoes with cream and cheese, bake until golden and bubbly.",
    "vegetarian": true
   }



### Delete Recipe

- **URL:** `/api/recipes/:id`
- **Method:** `DELETE`
- **Description:** Delete a specific recipe by its ID.



### Filter Recipes

#### All Vegetarian Recipes

- **URL:** `/api/recipes/filter`
- **Method:** `GET`
- **Description:** Retrieve all vegetarian recipes.
- **Query Parameter:** `vegetarian=true`
- **Response:**
  ```json
  [
    {
      "id": 1,
      "name": "Vegetable Stir Fry",
      "ingredients": ["broccoli", "carrots", "bell peppers", "soy sauce"],
      "servings": 2,
      "instructions": "Stir fry vegetables in a pan with soy sauce.",
      "vegetarian": true,
    }
  ]

#### Serve 4 Persons and Have "Potatoes" as an Ingredient

- **URL:** `/api/recipes/filter`
- **Method:** `GET`
- **Description:** Retrieve recipes that can serve 4 persons and contain "potatoes" as an ingredient.
- **Query Parameter:** `servings=4`
- **Query Parameter:** `ingredientInclude=Potatoes` 
- **Response:**
  ```json
  [
    {
      "id": 1,
      "name": "Vegetable Stir Fry",
      "ingredients": ["Potatoes", "carrots", "bell peppers", "soy sauce"],
      "vegetarian": true,
      "servings": 4,
      "instructions": "Stir fry vegetables in a pan with soy sauce."
    }
  ]

#### Recipes Without "Salmon" as an Ingredient and Have "Oven" in the Instructions

- **URL:** `/api/recipes/filter`
- **Method:** `GET`
- **Description:** Retrieve recipes without "salmon" as an ingredient that have "oven" in the instructions.
- **Query Parameter:** `instructionsText=oven`
- **Query Parameter:** `ingredientExclude=Salmon` 
- **Response:**
  ```json
  [
    {
        "id": 1,
        "name": "Spaghetti",
        "ingredients": [
            "spaghetti",
            "tomato sauce"
        ],
        "vegetarian": true,
        "servings": 2,
        "instructions": "Cook spaghetti. Mix with tomato sauce. Put in oven for 5 minutez  and serve"
    }
  ]

## Test Coverage

   ### Unit Tests (UT) & Integration Tests (IT)

1. **Scope**
   - Focuses on individual functions, methods, or classes.
   - Focuses on the interactions between units and their dependencies.

3. **Coverage**
   - Aims to cover all possible code paths, including edge cases.
   - Aims to cover all critical integration points and workflows.

4. **Framework**
   - Mockito

Test Coverage with UT and IT is more than 90% for Recipe Application.
![image](https://github.com/user-attachments/assets/731141f3-2748-46c0-8124-045d7ee990ec)



## Continuous Integration and Deployment

Within the recipe/.github/workflows/ directory, you'll find the ci-cd.yml file, which is used to build the code & deploy the image to dockerHub.

![image](https://github.com/user-attachments/assets/3faaa41c-1fb3-4362-a7fd-3df0e91a9ad2)


  - Also refer pipeline link https://github.com/SakshiMahajan899/recipe/actions/runs/12552128743/job/34997623342
  - Also refer the below screenprint for the tag creation of the Recipe Application in DockerHub
    ![image](https://github.com/user-attachments/assets/e0c6937b-8ce9-46d2-9117-e7f214c9aee7)

