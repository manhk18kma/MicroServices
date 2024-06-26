package TTCS.PostService.Database.Service;

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


    private String getPublicIdImg(String imageUrl) {
        // Chia URL thành các phần bằng dấu "/"
        String[] parts = imageUrl.split("/");

        // Lấy phần trước dấu "." trong phần cuối cùng của URL
        String publicIDWithFormat = parts[parts.length - 1]; // Image_625737be-5b6c-49ef-be49-bfd1787cd552.jpg
        String[] publicIDParts = publicIDWithFormat.split("\\."); // {"Image_625737be-5b6c-49ef-be49-bfd1787cd552", "jpg"}

        return publicIDParts[0]; // Trả về "Image_625737be-5b6c-49ef-be49-bfd1787cd552"
    }

    public void deleteImage(String imageUrl) {
        try {
            // Lấy public_id từ URL của ảnh
            String publicId = getPublicIdImg(imageUrl);

            // Tạo map tham số để xóa ảnh
            Map<String, Object> params = ObjectUtils.asMap("resource_type", "image");

            // Gọi phương thức destroy để xóa ảnh
            Map<String, Object> result = cloudinary.uploader().destroy(publicId, params);

            // In ra kết quả xóa ảnh
            System.out.println("Kết quả xóa ảnh: " + result);
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            System.out.println("Lỗi khi xóa ảnh");
            e.printStackTrace();
        }
    }

}
