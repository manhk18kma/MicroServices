package TTCS.ProfileService.infranstructure.persistence.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class UploadImageServiceImpl {
    private  Cloudinary cloudinary;
    @Autowired
    public UploadImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile multipartFile, String name) {
        String url = "";
        try {
            Map<String, Object> params = ObjectUtils.asMap("public_id", name);
            Map<String, Object> uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), params);
            url = uploadResult.get("url").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }


    private String getPublicIdImg(String imageUrl){
//        String[] parts = imageUrl.split("/");
//        String publicIDWithFormat = parts[parts.length-1];
//        String[] publicID
        return  null;
    }

    public void deleteImage(String imgUrl) {
//        try{
//            String publicId = getPublicIdImg(imageUrl);
//            cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("resource_type", "image"));
//        } catch (Exception e) {
//            System.out.println("Lỗi khi xoá ảnh");
//            e.printStackTrace();
//        }

    }
}
