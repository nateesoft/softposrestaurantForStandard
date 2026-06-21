package com.softpos.launcher;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import org.update4j.Configuration;
import org.update4j.FileMetadata;

/**
 * Developer utility — run ONCE on your build/server machine after every new build.
 * Generates update4j-config.xml that clients download to check for updates.
 *
 * Usage:
 *   java -cp "dist/*:lib/*" com.softpos.launcher.GenerateUpdateConfig \
 *        http://YOUR_SERVER/softpos \
 *        /path/to/dist \
 *        /output/path/update4j-config.xml
 *
 * Arguments:
 *   arg[0] = base URL on server  e.g. http://192.168.1.100/softpos
 *   arg[1] = local dist folder   e.g. /home/build/dist  (contains the .jar to publish)
 *   arg[2] = output XML file     e.g. /var/www/html/softpos/update4j-config.xml
 *
 * After running, upload to server:
 *   update4j-config.xml         → http://YOUR_SERVER/softpos/update4j-config.xml
 *   SoftPOSRestaurantForStandart.jar → http://YOUR_SERVER/softpos/SoftPOSRestaurantForStandart.jar
 *
 * REQUIRES: Java 9+ to run (update4j uses JPMS).
 */
public class GenerateUpdateConfig {

    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.err.println("Usage: GenerateUpdateConfig <baseUrl> <distFolder> <outputXmlFile>");
            System.err.println("  e.g. GenerateUpdateConfig http://192.168.1.100/softpos ./dist ./update4j-config.xml");
            System.exit(1);
        }

        String baseUrl    = args[0];  // e.g. http://192.168.1.100/softpos
        String distFolder = args[1];  // e.g. ./dist
        String outputFile = args[2];  // e.g. ./update4j-config.xml

        if (!baseUrl.endsWith("/")) baseUrl += "/";

        System.out.println("Scanning: " + distFolder);
        System.out.println("Base URL: " + baseUrl);

        Configuration config = Configuration.builder()
                .baseUri(baseUrl)
                .basePath("${user.dir}")
                .files(
                    FileMetadata.streamDirectory(distFolder)
                                .map(ref -> ref.classpath(true))
                                .collect(Collectors.toList())
                )
                .build();

        try (Writer writer = Files.newBufferedWriter(Paths.get(outputFile))) {
            config.write(writer);
        }

        System.out.println("Generated: " + outputFile);
        System.out.println("Upload this file and the JAR(s) to your server at: " + baseUrl);
    }
}
