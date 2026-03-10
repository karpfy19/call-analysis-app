package com.example.callanalysis.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnalysisController {

    @GetMapping("/")
    public String home(Model model) {
        List<String> transcriptLines = readResourceLines("/transcript.txt");
        String analysisJson = readResourceAsString("/analysis.json");

        model.addAttribute("transcriptLines", transcriptLines);
        model.addAttribute("analysisJson", analysisJson);

        return "index"; // corresponds to src/main/resources/templates/index.html
    }

    /**
     * Reads a resource file line by line into a List<String>
     */
    private List<String> readResourceLines(String resourcePath) {
        InputStream inputStream = getClass().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            return List.of("Error: Could not find " + resourcePath);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            return List.of("Error reading " + resourcePath + ": " + e.getMessage());
        }
    }

    /**
     * Reads a resource file entirely as a single String
     */
    private String readResourceAsString(String resourcePath) {
        InputStream inputStream = getClass().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            return "{}"; // empty JSON
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            return "{}";
        }
    }
}