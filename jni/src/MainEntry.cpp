/**
 * \file main.cc
 * \author Mohamed Aly <malaa@caltech.edu>
 * \date Wed Oct 6, 2010
 *
 */
#include <jni.h>

#include "../header/MainEntry.hh"

//#include "cmdline.h"
//#include "LaneDetector.hh"

#include <stdio.h>
#include <fstream>
#include <sstream>
#include <iostream>
#include <iomanip>
#include <ctime>
#include <string>


#include <opencv2/highgui/highgui.hpp>
#include "opencv2/opencv.hpp"

#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/features2d/features2d.hpp>

#include <vector>


//#include <cv.h>
//#include <highgui.h>

using namespace std;
using namespace cv;




jobjectArray ret;

//JNIEXPORT void JNICALL Java_tum_andrive_lanedetection_TestMain_main
  //(JNIEnv *env, jobject obj, jlong in, jlong out, jobjectArray objArray)
JNIEXPORT jobjectArray JNICALL Java_tum_andrive_lanedetection_TestMain_mainExecute
  (JNIEnv *env, jobject obj, jobjectArray objArray)

{

	 int len = env->GetArrayLength(objArray);
     printf("Length is : %d", len); //need to release this string when done with it in order to

		//jcharArray singleArgument = (*env).NewCharArray(50);
	 jboolean isCopy;
	 char **argv = new char *[len]();

	 for(int j=0; j<len; j++)
		 argv[j] = new char[100];


	for(int i=0; i<len; ++i)
		 {
		     jstring obj = (jstring) env->GetObjectArrayElement(objArray, i);

		     const char *str= (*env).GetStringUTFChars(obj,&isCopy);
		     printf("%s", str); //need to release this string when done with it in order to

		     char *p = new char[100];
		     strcpy(p,str);
		     argv[i] = p;



		     if (isCopy == JNI_TRUE) {
			     (*env).ReleaseStringUTFChars(obj,str);

		     }
		 }




	ret= (jobjectArray)env->NewObjectArray(len,env->FindClass("java/lang/String"),env->NewStringUTF(""));




	 for(int k=0;k<len;k++)
		 env->SetObjectArrayElement(ret,k,env->NewStringUTF(argv[k]));

	    return ret;


}



