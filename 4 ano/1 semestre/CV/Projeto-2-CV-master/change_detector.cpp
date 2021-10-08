#include <opencv2/core.hpp>
#include <opencv2/videoio.hpp>
#include <opencv2/highgui.hpp>
#include <opencv2/imgproc.hpp> 
#include <opencv2/imgcodecs.hpp>
#include <opencv2/video.hpp> 
#include <iostream>
#include <sstream>
#include <stdio.h>
#include <iostream>
using namespace std;

void absDiff();
void MOG2_KNN();
void initMOG2_KNN(bool MOG2);
void createOutput(cv::Mat);
void selectAlgorithm(cv::String);
void initCamera(int cameraIndex);

cv::VideoCapture capture;
cv::Ptr<cv::BackgroundSubtractor> pBackSub;
cv::Mat frame, out;
cv::Mat oldFrame;

char algorithm;
cv::String algorithmText;

int MIN_AREA = 500;                     
int UPDATE_RATE = 100;                  
int MOG2history = 500;                  
double MOG2varThreshold = 16.0;         
int KNNhistory = 500;                   
double KNNdist2Threshold = 400.0;       

int main(int argc, char *argv[]){   
    initCamera(0);

    cout << "Press 'ESC'/'q' to quit, 'r' to restart old frame (Abs Diff Only), 1/2/3 for AbsDiff/MOG2/KNN algorithm" << endl << endl;

    selectAlgorithm("AbsDiff");
    
    capture >> oldFrame; 
    long nFrames = 0;
    
    while (true) {
        capture >> frame;
        
        if (frame.empty()) {
            cerr << "ERROR: Can't grab camera frame." << endl;
            break;
        }

        if (algorithm == 0){
            absDiff();
        }
        else{
            MOG2_KNN();
        } 

        imshow("Frame", frame);
        imshow("Result", out);

        int key = cv::waitKey(1);
        if (key == 113 || key == 27) {
            cout << "Bye!" << endl;
            cv::destroyAllWindows();
            capture.release();
            break;
        }
        /* letter r */
        if (key == 114) {
            cout << "Reseting original frame (Abs diff only)" << endl;
            capture >> oldFrame; 
        }
        /* number 1 */
        if (key == 49) {
            selectAlgorithm("AbsDiff");
            cv::destroyAllWindows();
        } 
        /* number 2 */
        if (key == 50) {
            selectAlgorithm("MOG2");
            cv::destroyAllWindows();
        } 
        /* number 3 */
        if (key == 51) {
            selectAlgorithm("KNN");
            cv::destroyAllWindows();
        } 

        if (algorithm == 0) {
            nFrames++;
            imshow("Old Frame", oldFrame);
            if (nFrames % UPDATE_RATE == 0)            {
                capture >> oldFrame; 
            }
        }
    }
}

/*  Based on:
 *  https://docs.opencv.org/3.4/d2/de8/group__core__array.html#ga6fef31bc8c4071cbc114a758a2b79c14
 *  https://answers.opencv.org/question/184025/detecting-change-in-previous-and-current-image-frame/
 *  https://stackoverflow.com/questions/44785958/opencv-detect-changes-between-two-photos-taken-by-different-time
 */

void absDiff() {
    cv::Mat oldFrameGray, frameGray;
    cv::cvtColor(oldFrame, oldFrameGray, CV_BGR2GRAY);
    cv::cvtColor(frame, frameGray, CV_BGR2GRAY);
    
    cv::Mat diff;
    cv::absdiff(oldFrameGray, frameGray, diff);
    
    cv::Mat thresh;
    cv::threshold(diff, thresh, 25, 255, cv::THRESH_BINARY);

    createOutput(thresh);
}

/*  Based on:
 *  https://docs.opencv.org/3.4/d1/dc5/tutorial_background_subtraction.html
 * 
 *  Based on:
 *  https://docs.opencv.org/3.1.0/db/d88/classcv_1_1BackgroundSubtractorKNN.html
 */
void MOG2_KNN() {
    cv::Mat fgMask;
    pBackSub -> apply(frame, fgMask);
    
    cv::Mat thresh;
    cv::threshold(fgMask, thresh, 0, 255, cv::THRESH_BINARY);

    createOutput(thresh);
}

void initMOG2_KNN(bool MOG2) {
    if (MOG2) {
        cout << "MOG2 version (history, threshold) : " << MOG2history << ", " << MOG2varThreshold << endl;
        pBackSub = cv::createBackgroundSubtractorMOG2(MOG2history, MOG2varThreshold);
    }
    else {
        cout << "KNN version (history, threshold) : " << KNNhistory << ", " << KNNdist2Threshold << endl;
        pBackSub = cv::createBackgroundSubtractorKNN(KNNhistory, KNNdist2Threshold);
    }   
}

void createOutput (cv::Mat thresh) {
    /* Based on:
     * https://docs.opencv.org/trunk/dd/d49/tutorial_py_contour_features.html
     * https://docs.opencv.org/trunk/d1/d32/tutorial_py_contour_properties.html
     * https://docs.opencv.org/2.4/modules/imgproc/doc/structural_analysis_and_shape_descriptors.html
    */

    vector<vector<cv::Point>> contours;
    vector<cv::Vec4i> hierarchy;
    cv::findContours(thresh, contours, hierarchy, cv::RETR_EXTERNAL , cv::CHAIN_APPROX_SIMPLE);

    cv::cvtColor(frame, out, CV_BGR2GRAY);
    cv::String text = "No change";

    for (vector<cv::Point> cont: contours) {
        if (cv::contourArea(cont) < MIN_AREA) {
			continue;
        }

        cv::Rect box = boundingRect(cont);
        rectangle(out, box, cv::Scalar(255, 0, 0));
        text = "Detected change";       
    }
    putText(out, algorithmText + " " + text.c_str(), cv::Point(15, 15), cv::FONT_HERSHEY_SIMPLEX, 0.5 , cv::Scalar(0,0,0));
}

void selectAlgorithm (cv::String algorithmStr) {
    if (algorithmStr == "MOG2") {
        initMOG2_KNN(true);
        algorithm = 1; 
        algorithmText = "MOG2";
    }
    else if (algorithmStr == "KNN") {
        initMOG2_KNN(false);
        algorithm = 1;
        algorithmText = "KNN";
    }
    else {
        cout << "Abs Diff version" << endl;
        algorithm = 0;
        algorithmText = "ABSDIFF";
    }
}

void initCamera (int cameraIndex) {
    capture = cv::VideoCapture(cameraIndex);
    if (!capture.isOpened()){
        cerr << "ERROR: Can't initialize camera capture" << endl;
        capture.release();
        exit(-1);
    }

    cout << "Frame " << capture.get(cv::CAP_PROP_FRAME_WIDTH) << " x ";
    cout << capture.get(cv::CAP_PROP_FRAME_HEIGHT);
    cout << ", FPS: " << capture.get(cv::CAP_PROP_FPS) << endl;
}