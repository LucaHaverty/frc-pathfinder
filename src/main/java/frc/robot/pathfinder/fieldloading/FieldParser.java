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

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.pathfinder.fieldloading.Field.FieldConfig;
import frc.robot.pathfinder.fieldloading.SDFGenerator.Obstacle;

public class FieldParser {
    public static Field parseField(String fieldName) {
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

        JSONObject configJSON = (JSONObject)fieldData.get("config");
        JSONArray obstaclesJSON = (JSONArray)fieldData.get("obstacles");

        ArrayList<Obstacle> obstacles = parseObstacles(obstaclesJSON);
        FieldConfig config = parseConfig(configJSON);

        return new Field(config, obstacles);
    }

    private static ArrayList<Obstacle> parseObstacles(JSONArray obstaclesJSON) {
        ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

        for (Object obstacle : obstaclesJSON) {
            obstacles.add(Obstacle.fromJSON((JSONObject)obstacle));
        }
        return obstacles;
    }

    private static FieldConfig parseConfig(JSONObject config) {
        JSONArray bottomLeftPositionMetersJSON = (JSONArray)config.get("bottomLeftPositionMeters");
        JSONArray fieldSizeMetersJSON = (JSONArray)config.get("fieldSizeMeters");

        Translation2d bottomLeftPositionMeters = new Translation2d(
                (double)bottomLeftPositionMetersJSON.get(0), (double)bottomLeftPositionMetersJSON.get(1));

        Translation2d fieldSizeMeters = new Translation2d(
                (double)fieldSizeMetersJSON.get(0), (double)fieldSizeMetersJSON.get(1));
                
        double nodeSpacingMeters = (double)config.get("nodeSpacingMeters");

        return new FieldConfig(bottomLeftPositionMeters, fieldSizeMeters, nodeSpacingMeters);
    }
}