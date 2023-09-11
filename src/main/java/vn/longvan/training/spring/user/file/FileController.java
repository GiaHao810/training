package vn.longvan.training.spring.user.file;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.longvan.training.spring.user.model.ResponseObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/file/image")
public class FileController {

    private final String defaultFolderPath = "C:/LongVan/Upload/image/";

    @PostMapping("")
    ResponseEntity<ResponseObject> handleFileUpload(@RequestParam("file")MultipartFile multipartFile){
        String fileName = multipartFile.getOriginalFilename();
        File fileUrl = getFolderUpload();
        try {
            File file = new File(fileUrl, fileName);
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "File Uploaded", fileUrl + "\\" + fileName)
        );
    }

    public File getFolderUpload() {
        File folderUpload = new File(defaultFolderPath);
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) throws IOException {
        try {
            Path filePath = Paths.get(defaultFolderPath).resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
