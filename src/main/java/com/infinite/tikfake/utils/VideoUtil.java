package com.infinite.tikfake.utils;


import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Component
public class VideoUtil {

    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;
    @Value("${qiniu.bucket}")
    private String bucket;

    /**
     * 视频存储本地（暂时不用）
     * @param data  视频数据
     * @param title 视频标题
     * @return
     * @throws IOException
     */
    public boolean saveVideoLocal(MultipartFile data, String title) throws IOException {
        //文件上传的地址
        String realPath = ResourceUtils.getURL("classpath:").getPath()+"static/upload/";
        //用于查看路径是否正确
//        System.out.println(realPath);
        if(data.isEmpty()){
            return false;
        }
        data.transferTo(new File(realPath + title));
        return true;
    }

    public String getQiniuURL(String baseUrl){
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.privateDownloadUrl(baseUrl);
    }



    /**
     * 视频存储到七牛云oss
     * @param data
     * @param keyVideo
     * @param keyImg
     */
    public  void saveVideoOnline(MultipartFile data, String keyVideo, String keyImg) {
        // 获取第一帧
        byte[] img;
        try {
            img = fetchFrame(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();
        long expireSeconds = 3600;
        String upTokenImage = auth.uploadToken(bucket, keyImg, expireSeconds, putPolicy);
        String upTokenVideo = auth.uploadToken(bucket, keyVideo, expireSeconds, putPolicy);

        // 上传视频和图片到七牛云
        try {
            uploadManager.put(data.getBytes(), keyVideo, upTokenVideo);
            uploadManager.put(img, keyImg, upTokenImage);
            //解析上传成功的结果
//            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//            System.out.println(putRet.key);
//            System.out.println(putRet.hash);

        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 截取视频帧 作为封面图
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] fetchFrame(MultipartFile file) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(file.getBytes());
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(inputStream);
        ff.start();
        int length = ff.getLengthInFrames();
        int i = 0;
        Frame f = null;
        while (i < length) {
            // 过滤前5帧，避免出现全黑的图片，依自己情况而定
            f = ff.grabFrame();
            if ((i > 5) && (f.image != null)) {
                break;
            }
            i++;
        }
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage BufferedImage = converter.getBufferedImage(f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(BufferedImage, "jpg", out);
        ff.close();
        return out.toByteArray();
    }
}
