package com.pigeon.spring.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.pigeon.common.util.ResMessageUtil;
import com.pigeon.spring.entity.T_DEVICE_CONFIMBIND;
import com.pigeon.spring.entity.T_DEVICE_LOCATION;
import com.pigeon.spring.entity.T_DEVICE_LOGIN;
import com.pigeon.spring.entity.T_DEVICE_UPMSG;
import com.pigeon.spring.entity.T_DEVICE_WARN;
import com.pigeon.spring.service.DeviceManageDao;
import com.pigeon.util.StreamStringUtil;

/**
 * 2017-10-17 17:01:01
 * 
 * @author wei
 * 
 */
@Controller
@RequestMapping("/device")
public class DeviceInfoController {
	private static final String KEYS = "bcfbf9ebf25a4d0bf86fe9f416b62264";
	private static final String LOCATION_LBS_URL = "http://apilocate.amap.com/position";
	// private static final String IP = "127.0.0.1";
	// private static final Integer PORT = 8080;

	@Autowired
	private DeviceManageDao deviceManageDao;

	/** 日志实例 */
	private static final Logger logger = Logger
			.getLogger(DeviceInfoController.class);

	/*
	 * 登录服务器
	 */
	@RequestMapping(value = "/login", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String login(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ServletInputStream input = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		StringBuffer sb = new StringBuffer();
		String online = "";
		while ((online = reader.readLine()) != null) {
			sb.append(online);
		}
		T_DEVICE_LOGIN t_device_login = new T_DEVICE_LOGIN();

		logger.info("设备登录服务器信息 : " + sb.toString());
		String result = null;
		JSONObject json = new JSONObject();
		try {
			JSONObject paramsJSON = JSONObject.fromObject(sb.toString());
			String transactionCode = paramsJSON.getString("t");// 交易码
			String deviceSerialNumber = paramsJSON.getString("u");// 设备序列号
			int electricQuantity = paramsJSON.getInt("bat");// 电量电池
			String bts = paramsJSON.getString("bts");// 当前基站信息
														// mcc,mnc,lac,cellid,signal
			String timestamp = paramsJSON.getString("s");// 时间戳

			JSONObject btsJson = JSONObject.fromObject(bts);
			String mcc = btsJson.getString("mcc");
			String mnc = btsJson.getString("mnc");
			String lac = btsJson.getString("lac");
			String cellId = btsJson.getString("cellid");
			String signal = btsJson.getString("signal");

			StringBuffer lbs = new StringBuffer();
			lbs.append(mcc).append(",").append(mnc).append(",").append(lac)
					.append(",").append(cellId).append(",").append(signal);

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("accesstype", "0");
			map.put("network", "GSM/EDGE");
			map.put("cdma", "0"); // 非cdma卡
			map.put("imei", deviceSerialNumber); // 手机imei号
			// map.put("smac", smac);
			map.put("bts", lbs.toString());

			map.put("nearbts", lbs.toString());
			map.put("key", KEYS);
			String jsonToString = StreamStringUtil.sendGetToGaoDe(
					LOCATION_LBS_URL, map);
			String lon = "";
			String lat = "";
			if ("-1".equals(jsonToString)) {
				logger.info("定位未成功----------");
			} else {
				JSONObject jsons = JSONObject.fromObject(jsonToString);
				String status = jsons.getString("status"); // 回传状态,1表示成功
				if (status.equals("1")) {
					String results = jsons.getString("result"); // 结果级
					JSONObject jsonResult = JSONObject.fromObject(results);
					String locationJson = jsonResult.containsKey("location") ? jsonResult
							.getString("location") : null; // 经纬度

					if (locationJson != null) {
						logger.info("locationJson++" + locationJson);
						String str[] = locationJson.split(",");
						lon = str[0];
						lat = str[1];
					}
				} else {
					logger.info("定位不成功status不为1.");
				}
			}

			t_device_login.setOBJ_ID("1");
			t_device_login.setTRANSACTION_CODE(transactionCode);
			t_device_login.setDEVICE_SERIAL_NUMBER(deviceSerialNumber);
			t_device_login.setDEBICE_BAT(electricQuantity);
			t_device_login.setDEVICE_BTS(bts);
			t_device_login.setDEVICE_TIMESTAMP(timestamp);
			t_device_login.setINSERT_TIME(new Date());
			t_device_login.setLAT(lat);
			t_device_login.setLON(lon);

			deviceManageDao.addDeviceLogin(t_device_login);

			json.put("t", transactionCode);
			json.put("r", ResMessageUtil.responseValue("dealResult"));
			json.put("lon", "1");
			json.put("lat", "2");
			json.put("s1", timestamp);
			json.put("s2", System.currentTimeMillis());
			json.put("s3", System.currentTimeMillis());
			json.put("i", ResMessageUtil.responseValue("ip").toString());
			json.put("p", ResMessageUtil.responseValue("port"));
		} catch (JSONException e) {
			result = ResMessageUtil.responseMessage("paramTypeError");
			e.printStackTrace();
		} catch (Exception e) {
			result = ResMessageUtil.responseMessage("systemError");
			e.printStackTrace();
		}
		result = json.toString();
		return result;
	}

	/*
	 * 设备上传定位信息
	 */
	@RequestMapping(value = "/uploadLocation", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String uploadLocation(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ServletInputStream input = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		StringBuffer sb = new StringBuffer();
		String online = "";
		while ((online = reader.readLine()) != null) {
			sb.append(online);
		}
		T_DEVICE_LOCATION deviceLocation = new T_DEVICE_LOCATION();

		logger.info("设备上传定位信息 : " + sb.toString());
		String result = null;
		JSONObject json = new JSONObject();
		try {
			JSONObject paramsJSON = JSONObject.fromObject(sb.toString());

			String transactionCode = paramsJSON.getString("t");// 交易码
			String deviceSerialNumber = paramsJSON.getString("u");// 设备序列号
			String l1 = paramsJSON.getString("l1");// 经度
			String l2 = paramsJSON.getString("l2");// 纬度
			String gs = paramsJSON.getString("gs");// 速度
			String a = paramsJSON.getString("a");// 海拔
			String d = paramsJSON.getString("d");// 方向
			Integer e1 = paramsJSON.getInt("e1");// 电量使用状态
			Integer e2 = paramsJSON.getInt("e2");// 电量
			Integer f = paramsJSON.getInt("f");// 是否飞行
			String s = paramsJSON.getString("s");// 时间戳

			deviceLocation.setOBJ_ID("1");
			deviceLocation.setDEVICE_SERIAL_NUMBER(deviceSerialNumber);
			deviceLocation.setTRANSACTION_CODE(transactionCode);
			deviceLocation.setLAT(l1);
			deviceLocation.setLON(l2);
			deviceLocation.setSPEED(gs);
			deviceLocation.setELEVATION(a);
			deviceLocation.setDIRECTION(d);
			deviceLocation.setBAT_STATUS(e1);
			deviceLocation.setELECTRIC_QUANTITY(e2);
			deviceLocation.setFLY(f);
			deviceLocation.setTIMESTAMP(s);
			deviceLocation.setINSERT_TIME(new Date());

			deviceManageDao.addDeviceLocation(deviceLocation);

			json.put("t", transactionCode);
			json.put("r", ResMessageUtil.responseValue("dealResult"));
			json.put("s", System.currentTimeMillis());
			json.put("p", 1);
			json.put("f", 2);
			json.put("o", System.currentTimeMillis());

		} catch (JSONException e) {
			result = ResMessageUtil.responseMessage("paramTypeError");
			e.printStackTrace();
		} catch (Exception e) {
			result = ResMessageUtil.responseMessage("systemError");
			e.printStackTrace();
		}
		result = json.toString();
		return result;
	}

	/*
	 * 设备确定绑定
	 */
	@RequestMapping(value = "/confirmBind", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String confirmBind(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ServletInputStream input = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		StringBuffer sb = new StringBuffer();
		String online = "";
		while ((online = reader.readLine()) != null) {
			sb.append(online);
		}

		logger.info("设备确认绑定 : " + sb.toString());
		String result = null;
		JSONObject json = new JSONObject();
		try {
			JSONObject paramsJSON = JSONObject.fromObject(sb.toString());

			String transactionCode = paramsJSON.getString("t");// 交易码
			String deviceSerialNumber = paramsJSON.getString("u");// 设备序列号
			String s = paramsJSON.getString("s");// 时间戳

			T_DEVICE_CONFIMBIND dfb = new T_DEVICE_CONFIMBIND();
			dfb.setOBJ_ID("1");
			dfb.setDEVICE_SERIAL_NUMBER(deviceSerialNumber);
			dfb.setDEVICE_TIMESTAMP(s);
			dfb.setINSERT_TIME(new Date());
			dfb.setTRANSACTION_CODE(transactionCode);

			deviceManageDao.addDeviceConfirmBind(dfb);

			json.put("t", transactionCode);
			json.put("r", ResMessageUtil.responseValue("dealResult"));
			json.put("s", System.currentTimeMillis());

		} catch (JSONException e) {
			result = ResMessageUtil.responseMessage("paramTypeError");
			e.printStackTrace();
		} catch (Exception e) {
			result = ResMessageUtil.responseMessage("systemError");
			e.printStackTrace();
		}
		result = json.toString();
		return result;
	}

	/*
	 * 当设备被强制关机、SIM卡状态、电量过低的时候，发送此通知
	 */
	@RequestMapping(value = "/warn", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String warn(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ServletInputStream input = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		StringBuffer sb = new StringBuffer();
		String online = "";
		while ((online = reader.readLine()) != null) {
			sb.append(online);
		}
		T_DEVICE_WARN deviceWarn = new T_DEVICE_WARN();

		logger.info("设备告警 : " + sb.toString());
		String result = null;
		JSONObject json = new JSONObject();
		try {
			JSONObject paramsJSON = JSONObject.fromObject(sb.toString());

			String transactionCode = paramsJSON.getString("t");// 交易码
			String deviceSerialNumber = paramsJSON.getString("u");// 设备序列号
			Integer warnType = paramsJSON.getInt("w");// 预警类型
			String s = paramsJSON.getString("s");// 时间戳

			deviceWarn.setOBJ_ID("1");
			deviceWarn.setINSERT_TIME(new Date());
			deviceWarn.setDEVICE_SERIAL_NUMBER(deviceSerialNumber);
			deviceWarn.setTIMESTAMP(s);
			deviceWarn.setTRANSACTION_CODE(transactionCode);
			deviceWarn.setWARN_TYPE(warnType);

			deviceManageDao.addDeviceWarn(deviceWarn);

			json.put("t", transactionCode);
			json.put("r", ResMessageUtil.responseValue("dealResult"));
			json.put("s", System.currentTimeMillis());
			json.put("w", warnType);

		} catch (JSONException e) {
			result = ResMessageUtil.responseMessage("paramTypeError");
			e.printStackTrace();
		} catch (Exception e) {
			result = ResMessageUtil.responseMessage("systemError");
			e.printStackTrace();
		}
		result = json.toString();
		return result;
	}

	/*
	 * 当气压计测试  ***********设备升级完成之后发送此消息
	 */
	@RequestMapping(value = "/updateSendMsg", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String updateSendMsg(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ServletInputStream input = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		StringBuffer sb = new StringBuffer();
		String online = "";
		while ((online = reader.readLine()) != null) {
			sb.append(online);
		}

		logger.info("当设备升级完成之后发送此消息: " + sb.toString());
		String result = null;
		JSONObject json = new JSONObject();
		try {
			JSONObject paramsJSON = JSONObject.fromObject(sb.toString());

			String transactionCode = paramsJSON.getString("t");// 交易码
			String deviceSerialNumber = paramsJSON.getString("u");// 设备序列号
			String s = paramsJSON.getString("s");// 时间戳
			Integer mode = paramsJSON.getInt("mode");

			T_DEVICE_UPMSG deviceUpMsg = new T_DEVICE_UPMSG();

			deviceUpMsg.setOBJ_ID("1");
			deviceUpMsg.setDEVICE_SERIAL_NUMBER(deviceSerialNumber);
			deviceUpMsg.setINSERT_TIME(new Date());
			deviceUpMsg.setMODE(mode);
			deviceUpMsg.setTIMESTAMP(s);
			deviceUpMsg.setTRANSACTION_CODE(transactionCode);

			deviceManageDao.addDeviceUpMsg(deviceUpMsg);

			json.put("t", transactionCode);
			json.put("r", ResMessageUtil.responseValue("dealResult"));
			json.put("s", System.currentTimeMillis());

		} catch (JSONException e) {
			result = ResMessageUtil.responseMessage("paramTypeError");
			e.printStackTrace();
		} catch (Exception e) {
			result = ResMessageUtil.responseMessage("systemError");
			e.printStackTrace();
		}
		result = json.toString();
		return result;
	}

	/*
	 * 设备端每次开机时从服务器获取用户在app上设置的状态，对自己的状态进行修改
	 */
	@RequestMapping(value = "/getMyselfSet", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String getMyselfSet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String sb = StreamStringUtil.InputStreamTOString(request);
		logger.info("设备端每次开机时从服务器获取用户在app上设置的状态，对自己的状态进行修改: " + sb);
		String result = null;
		JSONObject json = new JSONObject();
		try {
			JSONObject paramsJSON = JSONObject.fromObject(sb);

			String transactionCode = paramsJSON.getString("t");// 交易码
			String deviceSerialNumber = paramsJSON.getString("u");// 设备序列号
			@SuppressWarnings("unused")
			String s = paramsJSON.getString("s");// 时间戳

			List<Map<String, Object>> list = deviceManageDao
					.queryDeviceMyselfSet(deviceSerialNumber, 0, 1);

			if (list.size() > 0) {
				json.put("p",
						Integer.valueOf(list.get(0).get("LOCATION_TYPE") + ""));
				json.put("f", Integer.valueOf(list.get(0)
						.get("POINT_FREQUENCY") + ""));
				json.put("m",
						Integer.valueOf(list.get(0).get("FLY_MODEL") + ""));
				json.put("o", list.get(0).get("TING_OFF_ON") + "");

				String id = list.get(0).get("ID").toString();
				logger.info("修改的id=" + id);

				String hql = "UPDATE DEVICE_SET SET GET_TIME ='"
						+ StreamStringUtil.stampToDate(System
								.currentTimeMillis())
						+ "', GET_STATUS='1' WHERE ID = '" + id + "'";
				deviceManageDao.updateMyselefByCondition(hql);
			}

			json.put("t", transactionCode);
			json.put("r", ResMessageUtil.responseValue("dealResult"));
			json.put("s", System.currentTimeMillis() + "");

		} catch (JSONException e) {
			result = ResMessageUtil.responseMessage("paramTypeError");
			e.printStackTrace();
		} catch (Exception e) {
			result = ResMessageUtil.responseMessage("systemError");
			e.printStackTrace();
		}
		result = json.toString();
		return result;
	}

	/*
	 * 采用spring提供的上传文件的方法
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/uploadFile", produces = "application/json;charset=UTF-8")
	public String uploadFile(HttpServletRequest request,
			HttpServletResponse response) throws IllegalStateException,
			IOException {
		String result = null;
		JSONObject json = new JSONObject();
		long startTime = System.currentTimeMillis();
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames();

			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next()
						.toString());
				if (file != null) {
					String dir = request.getSession(true).getServletContext().getRealPath("/upload");
					String path = dir + "/" +  file.getOriginalFilename();
					// 上传
					file.transferTo(new File(path));
				}

			}

		}
		json.put("t", "0");
		json.put("r", ResMessageUtil.responseValue("dealResult"));
		json.put("fn", "a.txt");
		json.put("s", System.currentTimeMillis() + "");
		json.put("p", 1);
		json.put("f", 2);
		json.put("o", "00:00:00 00:00:00");
		long endTime = System.currentTimeMillis();
		System.out
				.println("运行时间：" + String.valueOf(endTime - startTime) + "ms");
		result = json.toString();
		return result;
	}

}
