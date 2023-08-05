# NMS Utilities

Version dependent utilities. Currently, supports all versions from 1.8 to 1.19.2

### Maven
```xml
    <repositories>
        <repository>
            <id>nikl-repo</id>
            <url>https://repo.repsy.io/mvn/nikl/minecraft</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>me.nikl.nmsutilities</groupId>
            <artifactId>nmsutilities</artifactId>
            <version>1.6.0</version>
        </dependency>
    </dependencies>
```

## Adding new nms versions

Check https://www.spigotmc.org/wiki/spigot-nms-and-minecraft-versions-1-16/ for latest nms versions.

From 1.17 on we develop with a remapped Spigot jar. Get one using BuildTools like so `java -jar BuildTools.jar --rev $version --remapped`. To quickly set up all required spigot/craftbukkit versions, go to the directory with your `BuildTools.jar` and run the `install_all_versions.sh` script.
Then create a new module, and the new version to the switch case in the `nmsutilities` module.

## Bump project version

`mvn versions:set -DnewVersion=`
`mvn versions:commit` or `mvn versions:revert`
