package com.portfolio.michael.modules.backup.application.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.portfolio.michael.modules.auth.domain.UserRepository;
import com.portfolio.michael.modules.backup.application.dto.BackupData;
import com.portfolio.michael.modules.education.domain.EducationRepository;
import com.portfolio.michael.modules.experience.domain.ExperienceRepository;
import com.portfolio.michael.modules.proficiencylevel.domain.ProficiencyLevelRepository;
import com.portfolio.michael.modules.project.domain.ProjectRepository;
import com.portfolio.michael.modules.project.domain.ProjectStatusRepository;
import com.portfolio.michael.modules.showcase.domain.ProjectShowcaseRepository;
import com.portfolio.michael.modules.skill.domain.SkillRepository;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BackupServiceImpl implements BackupService {

    private final UserRepository userRepository;
    private final ProficiencyLevelRepository proficiencyLevelRepository;
    private final SkillRepository skillRepository;
    private final ProjectStatusRepository projectStatusRepository;
    private final ProjectRepository projectRepository;
    private final EducationRepository educationRepository;
    private final ExperienceRepository experienceRepository;
    private final ProjectShowcaseRepository showcaseRepository;

    @Value("${app.storage.location}")
    private String storageLocation;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Override
    @Transactional(readOnly = true)
    public void exportBackup(HttpServletResponse response) throws IOException {
        // 1. Collect Data
        BackupData data = BackupData.builder()
                .users(userRepository.findAll())
                .proficiencyLevels(proficiencyLevelRepository.findAll())
                .skills(skillRepository.findAll())
                .projectStatuses(projectStatusRepository.findAll())
                .projects(projectRepository.findAll())
                .educations(educationRepository.findAll())
                .experiences(experienceRepository.findAll())
                .showcases(showcaseRepository.findAll())
                .build();

        // 2. Prepare Response
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"backup_portfolio.zip\"");
        response.setStatus(HttpServletResponse.SC_OK);

        // 3. Write ZIP
        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
            // A. Write JSON Data
            ZipEntry jsonEntry = new ZipEntry("data.json");
            zos.putNextEntry(jsonEntry);
            String jsonString = objectMapper.writeValueAsString(data);
            zos.write(jsonString.getBytes(StandardCharsets.UTF_8));
            zos.closeEntry();

            // B. Write Uploads Folder
            Path uploadsPath = Paths.get(storageLocation);
            if (Files.exists(uploadsPath)) {
                Files.walk(uploadsPath)
                        .filter(path -> !Files.isDirectory(path))
                        .forEach(path -> {
                            String zipFileName = "uploads/" + uploadsPath.relativize(path).toString();
                            ZipEntry zipEntry = new ZipEntry(zipFileName);
                            try {
                                zos.putNextEntry(zipEntry);
                                Files.copy(path, zos);
                                zos.closeEntry();
                            } catch (IOException e) {
                                log.error("Error zipping file: " + path, e);
                            }
                        });
            }
        }
    }

    @Override
    @Transactional
    public void importBackup(MultipartFile file) throws IOException {
        Path uploadsPath = Paths.get(storageLocation);
        BackupData data = null;

        // 1. Unzip to Temp and Parse Data
        Path tempDir = Files.createTempDirectory("backup_restore_");
        try (ZipInputStream zis = new ZipInputStream(file.getInputStream())) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path filePath = tempDir.resolve(entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(filePath);
                } else {
                    Files.createDirectories(filePath.getParent());
                    Files.copy(zis, filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                }

                if (entry.getName().equals("data.json")) {
                    data = objectMapper.readValue(filePath.toFile(), BackupData.class);
                }
                zis.closeEntry();
            }
        }

        if (data == null) {
            throw new IOException("Invalid backup format: data.json not found");
        }

        // 2. Clear Database (Deletion Order: Dependent -> Independent)
        showcaseRepository.deleteAll();
        projectRepository.deleteAll();
        educationRepository.deleteAll();
        experienceRepository.deleteAll();
        skillRepository.deleteAll();
        projectStatusRepository.deleteAll();
        proficiencyLevelRepository.deleteAll();
        userRepository.deleteAll();

        // 3. Restore Database (Insertion Order: Independent -> Dependent)
        if (data.getUsers() != null)
            userRepository.saveAll(data.getUsers());
        if (data.getProficiencyLevels() != null)
            proficiencyLevelRepository.saveAll(data.getProficiencyLevels());
        if (data.getProjectStatuses() != null)
            projectStatusRepository.saveAll(data.getProjectStatuses());
        if (data.getSkills() != null)
            skillRepository.saveAll(data.getSkills());
        if (data.getProjects() != null)
            projectRepository.saveAll(data.getProjects());
        if (data.getEducations() != null)
            educationRepository.saveAll(data.getEducations());
        if (data.getExperiences() != null)
            experienceRepository.saveAll(data.getExperiences());
        if (data.getShowcases() != null)
            showcaseRepository.saveAll(data.getShowcases());

        // 4. Restore Files
        Path extractedUploads = tempDir.resolve("uploads");
        if (Files.exists(extractedUploads)) {
            // Clear current uploads
            if (Files.exists(uploadsPath)) {
                try (java.util.stream.Stream<Path> walk = Files.walk(uploadsPath)) {
                    walk.sorted(java.util.Comparator.reverseOrder())
                            .map(Path::toFile)
                            .forEach(java.io.File::delete);
                }
            }
            Files.createDirectories(uploadsPath);

            // Move new uploads
            try (java.util.stream.Stream<Path> walk = Files.walk(extractedUploads)) {
                walk.filter(p -> !Files.isDirectory(p))
                        .forEach(source -> {
                            try {
                                Path target = uploadsPath.resolve(extractedUploads.relativize(source));
                                Files.createDirectories(target.getParent());
                                Files.move(source, target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException e) {
                                throw new RuntimeException("Failed to move file", e);
                            }
                        });
            }
        }

        // Cleanup Temp
        try (java.util.stream.Stream<Path> walk = Files.walk(tempDir)) {
            walk.sorted(java.util.Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(java.io.File::delete);
        }

    }
}
