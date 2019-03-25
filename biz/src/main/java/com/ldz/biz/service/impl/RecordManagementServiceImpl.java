package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.RecordManagementMapper;
import com.ldz.biz.model.ArchivesRecord;
import com.ldz.biz.model.RecordManagement;
import com.ldz.biz.service.ArchivesRecordService;
import com.ldz.biz.service.RecordManagementService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.MathUtil;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecordManagementServiceImpl extends BaseServiceImpl<RecordManagement, String> implements RecordManagementService {

	@Autowired
	private RecordManagementMapper baseMapper;

	@Autowired
	private JgService jgService;
	@Autowired
	private ArchivesRecordService archivesRecordService;

	@Override
	protected Mapper<RecordManagement> getBaseMapper() {
		return baseMapper;
	}

	/**
	 * 分页补充
	 * @param condition
	 * @return
	 */
	@Override
	public boolean fillPagerCondition(LimitedCondition condition){
		HttpServletRequest requset = getRequset();
		String orderType = requset.getParameter("orderType");
		if(StringUtils.equals(orderType,"1")){//按可用量来做倒序
			condition.setOrderByClause(" (ifnull(capacity,0)-ifnull(num,0)) desc");// 收费时间为空
		}else if(StringUtils.equals(orderType,"2")){//按编号ID做排序正向
			condition.setOrderByClause(RecordManagement.InnerColumn.id.asc());
		}else{
		}

		String serialNum = requset.getParameter("serialNum");//学员流水号
//		String archivesCode = requset.getParameter("archivesCode");//档案柜编码
//		String archivesStype = requset.getParameter("archivesStype");//档案状态 0在库 1 出库 null是查询全部
		//档案记录表
		Boolean archivesType=false;
		SimpleCondition archivesCondition = new SimpleCondition(ArchivesRecord.class);
		if(StringUtils.isNotEmpty(serialNum)) {
			archivesType=true;
			archivesCondition.eq(ArchivesRecord.InnerColumn.serialNum,serialNum);
		}
//		if(StringUtils.isNotEmpty(archivesCode)){
//			archivesType=true;
//			archivesCondition.eq(ArchivesRecord.InnerColumn.archivesCode,archivesCode);
//		}
//		if(StringUtils.isNotEmpty(archivesStype)){
//			archivesCondition.eq(ArchivesRecord.InnerColumn.stype,archivesStype);
//		}
		if(archivesType){
			archivesCondition.eq(ArchivesRecord.InnerColumn.stype,"0");
			List<ArchivesRecord> ArchivesRecordList=archivesRecordService.findByCondition(archivesCondition);
			if(CollectionUtils.isNotEmpty(ArchivesRecordList)){
				List<String> collect = ArchivesRecordList.stream().map(ArchivesRecord::getArchivesCode).collect(Collectors.toList());
				if(CollectionUtils.isNotEmpty(collect)) {
					condition.in(ArchivesRecord.InnerColumn.id, collect);
				}
			}else {//档案不在库中，就返回空值
				return false;
			}
		}
		return true;
	}

	@Override
	public ApiResponse<String> validAndSave(RecordManagement entity) {
		SysYh sysYh=getCurrentUser();

		String archivesCodeStart=entity.getArchivesCodeStart();
		RuntimeCheck.ifBlank(archivesCodeStart,"档案柜起始编号不能为空");
		RuntimeCheck.ifTrue(archivesCodeStart.length()>20,"档案柜起始编号长度不能超过20位");
		RuntimeCheck.ifFalse(entity.getArchivesNum()!=null&&entity.getArchivesNum()>1,"新增档案数量不能小于0");


		RuntimeCheck.ifBlank(entity.getPosition(),"位置不能为空");
		RuntimeCheck.ifFalse(entity.getCapacity()!=null && entity.getCapacity()>0,"档案柜容量必须大于0");
//		RuntimeCheck.ifFalse(entity.getNum()!=null && entity.getNum() < entity.getCapacity(),"档案柜现有数量，不能大于该档案柜总容量");

		entity.setNum(0);
		SimpleCondition condition = new SimpleCondition(RecordManagement.class);
        condition.like(RecordManagement.InnerColumn.id.name(), archivesCodeStart);
        condition.setOrderByClause(" id desc ");
        List<RecordManagement> managements = findByCondition(condition);
        RecordManagement recordManagement =null;
        long start=0;
        if(CollectionUtils.isNotEmpty(managements)){
            recordManagement = managements.get(0);
            String selectId=recordManagement.getId();
            selectId=selectId.replaceAll(archivesCodeStart,"");
			if(MathUtil.isNumeric(selectId)){
				start=Long.parseLong(selectId);
			}
        }

		SysJg sysJg = jgService.findById(sysYh.getJgdm());
		entity.setJgmc(sysJg.getJgmc());
		entity.setJgdm(sysJg.getJgdm());
		entity.setCjr(sysYh.getZh()+"-"+sysYh.getXm());
		entity.setCjsj(DateUtils.getNowTime());
		entity.setUpdater(sysYh.getZh()+"-"+sysYh.getXm());
		entity.setUpdateTime(DateUtils.getNowTime());

		start++;
		for(int i=0;i<entity.getArchivesNum();i++){
			String archivesCode= String.format("%04d",start+i);
			archivesCode=archivesCodeStart+archivesCode;
			entity.setArchivesCode(archivesCode);
			entity.setId(archivesCode);
			baseMapper.insertSelective(entity);
		}

		return ApiResponse.success();
	}

/*
	*//**
	 * 完成深克隆被克隆的类和类的引用类
	 * 均实现Serializable接口
	 * @param student
	 * @return Object
	 *//*
	public Object deepClone(RecordManagement student){
		*//*
		 *本次实现深克隆使用 ByteArrayOutputStream
		 * 和ByteArrayInputStream
		 *作为复制过程中字符数组存储中介
		 *//*

		try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);

			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));) {
			//将bos作为收集字节数组中介

			//将传入参数student类写入bos中
			oos.writeObject(student);
			//将读取到数据传入ObjectInputStream

			return ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			//JDK 1.7后引入 可以同时用| 优化代码可读性
			e.printStackTrace();
			return null;
		}
	}*/

}

