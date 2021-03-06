package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by jt on 7/3/17.
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {


    private final RecipeReactiveRepository recipeReactiveRepository;

    public ImageServiceImpl( RecipeReactiveRepository recipeReactiveRepository) {

        this.recipeReactiveRepository = recipeReactiveRepository;
    }

    @Override
    public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {
    		
    		Mono<Recipe> recipeMono = recipeReactiveRepository.findById(recipeId)
				.map(rcp -> {
					try {
    						Byte[] byteObjects = new Byte[file.getBytes().length];
    						int i = 0;
    			            for (byte b : file.getBytes()){
    			                byteObjects[i++] = b;
    			            }
    			            rcp.setImage(byteObjects);
    			            return rcp;
					} catch (IOException e) {
			            //todo handle better
			            log.error("Error occurred", e);

			            e.printStackTrace();
			            throw new RuntimeException(e);
			        }
    				});
		
    		recipeReactiveRepository.save(recipeMono.block()).block();
        
        return Mono.empty();
    }
}
