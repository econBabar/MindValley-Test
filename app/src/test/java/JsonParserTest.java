import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runners.JUnit4;

import java.util.List;

import test.models.Pin;
import test.utils.JsonParser;

/**
 * Created by Babar on 24-Jul-16.
 */
public class JsonParserTest extends TestCase
{
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testParser()
    {
        String sampleJson = "[\n" +
                "\t{\n" +
                "\t\t\"id\": \"4kQA1aQK8-Y\",\n" +
                "\t\t\"created_at\": \"2016-05-29T15:42:02-04:00\",\n" +
                "\t\t\"width\": 2448,\n" +
                "\t\t\"height\": 1836,\n" +
                "\t\t\"color\": \"#060607\",\n" +
                "\t\t\"likes\": 12,\n" +
                "\t\t\"liked_by_user\": false,\n" +
                "\t\t\"user\": {\n" +
                "\t\t\t\"id\": \"OevW4fja2No\",\n" +
                "\t\t\t\"username\": \"nicholaskampouris\",\n" +
                "\t\t\t\"name\": \"Nicholas Kampouris\",\n" +
                "\t\t\t\"profile_image\": {\n" +
                "\t\t\t\t\"small\": \"https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=32&w=32&s=63f1d805cffccb834cf839c719d91702\",\n" +
                "\t\t\t\t\"medium\": \"https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=64&w=64&s=ef631d113179b3137f911a05fea56d23\",\n" +
                "\t\t\t\t\"large\": \"https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=128&w=128&s=622a88097cf6661f84cd8942d851d9a2\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"links\": {\n" +
                "\t\t\t\t\"self\": \"https://api.unsplash.com/users/nicholaskampouris\",\n" +
                "\t\t\t\t\"html\": \"http://unsplash.com/@nicholaskampouris\",\n" +
                "\t\t\t\t\"photos\": \"https://api.unsplash.com/users/nicholaskampouris/photos\",\n" +
                "\t\t\t\t\"likes\": \"https://api.unsplash.com/users/nicholaskampouris/likes\"\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"current_user_collections\": [],\n" +
                "\t\t\"urls\": {\n" +
                "\t\t\t\"raw\": \"https://images.unsplash.com/photo-1464550883968-cec281c19761\",\n" +
                "\t\t\t\"full\": \"https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&s=4b142941bfd18159e2e4d166abcd0705\",\n" +
                "\t\t\t\"regular\": \"https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=1080&fit=max&s=1881cd689e10e5dca28839e68678f432\",\n" +
                "\t\t\t\"small\": \"https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=400&fit=max&s=d5682032c546a3520465f2965cde1cec\",\n" +
                "\t\t\t\"thumb\": \"https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=200&fit=max&s=9fba74be19d78b1aa2495c0200b9fbce\"\n" +
                "\t\t},\n" +
                "\t\t\"categories\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"id\": 4,\n" +
                "\t\t\t\t\"title\": \"Nature\",\n" +
                "\t\t\t\t\"photo_count\": 46148,\n" +
                "\t\t\t\t\"links\": {\n" +
                "\t\t\t\t\t\"self\": \"https://api.unsplash.com/categories/4\",\n" +
                "\t\t\t\t\t\"photos\": \"https://api.unsplash.com/categories/4/photos\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"id\": 6,\n" +
                "\t\t\t\t\"title\": \"People\",\n" +
                "\t\t\t\t\"photo_count\": 15513,\n" +
                "\t\t\t\t\"links\": {\n" +
                "\t\t\t\t\t\"self\": \"https://api.unsplash.com/categories/6\",\n" +
                "\t\t\t\t\t\"photos\": \"https://api.unsplash.com/categories/6/photos\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"links\": {\n" +
                "\t\t\t\"self\": \"https://api.unsplash.com/photos/4kQA1aQK8-Y\",\n" +
                "\t\t\t\"html\": \"http://unsplash.com/photos/4kQA1aQK8-Y\",\n" +
                "\t\t\t\"download\": \"http://unsplash.com/photos/4kQA1aQK8-Y/download\"\n" +
                "\t\t}\n" +
                "\t}]";



        JsonParser parser = new JsonParser();

        List<Pin> pins = parser.parsePins(sampleJson);

        assertNotNull("Pins was null", pins);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
