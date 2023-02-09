package com.syxu.yaofang.controller;

import com.alibaba.fastjson.JSONObject;
import com.syxu.yaofang.model.Client;
import com.syxu.yaofang.model.Doctor;
import com.syxu.yaofang.model.Page;
import com.syxu.yaofang.model.Result;
import com.syxu.yaofang.pojo.DoctorPoJo;
import com.syxu.yaofang.service.DoctorService;
import com.syxu.yaofang.utils.VerificationCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;


@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    MailProperties mailProperties;

    @PostMapping("/addDoctor")
    private Result addDoctor(Doctor doctor){
        doctor.setPic("/pics/doctor.png");
        int i = doctorService.addDoctor(doctor);
//        BeanUtils.copyProperties(doctorPoJo,doctor);
        if(i>0){
            return Result.success();
        }
        else return Result.error();
    }

    @PostMapping("/cheakDname/{dname}")
    private Result cheakDname(@PathVariable("dname") String dname){
        Doctor doctor = doctorService.findDoctorByName(dname);
        if(doctor!=null){
            return Result.error();
        }
        else return Result.success();
    }

    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        HttpSession session = request.getSession(true);
        request.getServletContext().setAttribute("verify_code_Doctor",text);
        session.setAttribute("verify_code_Doctor",text);

        VerificationCode.output(image,resp.getOutputStream());
    }

    @PostMapping("/queryDoctor")
    private Result queryDoctor(HttpServletRequest req, @RequestParam String name, @RequestParam String password, @RequestParam String code){
        String verify_code = (String) req.getServletContext().getAttribute("verify_code_Doctor");
        if(code == null || verify_code == null || "".equals(code) || !verify_code.toLowerCase().equals(code.toLowerCase())){
            return Result.othereError(3,"验证码错误");
        }else{
            Doctor doctor = doctorService.findDoctorByNameAndPw(name,password);
            String clientInfo = JSONObject.toJSONString(doctor);
            if(doctor == null){
                return Result.othereError(4,"用户名或密码错误");
            }
            else return Result.success(clientInfo);
        }
    }

    @PostMapping("/queryDocList")
    private Result queryDocList(Doctor doctor,@RequestParam(required = false,defaultValue = "1") Integer pageNum, @RequestParam(required = false,defaultValue = "4") Integer pageSize){
       Page<Doctor> doctorPage = doctorService.queryDocList(doctor,pageNum,pageSize);
       if(doctorPage!=null){
           return Result.success(doctorPage);
       }
       else return Result.error();
    }

    @PostMapping("/queryDoctorById/{id}")
    private Result queryDoctorById(@PathVariable("id") Integer id){
        Doctor doctor = doctorService.queryDoctorById(id);
        return Result.success(doctor);
    }

    @PostMapping("/queryDocByBingliId/{bingliId}")
    private Result queryDocByBingliId(@PathVariable("bingliId") Integer bingliId){
        Doctor doctor = doctorService.queryDocByBingliId(bingliId);
        return Result.success(doctor);
    }

    @PostMapping("/updatedoctorMsg")
    public Result updatedoctorMsg(Doctor doctor){
        int i = doctorService.updatedoctorMsg(doctor);
        if(i>0){
            return Result.success();
        }
        else return Result.error();
    }

    @GetMapping("/updatedoctorImg")
    private Result updatedoctorImg(Doctor doctor){
        int i = doctorService.updatedoctorImg(doctor);
        if(i>0){
            return Result.success();
        }
        else return Result.error();
    }

    @PostMapping("/noticeDoctor/{userName}/{email}")
    private Result noticeDoctor(@PathVariable("userName") String userName,@PathVariable("email") String email) throws MessagingException {
        //收到消息，发送邮件
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg,true);
        try {
            //组装发送邮件参数
            String title = "系统提醒";
            helper.setTo(email);
            helper.setFrom(mailProperties.getUsername());
            helper.setSubject(title);
            helper.setSentDate(new Date());
            helper.setText("<p>用户"+userName+"发来了一条病历信息,请及时处理!</p>", true);
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return Result.success();
    }
}
