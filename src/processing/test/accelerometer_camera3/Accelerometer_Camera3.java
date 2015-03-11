package processing.test.accelerometer_camera3;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ketai.camera.*; 
import ketai.sensors.*; 
import android.view.WindowManager; 
import android.view.View; 
import android.os.Bundle; 
import android.provider.MediaStore; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Accelerometer_Camera3 extends PApplet {

/**
 * <p>Ketai Sensor Library for Android: http://KetaiProject.org</p>
 *
 * <p>KetaiSensor Features:
 * <ul>
 * <li>handles incoming Sensor Events</li>
 * <li>Includes Accelerometer, Magnetometer, Gyroscope, GPS, Light, Proximity</li>
 * <li>Use KetaiNFC for Near Field Communication</li>
 * </ul>
 * <p>Updated: 2012-03-10 Daniel Sauter/j.duran</p>
 */




KetaiCamera cam;

KetaiSensor sensor;
float accelerometerX, accelerometerY, accelerometerZ;
float pre_accelX = 0;
float pre_accelY = 0;
//float pre_accelZ = 0;
float distX, distY, distZ;

float xTolerance = 0.8f;
float xCountMax = 5; 
float xCount;
float captureInterval=0;
float captureInterval2=0;

//float[] avgX = new float[10];
//int currXPos = 0;

public void setup()
{
  sensor = new KetaiSensor(this);
  sensor.start();
  orientation(LANDSCAPE);
  imageMode(CENTER);
  cam = new KetaiCamera(this, 320, 240, 10);
 //cam.setCameraID(0);
 // cam.start();
         

}

public void draw()
{
  image(cam, width/2, height/2);
}


public void keyPressed()
{
  if (cam.isStarted())
  {
    cam.stop();
  }
  else
    cam.start();
}




public void onAccelerometerEvent(float x, float y, float z)
{
  accelerometerX = x;
  accelerometerY = y;
  accelerometerZ = z;
  
  checkSensors();

  
}


public void checkSensors() {
  

    if(accelerometerX < xTolerance){
      xCount++;
      if (xCount > xCountMax && captureInterval == 0){
        xCount = xCountMax;
         Capture();
         captureInterval = 500;
        
  }
      
    } else {
      xCount = 0;
    }
    
   if(captureInterval>0){
    captureInterval --; 
  
  distY = abs(pre_accelY - accelerometerY);
 

  if (distY >= 0 && distY < 0.5f && captureInterval2 ==0) {
    
    Capture();
    captureInterval2 =500;
     
   }
    
  } 
  
  
  
  
  if(captureInterval2>0){
    captureInterval2 --; 
  
  //pre_accelX = accelerometerX;
  pre_accelY = accelerometerY;
 // pre_accelZ = accelerometerZ;
}
}

public void onCameraPreviewEvent()
{
  
  cam.read();
}


public void Capture()
{
    cam.savePhoto();
}



public void onSavePhotoEvent(String filename)
{
  cam.addToMediaLibrary(filename); 
  
}







public void onCreate(Bundle bundle) 
{
  super.onCreate(bundle);
  // fix so screen doesn't go to sleep when app is active
  getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
  getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
}



/*
	available sensors/methods 
	
 * void onSensorEvent(SensorEvent e) - raw android sensor event <br />
 * void onAccelerometerEvent(float x, float y, float z, long a, int b): x,y,z force in m/s^2, a=timestamp(nanos), b=accuracy
 * void onAccelerometerEvent(float x, float y, float z):  x,y,z force in m/s2
 * void onOrientationEvent(float x, float y, flaot z, long a, int b):  x,y,z rotation in degrees, a=timestamp(nanos), b=accuracy
 * void onOrientationEvent(float x, float y, float z) : x,y,z rotation in degrees
 * void onMagneticFieldEvent(float x, float y, float z, long a, int b) : x,y,z geomag field in uT, a=timestamp(nanos), b=accuracy
 * void onMagneticFieldEvent(float x, float y, float z): x,y,z geomagnetic field in uT
 * void onGyroscopeEvent(float x, float y, float z, long a, int b):x,y,z rotation in rads/sec, a=timestamp(nanos), b=accuracy
 * void onGyroscopeEvent(float x, float y, float z): x,y,z rotation in rads/sec
 * void onGravityEvent(float x, float y, float z, long a, int b): x,y,z force of gravity in m/s^2, a=timestamp(nanos), b=accuracy
 * void onGravityEvent(float x, float y, float z): x,y,z rotation in m/s^s
 * void onProximityEvent(float d, long a, int b): d distance from sensor (typically 0,1), a=timestamp(nanos), b=accuracy
 * void onProximityEvent(float d): d distance from sensor (typically 0,1)
 * void onLightEvent(float d, long a, int b): d illumination from sensor in lx
 * void onLightEvent(float d): d illumination from sensor in lx
 * void onPressureEvent(float p, long a, int b): p ambient pressure in hPa or mbar, a=timestamp(nanos), b=accuracy
 * void onPressureEvent(float p): p ambient pressure in hPa or mbar
 * void onTemperatureEvent(float t, long a, int b): t temperature in degrees in degrees Celsius, a=timestamp(nanos), a=timestamp(nanos), b=accuracy
 * void onTemperatureEvent(float t): t temperature in degrees in degrees Celsius
 * void onLinearAccelerationEvent(float x, float y, float z, long a, int b): x,y,z acceleration force in m/s^2, minus gravity, a=timestamp(nanos), b=accuracy
 * void onLinearAccelerationEvent(float x, float y, float z): x,y,z acceleration force in m/s^2, minus gravity
 * void onRotationVectorEvent(float x, float y, float z, long a, int b): x,y,z rotation vector values, a=timestamp(nanos), b=accuracy
 * void onRotationVectorEvent(float x, float y, float z):x,y,z rotation vector values
 * void onAmibentTemperatureEvent(float t): same as temp above (newer API)
 * void onRelativeHumidityEvent(float h): h ambient humidity in percentage
 
*/

}
