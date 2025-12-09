package com.coveros.training.flavorhub.service;

import com.coveros.training.flavorhub.model.Recipe;
import com.coveros.training.flavorhub.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing recipes
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RecipeService {
    
    private final RecipeRepository recipeRepository;
    
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }
    
    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }
    
    public List<Recipe> getRecipesByDifficulty(String difficultyLevel) {
        return recipeRepository.findByDifficultyLevel(difficultyLevel);
    }
    
    public List<Recipe> getRecipesByCuisine(String cuisineType) {
        return recipeRepository.findByCuisineType(cuisineType);
    }
    
    public List<Recipe> searchRecipes(String searchTerm) {
        return recipeRepository.findByNameContainingIgnoreCase(searchTerm);
    }
    
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
    
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }
    
    /**
     * Get the recipe of the day based on the current date.
     * Uses a deterministic algorithm to ensure the same recipe is returned throughout the day.
     * The algorithm uses the day of year to select a recipe from the available list.
     * 
     * @return Optional containing the daily recipe, or empty if no recipes exist
     */
    public Optional<Recipe> getDailyRecipe() {
        log.info("Fetching recipe of the day");
        List<Recipe> allRecipes = recipeRepository.findAll();
        
        if (allRecipes.isEmpty()) {
            log.warn("No recipes available for recipe of the day");
            return Optional.empty();
        }
        
        // Use day of year as a deterministic seed
        // This ensures the same recipe is selected throughout the entire day
        LocalDate today = LocalDate.now();
        int dayOfYear = today.getDayOfYear();
        int year = today.getYear();
        
        // Combine year and day for better distribution across years
        int seed = (year * 1000 + dayOfYear) % allRecipes.size();
        
        Recipe dailyRecipe = allRecipes.get(seed);
        log.info("Selected recipe of the day: {} (ID: {})", dailyRecipe.getName(), dailyRecipe.getId());
        
        return Optional.of(dailyRecipe);
    }
    
    /**
     * Find recipes that can be made based on available ingredients in the pantry
     * NOTE: This method is intentionally left incomplete for workshop participants
     * Participants will use GitHub Copilot to implement this recommendation logic
     */
    // TODO: Implement method to recommend recipes based on pantry ingredients
    
    /**
     * Get recipes that match specific dietary requirements or filters
     * NOTE: This is a more advanced feature to be implemented during the workshop
     */
    // TODO: Implement advanced filtering logic
}
