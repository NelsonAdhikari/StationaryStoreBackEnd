package com.lcwd.electronic.store.recommendsystem;

import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class RecommendService {
    private final ProductRepository productRepository;

    public List<Product> recommandProductByTitle(String title) {
        System.out.println("I am here: "+title);
        List<Product> allProducts = (List<Product>) productRepository.findAll();
        List<String> tokenedQuery = Arrays.asList(title.toLowerCase().split("\\s+"));

        Map<Product, Double> productSimilarities = new HashMap<>();
        for(Product product: allProducts){
            List<String> tokenizedTitle = Arrays.asList(product.getTitle().toLowerCase().split("\\s+"));
            long termFrequency = tokenizedTitle.stream()
                    .filter(tokenedQuery::contains)
                    .count();
            long documentFrequency = allProducts.stream()
                    .filter(p -> p.getTitle().toLowerCase().contains(tokenedQuery.get(0)))
                    .count();
            double tfidfScore = (termFrequency/(double) tokenizedTitle.size())*
                    Math.log(allProducts.size()/(double) (documentFrequency + 1));

            productSimilarities.put(product, tfidfScore);
        }

        return productSimilarities.entrySet()
                .stream()
                .sorted(Map.Entry.<Product, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }



}
