# Recipe Project

## Description

This project is a collection of delicious recipes which allows users to manage their favorite recipes. Each recipe includes a list of ingredients,number of servings,receipe is vegetarian,ingredients, instructions.

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
  
