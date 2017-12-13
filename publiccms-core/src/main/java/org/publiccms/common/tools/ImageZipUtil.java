package org.publiccms.common.tools;

import java.awt.Image;  
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import org.w3c.dom.Element;  

 

public class ImageZipUtil {  

    /** 
     * 等比例压缩图片文件<br> 先保存原文件，再压缩、上传 
     * @param oldFile  要进行压缩的文件 
     * @param newFile  新文件 
     * @param width  宽度 //设置宽度时（高度传入0，等比例缩放） 
     * @param height 高度 //设置高度时（宽度传入0，等比例缩放） 
     * @param quality 质量 
     * @return 返回压缩后的文件的全路径 
     */  
    public static void zipImageFile(File oldFile,File newFile, int width, int height,  
            float quality) {  
        try {  
            /** 对服务器上的临时文件进行处理 */  
            Image srcFile = ImageIO.read(oldFile);  
            int w = srcFile.getWidth(null);  
            //System.out.println(w);  
            int h = srcFile.getHeight(null);  
            //System.out.println(h);  
            double bili;  
            if(width>0){  
                bili=width/(double)w;  
                height = (int) (h*bili);  
            }else{  
                if(height>0){  
                    bili=height/(double)h;  
                    width = (int) (w*bili);  
                }  
            }  
            /** 宽,高设定 */  
            BufferedImage tag = new BufferedImage(width, height,  
                    BufferedImage.TYPE_INT_RGB);  
            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);  
            //String filePrex = oldFile.getName().substring(0, oldFile.getName().indexOf('.')); 
            //System.out.println(filePrex);  
            /** 压缩后的文件名 */  
            //String newImage = filePrex +  oldFile.getName().substring(filePrex.length());  
            //System.out.println(newImage);  

            /** 压缩之后临时存放位置 */  
            FileOutputStream out = new FileOutputStream(newFile);  
 //           JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
//            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);  
            /** 压缩质量 */  
//            jep.setQuality(quality, true);  
 //           encoder.encode(tag, jep);  
            /** 新的方法 */   
            saveAsJPEG(100, tag, quality, out);  
            out.close();  

        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
    
    /** 
     * 以JPEG编码保存图片 
     * @param dpi  分辨率 
     * @param tag  要处理的图像图片 
     * @param quality  压缩比 
     * @param out 文件输出流 
     * @throws IOException 
     */  
    public static void saveAsJPEG(Integer dpi ,BufferedImage tag, float quality, FileOutputStream out) throws IOException {  
            
        //useful documentation at http://docs.oracle.com/javase/7/docs/api/javax/imageio/metadata/doc-files/jpeg_metadata.html  
        //useful example program at http://johnbokma.com/java/obtaining-image-metadata.html to output JPEG data  
        
        //old jpeg class  
        //com.sun.image.codec.jpeg.JPEGImageEncoder jpegEncoder  =  com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(out);  
        //com.sun.image.codec.jpeg.JPEGEncodeParam jpegEncodeParam  =  jpegEncoder.getDefaultJPEGEncodeParam(tag);  
        
        // Image writer  
//      JPEGImageWriter imageWriter = (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpeg").next();  
        ImageWriter imageWriter  =   ImageIO.getImageWritersBySuffix("jpg").next();  
        ImageOutputStream ios  =  ImageIO.createImageOutputStream(out);  
        imageWriter.setOutput(ios);  
        //and metadata  
        IIOMetadata imageMetaData  =  imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(tag), null);  
           
           
        if(dpi !=  null && !dpi.equals("")){  
               
             //old metadata  
            //jpegEncodeParam.setDensityUnit(com.sun.image.codec.jpeg.JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);  
            //jpegEncodeParam.setXDensity(dpi);  
            //jpegEncodeParam.setYDensity(dpi);  
        
            //new metadata  
            Element tree  =  (Element) imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");  
            Element jfif  =  (Element)tree.getElementsByTagName("app0JFIF").item(0);  
            jfif.setAttribute("Xdensity", Integer.toString(dpi) );  
            jfif.setAttribute("Ydensity", Integer.toString(dpi));  
               
        }  
        
        
        if(quality >= 0 && quality <= 1f){  
        
            //old compression  
            //jpegEncodeParam.setQuality(quality,false);  
        
            // new Compression  
            JPEGImageWriteParam jpegParams  =  (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();  
            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);  
            jpegParams.setCompressionQuality(quality);  
        
        }  
        
        //old write and clean  
        //jpegEncoder.encode(tag, jpegEncodeParam);  
        
        //new Write and clean up  
        imageWriter.write(imageMetaData, new IIOImage(tag, null, null), null);  
        ios.close();  
        imageWriter.dispose();  
        
    }

}