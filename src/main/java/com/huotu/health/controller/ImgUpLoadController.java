package com.huotu.health.controller;

import com.huotu.health.model.UploadImageModel;
import com.huotu.health.service.resource.StaticResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.UUID;

/**
 * 图片上传
 * Created by slt on 2016/11/23.
 */
@Controller
@RequestMapping("/resource")
public class ImgUpLoadController {


    private static final String[] PIC_EXT = {"BMP", "JPG", "JPEG", "PNG", "GIF"};

    @Autowired
    private StaticResourceService staticResourceService;


    @RequestMapping(value = "/uploadMessageImage")
    @ResponseBody
    public UploadImageModel uploadAticleImage(@RequestParam(value = "fileImage") MultipartFile shareImage
            , HttpServletResponse response) throws Exception {
        UploadImageModel resultModel = new UploadImageModel();

        //文件格式判断
        if (ImageIO.read(shareImage.getInputStream()) == null) {
            resultModel.setCode(0);
            resultModel.setMessage("请上传图片文件！");
            return resultModel;
        }

        if (shareImage.getSize() == 0) {
            resultModel.setCode(0);
            resultModel.setMessage("请上传图片！");
            return resultModel;
        }

        //获取图片后缀名
        String fileName = shareImage.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
        Boolean flag = false;

        for (String s : PIC_EXT) {
            if (ext.equals(s)) {
                flag = true;
                break;
            }
        }

        if (!flag) {
            resultModel.setCode(0);
            resultModel.setMessage("文件后缀名非图片后缀名");
            return resultModel;
        }

        //保存图片
        fileName = StaticResourceService.message + "/" + UUID.randomUUID().toString().replace("-", "") + "." + ext.toLowerCase();
        URI uri = staticResourceService.uploadResource(fileName, shareImage.getInputStream());
        response.setHeader("X-frame-Options", "SAMEORIGIN");
        resultModel.setCode(1);
        resultModel.setMessage(uri.toString());
        resultModel.setUrl(uri.toString());
        return resultModel;
    }
}
