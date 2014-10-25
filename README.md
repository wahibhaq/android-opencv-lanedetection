android-opencv-lanedetection
============================

This project was developed as a semester project during my MSc. Computer Science at TUM. The Android app detects lanes on road, overlays using a red line and calculate estimated values of Standard Deviation of Lane Position (SDLP) based on position of car in the lain. The motivation of developing this android app as a proof of concept was to help researchers in domain of ergonomics to investigate driver’s behavior in different scenarios. Proposed system acquires the front view using an android phone camera mounted on the vehicle's dashboard and then applying few processes in order to detect the lanes. The "lane detection" code is primarily used from https://github.com/jdorweiler/lane-detection to provide the baseline functionality which is developed to run on Desktop. Code is ported to Android NDK project and then improved further and features added as per project requirement.

This Android project is using the core lane detection module from project "Lane detection using OpenCV" http://www.transistor.io/revisiting-lane-detection-using-opencv.html . I have ported, implemented it in Android NDK, modified and added several optimization techniques to make it efficient for smartphones.

