package com.example.unsplash.controllers;

import com.example.unsplash.models.ResponseObject;
import com.example.unsplash.services.FileUploadService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/upload")
public class FileController {
    private final FileUploadService fileUploadService;

    public FileController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }


    //key,value = file,data
    @PostMapping
    public ResponseEntity<ResponseObject> uploadFile(@RequestBody MultipartFile file) {
        try {
            fileUploadService.storeFile(file);
            return new ResponseEntity<>(
                    new ResponseObject(200, "upload " + file.getOriginalFilename() + " successfully! ", file.getOriginalFilename()),
                    HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(
                    new ResponseObject(HttpStatus.BAD_REQUEST.value(), "upload " + file.getOriginalFilename() + " failed!", null),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("imageName") String imageName)  {
        try {
            byte[] data = fileUploadService.readFile(imageName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.inline().filename(imageName).build());
            return ResponseEntity.ok().headers(headers).body(data);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
