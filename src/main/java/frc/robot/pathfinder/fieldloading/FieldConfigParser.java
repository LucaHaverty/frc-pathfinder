package frc.robot.pathfinder.fieldloading;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.pathfinder.fieldloading.SDFGenerator.Obstacle;

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
        JSONArray JSONObstacles = (JSONArray)fieldData.get("obstacles");

        ArrayList<Obstacle> obstacles = parseObstacles(JSONObstacles);

        System.out.println(obstacles.get(0).position);
    }

    private static ArrayList<Obstacle> parseObstacles(JSONArray JSONObstacles) {
        ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

        for (Object obstacle : JSONObstacles) {
            obstacles.add(Obstacle.fromJSON((JSONObject)obstacle));
        }
        return obstacles;
    }

    private static void parseConfig(JSONObject config) {
        // TODO: create and return object to store config
    }
}

