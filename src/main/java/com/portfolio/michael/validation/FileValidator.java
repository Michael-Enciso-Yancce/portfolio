package com.portfolio.michael.validation;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private List<String> allowedTypes;
    private List<String> allowedExtensions;
    private long maxSize;

    @Override
    public void initialize(ValidFile constraint) {
        this.allowedTypes = Arrays.asList(constraint.allowedTypes());
        this.allowedExtensions = Arrays.asList(constraint.allowedExtensions());
        this.maxSize = constraint.maxSize();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

        // 1) Si no hay archivo no falla (opcional)
        if (file == null || file.isEmpty()) {
            return true;
        }

        // 2) Tamaño máximo
        if (maxSize > 0 && file.getSize() > maxSize) {
            // Podemos personalizar el mensaje aquí si quisiéramos
            return false;
        }

        try {
            // 3) Validar tipo MIME real usando Tika
            Tika tika = new Tika();
            String detectedType = tika.detect(file.getInputStream());

            if (!allowedTypes.isEmpty() && !allowedTypes.contains(detectedType)) {
                return false;
            }

            // 4) Validar extensión opcional
            if (!allowedExtensions.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                if (originalFilename == null) {
                    return false;
                }

                String ext = originalFilename.replaceAll("^.*\\.", "").toLowerCase();

                if (!allowedExtensions.contains(ext)) {
                    return false;
                }
            }

            return true;

        } catch (IOException e) {
            return false;
        }
    }
}
