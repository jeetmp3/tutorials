package tutorials.common.utils;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Jitendra Singh.
 */
public final class ResourceUtils {

    private ResourceUtils() {}

    public static URL readClasspathResource(String filename) {
        return ResourceUtils.class.getClassLoader().getResource(filename);
    }

    public static String readClasspathResourceContent(String filename) throws Exception {
        URL fileUrl = readClasspathResource(filename);
        byte[] fileContent = Files.readAllBytes(Paths.get(fileUrl.toURI()));
        return new String(fileContent);
    }
}
