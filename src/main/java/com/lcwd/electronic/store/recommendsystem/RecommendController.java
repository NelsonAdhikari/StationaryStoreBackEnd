package com.lcwd.electronic.store.recommendsystem;

import com.lcwd.electronic.store.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/recommand")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class RecommendController {
    private final RecommendService recommService;

    @PostMapping("/content-tdidf")
    public List<Product> recommandProductsByTitle(@RequestBody String title){
        System.out.println("title"+title);
        return recommService.recommandProductByTitle(title);

    }

}
