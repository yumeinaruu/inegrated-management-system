package com.yumeinaruu.iis.controller;

import com.yumeinaruu.iis.model.dto.file.FileDownloadDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
@Tag(name = "Work with files", description = "Methods to work with files")
@SecurityRequirement(name = "Bearer Authentication")
@Slf4j
public class FileController {
    private final Path ROOT_FILE_PATH = Paths.get("data");

    @Operation(summary = "Uploads a file")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "file uploaded successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PostMapping(name = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> upload(@RequestParam("file") MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), ROOT_FILE_PATH.resolve(file.getOriginalFilename()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            log.warn(e + "");
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Operation(summary = "Returns a file")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "File returned successfully"),
            @ApiResponse(responseCode = "404", description = "File not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @PostMapping("/filename")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<Resource> getFile(@RequestBody FileDownloadDto fileDownloadDto) {

        Path path = ROOT_FILE_PATH.resolve(fileDownloadDto.getFileName());
        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            }
        } catch (MalformedURLException e) {
            log.warn(e + "");
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Returns all file names")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "File names returned successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<ArrayList<String>> getFiles() {
        try {
            ArrayList<String> filenames = (ArrayList<String>) Files.walk(this.ROOT_FILE_PATH, 1).filter(path -> !path.equals(this.ROOT_FILE_PATH)).map(Path::toString).collect(Collectors.toList());
            return new ResponseEntity<>(filenames, HttpStatus.OK);
        } catch (IOException e) {
            log.warn(e + "");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
    }

    @Operation(summary = "Deletes a file")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "File was deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "403", description = "You have no rights to this resource"),
            @ApiResponse(responseCode = "409", description = "Some error from your/our side")
    })
    @DeleteMapping("/filename")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<HttpStatus> deleteFile(@RequestBody FileDownloadDto fileDownloadDto) {
        Path path = ROOT_FILE_PATH.resolve(fileDownloadDto.getFileName());

        File file = new File(path.toString());
        if (file.delete()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
