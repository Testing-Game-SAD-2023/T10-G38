package com.taskt6.editor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class EditorController {

  private String path = "/FileSystem/";
  private String classname;

  @GetMapping("/")
  public String getHomePage(@RequestParam(name = "myParam") String class_name, @RequestParam(name = "id") String id, @RequestParam(name = "robot") String robot) {

    classname = class_name;

    System.out.println("Parameter value: " + class_name);
    return "code-editor.html";
  }

  @PostMapping("/class")
  public String postClass(@RequestBody String testClass, @RequestParam(name = "id") String id,
      @RequestParam(name = "gameid") String gameid) {
    String filename = "AUT" + classname;
    if (testClass != null) {

      try {
        String path_comp = path + filename.replace(".java", "") + "/" + id + "/" + gameid + "/TestSourceCode";
        new File(path_comp).mkdirs();
        FileWriter myWriter = new FileWriter(path_comp + "/" + filename);
        myWriter.write(testClass);
        myWriter.close();
        System.out.println("Successfully wrote to the file.");
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }

    }
    return "Success";
  }

  @CrossOrigin(origins = "*")
  @PostMapping("/original")
  public String postOriginal(@RequestBody String testClass) {
    String filename = classname;
    if (testClass != null) {

      try {
        String path_comp = path + "AUT" + filename.replace(".java", "") + "/AUT"
            + filename.replace(".java", "SourceCode");
        new File(path_comp).mkdirs();
        FileWriter myWriter = new FileWriter(path_comp + "/" + filename);
        myWriter.write(testClass);
        myWriter.close();
        System.out.println("Successfully wrote to the file.");
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }

    }
    return "Success";
  }

}
