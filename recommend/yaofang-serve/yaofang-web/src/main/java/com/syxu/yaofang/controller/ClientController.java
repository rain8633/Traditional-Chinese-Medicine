package com.syxu.yaofang.controller;

import com.alibaba.fastjson.JSONObject;
import com.syxu.yaofang.model.Client;
import com.syxu.yaofang.model.MailConstants;
import com.syxu.yaofang.model.MailSendLog;
import com.syxu.yaofang.model.Result;
import com.syxu.yaofang.service.ClientService;
import com.syxu.yaofang.service.Impl.ClientServiceImpl;
import com.syxu.yaofang.service.Impl.MailSendLogService;
import com.syxu.yaofang.utils.MD5Util;
import com.syxu.yaofang.utils.VerificationCode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class ClientController {

    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    private MailSendLogService mailSendLogService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ResponseBody
    @GetMapping("/cheakUsername/{userName}")
    public Result cheakUsername(@PathVariable("userName") String userName){
         Client client = clientService.findClientByName(userName);
         if(client != null){
             return Result.error();
         }
         else return Result.success();
    }

    @ResponseBody
    @PostMapping("/queryUser")
    public Result queryUser(HttpServletRequest req,@RequestParam String userName,@RequestParam String password,@RequestParam String code){
        String verify_code = (String) req.getServletContext().getAttribute("verify_code");
        if(code == null || verify_code == null || "".equals(code) || !verify_code.toLowerCase().equals(code.toLowerCase())){
            return Result.othereError(3,"验证码错误");
        }else{
            Client client = clientService.findClientByNameAndPw(userName,password);
            String clientInfo = JSONObject.toJSONString(client);
            if(client == null){
                return Result.othereError(4,"用户名或密码错误");
            }
            else if(client.getEmailStatus()==0){
                return Result.othereError(4,"请先去邮箱激活");
            }
            else return Result.success(clientInfo);
        }
    }

    @ResponseBody
    @GetMapping("/cheakEmail/{email}")
    public Result cheakEmail(@PathVariable("email") String email){
        Client client = clientService.findClientByEmail(email);
        if(client != null){
            return Result.error();
        }
        else return Result.success();
    }

    @ResponseBody
   @PostMapping("/addUser")
   public Result addUser(@RequestBody Client client){
        String email = client.getEmail();
        String validateCode = MD5Util.md5Encrypt32Upper(email);
        client.setValidateCode(validateCode);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        client.setCreateTime(new Date());
        client.setUpdateTime(new Date());
        int i = clientService.addClient(client);
        if(i>0){
            Client con = clientService.findClientByName(client.getUserName());
            //生成消息的唯一id
            String msgId = UUID.randomUUID().toString();
            MailSendLog mailSendLog = new MailSendLog();
            mailSendLog.setMsgId(msgId);
            mailSendLog.setCreateTime(new Date());
            mailSendLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailSendLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            mailSendLog.setConId(con.getId());
            mailSendLog.setTryTime(new Date(System.currentTimeMillis() + 1000 * 60 * MailConstants.MSG_TIMEOUT));
            mailSendLogService.insert(mailSendLog);
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY_NAME, con, new CorrelationData(msgId));

            return Result.success(email);
        } else {
            return Result.error();
        }
   }

    @ResponseBody
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        HttpSession session = request.getSession(true);
        request.getServletContext().setAttribute("verify_code",text);
        session.setAttribute("verify_code",text);

        VerificationCode.output(image,resp.getOutputStream());
    }

    @ResponseBody
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
//        String extension = FilenameUtils.getExtension();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String prefix = UUID.randomUUID().toString().replaceAll("-", "");
        String newName=prefix+suffix;
        String path="H:/upload";

        file.transferTo(new File(path,newName));
        return Result.success("/pics/"+newName);
    }

    /**
     * 激活用户状态
     *
     * @param email
     * @param validateCode
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/activation", method = RequestMethod.GET)
    public String activation(String email, String validateCode, Model model) throws ParseException {

        Object baseResponse = clientService.activation(email, validateCode);

        String jsonStr= JSONObject.toJSONString(baseResponse);

        JSONObject jsonObject=JSONObject.parseObject(jsonStr);
        model.addAttribute("url", "http://localhost:8080");

        if (jsonObject.containsKey("code")) {
            return "email/dmail";
        } else {
            model.addAttribute("error",jsonObject.getString("msg"));
            return "email/error";
        }

    }
}
