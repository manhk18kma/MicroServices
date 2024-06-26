package TTCS.PostService.Database.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {

    UploadImageServiceImpl uploadImageService;
    public String getUrl(String urlAtv , String idPost){
        String base64 = urlAtv;
        MultipartFile multipartFileAvt = Base64ToMultipartFileConverter.convert(base64);
        String urlAvt = uploadImageService.uploadImage(multipartFileAvt, "Image_" + idPost);
        return urlAvt;
    }
}
