package frc.robot.pathfinder.fieldloading;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.wpi.first.wpilibj.Filesystem;

public class FieldConfigParser {
    public static void parseField(String fieldName) {
        JSONParser parser = new JSONParser();

        JSONObject fieldData = null;
        try {
            fieldData = (JSONObject) parser.parse(new FileReader(new File(Filesystem.getDeployDirectory(), "pathfinder/" + fieldName + ".json")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONObject config = (JSONObject)fieldData.get("config");

        double fieldSizeX = (double)config.get("fieldSizeX");
        double fieldSizeY = (double)config.get("fieldSizeY");

        System.out.println(fieldSizeX + " , " + fieldSizeY);
    }
}

