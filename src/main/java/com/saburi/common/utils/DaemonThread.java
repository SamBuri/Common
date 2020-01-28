/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;
//import org.opencv.videoio.VideoCapture;

/**
 *
 * @author CLINICMASTER13
 */
public class DaemonThread implements Runnable {

    @Override
    public void run() {
      
    }

//    protected volatile boolean runnable = false;
//    VideoCapture webSource;
//    Mat frame = new Mat();
//    MatOfByte mem = new MatOfByte();
//    private final ImageView imgView;
//
//    public DaemonThread(VideoCapture webSource, ImageView imgView) {
//        this.imgView = imgView;
//        this.webSource = webSource; 
//
//    }
//
//    @Override
//    public void run() {
//        synchronized (this) {
//            while (runnable) {
//                if (webSource.grab()) {
//                    try {
//                        webSource.retrieve(frame);
//                        Imgcodecs.imencode(".bmp", frame, mem);
//                        
//                        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
//
//                        imgView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
//
//                        if (runnable == false) {
//                            System.out.println("Going to wait()");
//                            this.wait();
//                        }
//
//                    } catch (IOException | InterruptedException ex) {
//                        FXUIUtils.errorMessage(ex);
//                    }
//                }
//            }
//        }
//    }
//    
//    public void setRunnable(boolean runnable){
//      this.runnable = runnable;
//    }
//    
//    public void stop(){
//      this.runnable = false;
//      this.webSource.release();
//    }

}
