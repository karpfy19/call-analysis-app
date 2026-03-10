package com.example.callanalysis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class AnalysisController {

    @GetMapping("/")
    public String home(Model model) throws Exception {
        // Load transcript
        List<String> transcriptLines = Files.readAllLines(Paths.get("transcript.txt"));

        // Load stage highlights
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = mapper.readValue(Paths.get("analysis.json").toFile(), Map.class);
        List<Map<String, Object>> stageHighlights = (List<Map<String, Object>>) jsonMap.get("stageHighlights");

        // Prepare transcript with highlight info
        List<Map<String, Object>> transcriptWithHighlight = new ArrayList<>();
        for (String line : transcriptLines) {
            boolean highlight = shouldHighlight(line, stageHighlights);
            Map<String, Object> lineMap = new HashMap<>();
            lineMap.put("text", line);
            lineMap.put("highlight", highlight);
            transcriptWithHighlight.add(lineMap);
        }

        model.addAttribute("transcriptWithHighlight", transcriptWithHighlight);
        model.addAttribute("stageHighlights", stageHighlights);

        return "index";
    }

    private boolean shouldHighlight(String line, List<Map<String, Object>> stageHighlights) {
        for (Map<String, Object> stage : stageHighlights) {
            List<String> snippets = (List<String>) stage.get("textSnippets");
            for (String snippet : snippets) {
                if (line.contains(snippet)) {
                    return true;
                }
            }
        }
        return false;
    }
}