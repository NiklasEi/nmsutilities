# NMS Utilities

Version dependent utilities. Currently, supports all versions from 1.8 to 1.20.1

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

## Setup

- Requires Java 8 (for mc up to `1.16`) and java 17 (mc >= `1.17`)
- Build tools setup
- In your build tools directory
    - Run the `install_versions_below_1_17` script with java 8 configured
    - Run the `install_versions_1_17_and_up` script with java 17 configured

## Adding new nms versions

Check https://www.spigotmc.org/wiki/spigot-nms-and-minecraft-versions-1-16/ for latest nms versions.
Make sure the required spigot/craftbukkit versions, then create a new module. Add the new version to the switch case in the `nmsutilities` module.

## Bump project version

`mvn versions:set -DnewVersion=`
`mvn versions:commit` or `mvn versions:revert`
