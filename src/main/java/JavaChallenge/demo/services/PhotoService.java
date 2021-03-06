package JavaChallenge.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class PhotoService {
    @Value("${my.property}")
    private String directory;

    public String copyPhoto(MultipartFile archive) throws Exception {
        try {
            String namePhoto = archive.getOriginalFilename();
            Path pathPhoto = Paths.get(directory, namePhoto).toAbsolutePath();
            Files.copy(archive.getInputStream(), pathPhoto, StandardCopyOption.REPLACE_EXISTING);
            return namePhoto;
        }catch (IOException e){
            throw new Exception("Error al guardar foto");
        }
    }
}