package com.react.task;

import bsh.EvalError;
import bsh.Interpreter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin
public class Controller {
    @PostMapping("api/calculate/report")
    @ResponseBody
    public ResponseEntity<Resource> handleCalculateRequestAndGenerateReport(@RequestParam("expression") String expression) throws EvalError, IOException {
        Interpreter interpreter = new Interpreter();
        if (expression.startsWith("=")) {
            interpreter.eval(expression);
        } else {
            interpreter.eval("result=" + expression);
        }
        File file = FileUtils.saveTextToFile(expression + "=" + interpreter.get("result"));
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping("api/calculate")
    public ResponseEntity<String> handleCalculateRequest(@RequestParam("expression") String expression) throws EvalError {
        Interpreter interpreter = new Interpreter();
        if (expression.startsWith("=")) {
            interpreter.eval(expression);
        } else {
            interpreter.eval("result=" + expression);
        }
        return ResponseEntity.ok(interpreter.get("result").toString());
    }
}
