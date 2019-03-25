package com.ldz.util.bean;

import java.io.Serializable;

/**
 * 公共接收参数对象
 * @author wanggang
 *
 */
public class RequestCommonParamsDto implements Serializable {

	/**
	 * 
	 */
	private String id;
	
	private static final long serialVersionUID = 1L;
	private String deviceId;//设备id（每次都必须上传）
	private String channelId;//用于推送（每次都必须上传）
	private String deviceTag;//设备tag主要也是 用于推送（每次都必须上传）
	private String startTime;//开始时间
	private String endTime;//结束时间
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getDeviceTag() {
		return deviceTag;
	}
	public void setDeviceTag(String deviceTag) {
		this.deviceTag = deviceTag;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	private String longitude;//经度（参数几乎每个接口都会上传）
	private String latitude;//纬度（参数几乎每个接口都会上传）
	private String speed;//速度
	//private String direction;//方向
	private String eventType;//事件  10急加速，20急刹车，30急转弯 ，40超速，50点火，60熄火 ， 70 运行时GPS上传 ， 71 熄火时候的GPS上传
	private String filePath;//文件相对路径(上传视频或者图片才会使用的参数)
	private String fileLocalPath;//文件本地绝对路径(上传视频或者图片才会使用的参数)
	private String fileRealName;//上传的文件在设备中的名称(上传视频或者图片才会使用的参数)
	private String fileSize;//文件大小(上传视频或者图片才会使用的参数)
	private String filePostfix;//文件后缀（可以用于文件类型）
	private String taskId;//任务id（用于上传服务器下载命令之后，终端上传之后回调给服务器之后的数据使用）
	
	private String sczt;//汽车是在运行中还是已经熄火的数据上传标识
	
	/**
	 * 命令类型（推送消息时使用）  01：超速设定 02:灵敏度设定(急加速灵敏度)  
	 *  11:拍（当前时间前后10s）视频 12:拍图片(实时) 13 合并视频   20 碰撞灵敏度    30 上传模式  40 GPS心跳间隔
	 */
	private String cmdType;//
	/**
	  cmdType 02:  
	    设置加减速监测灵敏等级，level 1-6，默认为 2 :
         1. 速度从零加到百公里约58秒内，能够被检测到急加速;
         2. 速度从零加到百公里约29秒内，能够被检测到急加速;
         3. 速度从零加到百公里约19秒内，能够被检测到急加速;
         4. 速度从零加到百公里约14秒内，能够被检测到急加速;
         5. 速度从零加到百公里约11秒内，能够被检测到急加速;
         6. 速度从零加到百公里约9秒内，能够被检测到急加速
       01:   值不能大于120且不能小于10
       
       
       cmdType= 30:
       cmd值（ 00 默认值（任意上传）
       10 仅WIFI上视频  )
       
       cmdType=40:
       	cmd值（ 1~300之间的数字)
       	
       cmdType=01
        cmd值（ 1~150之间的数字)
        
       cmdType=20
         cmd值（ 00：低
          10：中
          20： 高)
          
       cmdType=13
                    合并视频只需要将startTime 2017-10-12 00:00:00 和endTime 2017-10-12 00:00:00赋值即可
       cmdType=50
          cmd 00 全部视频   10 只上传碰撞视频
       cmdType=11,12,13时这个参数不使用，使用拓展参数进行传值
	 */
	private String cmd;//具体命令（推送消息时使用）   
	/**
	 * cmdType 为11和12的时候使用
	 * 参数格式为分隔式字符串  如:0-10 前一个0 标识要抓拍的摄像头  后一个10标识当前时间点前后十秒
	 * 摄像头参数如下:0,前后都抓拍, 1表示仅前摄像头, 2表示仅仅后摄像头。当cmdType为12的时候，此参数也是一样，只是抓拍前后多少秒参数无效【客户端自动判断，后台传递参数即可】
	 * cmdType 为13的时候参数是0-0 或者1-0  ，特别注意，为13的时候，startTime和endTime必须有值
	 * 摄像头参数如下:0 合并前摄像头  1 合并后摄像头  2 合并内置摄像头【内置摄像头这个暂时无法使用】
	 */
	private String cmdParams;//其它参数（推送消息时使用）
	
	//private String dwjd;//GPS定位角度
	private String gpsjd;//GPS精度
	
	private String fxj;//方向角
	
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	/*
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}*/
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileLocalPath() {
		return fileLocalPath;
	}
	public void setFileLocalPath(String fileLocalPath) {
		this.fileLocalPath = fileLocalPath;
	}
	public String getFileRealName() {
		return fileRealName;
	}
	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFilePostfix() {
		return filePostfix;
	}
	public void setFilePostfix(String filePostfix) {
		this.filePostfix = filePostfix;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getCmdType() {
		return cmdType;
	}
	public void setCmdType(String cmdType) {
		this.cmdType = cmdType;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getCmdParams() {
		return cmdParams;
	}
	public void setCmdParams(String cmdParams) {
		this.cmdParams = cmdParams;
	}
	/*
	public String getDwjd() {
		return dwjd;
	}
	public void setDwjd(String dwjd) {
		this.dwjd = dwjd;
	}*/
	public String getGpsjd() {
		return gpsjd;
	}
	public void setGpsjd(String gpsjd) {
		this.gpsjd = gpsjd;
	}
	public String getFxj() {
		return fxj;
	}
	public void setFxj(String fxj) {
		this.fxj = fxj;
	}
	
	
	public String getSczt() {
		return sczt;
	}
	public void setSczt(String sczt) {
		this.sczt = sczt;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "RequestCommonParamsDto [id=" + id + ", deviceId=" + deviceId + ", channelId=" + channelId
				+ ", deviceTag=" + deviceTag + ", startTime=" + startTime + ", endTime=" + endTime + ", longitude="
				+ longitude + ", latitude=" + latitude + ", speed=" + speed + ", eventType=" + eventType + ", filePath="
				+ filePath + ", fileLocalPath=" + fileLocalPath + ", fileRealName=" + fileRealName + ", fileSize="
				+ fileSize + ", filePostfix=" + filePostfix + ", taskId=" + taskId + ", sczt=" + sczt + ", cmdType="
				+ cmdType + ", cmd=" + cmd + ", cmdParams=" + cmdParams + ", gpsjd=" + gpsjd + ", fxj=" + fxj + "]";
	}
	

	
	
}
