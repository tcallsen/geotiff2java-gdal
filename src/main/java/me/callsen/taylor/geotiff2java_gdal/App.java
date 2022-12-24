package me.callsen.taylor.geotiff2java_gdal;

import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.gdal;
import org.gdal.osr.CoordinateTransformation;
import org.gdal.osr.SpatialReference;

public class App {

  public static void main( String[] args ) throws Exception {

    // based on: https://gis.stackexchange.com/questions/349760/get-elevation-of-geotiff-using-gdal-bindings-in-java

    // read geotiff file and raster band
    gdal.AllRegister();
    Dataset dataset = gdal.Open("/development/workspace/USGS_13_n38w123_uncomp.tif");
    Band band = dataset.GetRasterBand(1);
    
    // convert supplied lat long coordinates to tiff x/y pixels
    SpatialReference src = new SpatialReference();
    src.SetWellKnownGeogCS("WGS84");
    String projection = dataset.GetProjection();
    SpatialReference dst = new SpatialReference(projection);
    CoordinateTransformation ct = new CoordinateTransformation(src, dst);
    // https://gis.stackexchange.com/questions/245472/convert-latitude-longitude-pair-to-pixels-in-geotiff
    // https://gis.stackexchange.com/questions/221292/retrieve-pixel-value-with-geographic-coordinate-as-input-with-gdal
    double lat = 37.75497;
    double lon = -122.44580;
    double[] xy = ct.TransformPoint(lon, lat); // longitude supplied first
    double[] transform = dataset.GetGeoTransform();
    int xPixel = (int)(((xy[0] - transform[0]) / transform[1]));
    int yPixel = (int)(((xy[1] - transform[3]) / transform[5]));

    // sample raster data at pixel coords
    double[] rasterData = new double[1];
    band.ReadRaster(xPixel, yPixel, 1, 1, rasterData);

    System.out.println(String.format("GeoTIFF data at %s, %s: %s", lat, lon, rasterData[0]));
  }

}
