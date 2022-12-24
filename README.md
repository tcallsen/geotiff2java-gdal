# Read GeoTIFF Data with Java GDAL bindings

Demonstrates how to read GeoTIFF data using the Java GDAL bindings. Raster data is retrieved for a particular latitude/longitude.

## Installing GDAL Java bindings

Installing the bindings generally involves building the GDAL library from source with special parameters to enable the bindings. Luckily on Ubuntu `18.04` and `20.04`, installing the [libgdal-java](https://packages.ubuntu.com/focal/libgdal-java) package will install `.so` and `.jar` files required for the Java bindings. This works well when paied with the `gdal-bin` package (also installed via `aptitude`).

Once the `.jar` file is available, it must be added into the Java Class Path. For this project, that was done by pointing to the `.jar` in the `pom.xml`:

```
<dependency>
  <groupId>org.gdal</groupId>
  <artifactId>gdal</artifactId>
  <version>3.0.4</version>
  <scope>system</scope>
  <systemPath>/usr/share/java/gdal.jar</systemPath>
</dependency>
```

## GeoTools and Other Libraries

I also experimented with GeoTools and a few other libraries used to read GeoTIFF files. These required much less setup (no bindings to install/configure), but I did run into limitations when reading compressed GeoTIFF files. 

Here is a sample project using GeoTools to read a GeoTIFF file: [https://github.com/tcallsen/geotiff2java](https://github.com/tcallsen/geotiff2java)