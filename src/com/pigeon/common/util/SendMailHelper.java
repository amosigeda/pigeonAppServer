package com.pigeon.common.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 * 邮件发送工具类
 * 
 * @author fengmb 2016/12/28
 */
public class SendMailHelper {
	
	/** 日志实例 */
	private static final Logger logger = Logger.getLogger(SendMailHelper.class);

	//发送邮件服务器地址
	private String VALUE_SMTP = null;//smtp.163.com
	//发送邮件服务器端口
	private String PORT = null;
	//发件人用户名、密码
	private String SEND_USER = null;//
	private String SEND_PWD = null;//
	//连接邮件服务器超时时间
	private String mail_timeout = null;
	
	private MimeMessage message;
	private Session session;

	public SendMailHelper() {
		VALUE_SMTP = PropertiesFileUtils.getProperties().getProperty("mail_stmp");
		PORT = PropertiesFileUtils.getProperties().getProperty("mail_port");
		SEND_USER = PropertiesFileUtils.getProperties().getProperty("mail_user_name");
		SEND_PWD = PropertiesFileUtils.getProperties().getProperty("mail_pwd");
		mail_timeout = PropertiesFileUtils.getProperties().getProperty("mail_connection_timeout");
		
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", VALUE_SMTP);//发件人的邮箱的 SMTP 服务器地址
		props.setProperty("mail.smtp.port", PORT);//服务器验证,发件人的邮箱的 SMTP 服务器端口
		props.setProperty("mail.smtp.auth", "true");//服务器验证,请求认证
		props.setProperty("mail.smtp.connectiontimeout", mail_timeout);
		session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SEND_USER, SEND_PWD);
			}
		});
		session.setDebug(true);
		message = new MimeMessage(session);
	}

	public boolean sendHtmlEmail(String mailTitle, String mailContent, String receiveUser) {
		try {
			// 发件人
			InternetAddress from = new InternetAddress(SEND_USER);
			message.setFrom(from);
			// 收件人
			InternetAddress to = new InternetAddress(receiveUser);
			message.setRecipient(Message.RecipientType.TO, to);
			// 邮件标题
			message.setSubject(mailTitle);
			String content = mailContent.toString();
			// 邮件内容,也可以使纯文本"text/plain"
			message.setContent(content, "text/html;charset=UTF-8");
			message.saveChanges();
			Transport transport = session.getTransport("smtp");
			// smtp验证，就是你用来发邮件的邮箱用户名密码
			transport.connect(VALUE_SMTP, SEND_USER, SEND_PWD);
			// 发送
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			logger.info("send mail success!!!");
			return true;
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SendMailHelper mail = new SendMailHelper();
		mail.sendHtmlEmail("测试发送邮件2016-12-29", "邮件内容2016-12-29", "feng.mingbao@foxmail.com");
	}

}
