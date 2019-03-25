package com.ldz.biz.service.impl;

import com.ldz.biz.mapper.ArchivesRecordMapper;
import com.ldz.biz.mapper.TraineeInformationMapper;
import com.ldz.biz.model.ArchivesRecord;
import com.ldz.biz.model.RecordManagement;
import com.ldz.biz.model.TraineeInformation;
import com.ldz.biz.service.ArchivesRecordService;
import com.ldz.biz.service.RecordManagementService;
import com.ldz.biz.service.TraineeInformationService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArchivesRecordServiceImpl extends BaseServiceImpl<ArchivesRecord, String> implements ArchivesRecordService {

	@Autowired
	private ArchivesRecordMapper baseMapper;
    @Autowired
	private TraineeInformationMapper traineeMapper;
	@Autowired
	private RecordManagementService recordManagementService;
	@Autowired
	private TraineeInformationService traineeService;//学员
	@Override
	protected Mapper<ArchivesRecord> getBaseMapper() {
		return baseMapper;
	}
//     * @param entity serialNum 学员流水号
//     * @param entity archivesCode 档案柜编码
//     * @param entity remark      备注
	@Override
	public ApiResponse<TraineeInformation> addWarehousing(ArchivesRecord entity) {
		SysYh sysUser=getCurrentUser();

		ArchivesRecord archivesRecord=new ArchivesRecord();
		archivesRecord.setId(genId());


		ApiResponse<TraineeInformation> res = new ApiResponse<>();
		RuntimeCheck.ifBlank(entity.getSerialNum(), "学员流水号不能为空");
		RuntimeCheck.ifBlank(entity.getArchivesCode(), "档案柜编码不能为空");
//		RuntimeCheck.ifBlank(entity.getInputPerson(), "入库人不能为空");

//		学员检验
		SimpleCondition condition = new SimpleCondition(TraineeInformation.class);
		condition.eq(TraineeInformation.InnerColumn.serialNum,entity.getSerialNum());
		condition.setOrderByClause(TraineeInformation.InnerColumn.id.desc());
		List<TraineeInformation> traineeList=traineeService.findByCondition(condition);
		TraineeInformation t=new TraineeInformation();
		if(traineeList!=null&&traineeList.size()>0){
			t=traineeList.get(0);
			if(StringUtils.isNotEmpty(t.getRecordLib())){
				res.setCode(500);
				res.setMessage("该流水号，已入档不要重复入档。该流水号在的档案号："+t.getRecordLib());
				res.setResult(t);
				return res;
			}

			archivesRecord.setTraineeId(t.getId());
			archivesRecord.setTraineeName(t.getName());
			archivesRecord.setSerialNum(t.getSerialNum());

		}else{
			t.setSerialNum(entity.getSerialNum());
			res.setCode(500);
			res.setMessage("未找流水号，未找到匹配的学员");
			res.setResult(t);
			return res;
		}

		//		档案柜检验
		RecordManagement record=null;//

		SimpleCondition recordCondition = new SimpleCondition(RecordManagement.class);
		recordCondition.eq(RecordManagement.InnerColumn.archivesCode,entity.getArchivesCode());
		List<RecordManagement> recordList=recordManagementService.findByCondition(recordCondition);
		if(recordList!=null&&recordList.size()>0){
			record=recordList.get(0);
		}
		if(record==null){
			res.setCode(500);
			res.setMessage("档案柜不存在，请核实");
			res.setResult(t);
			return res;
		}
		long capacity=record.getCapacity()==null?0:record.getCapacity();//容量
		long num=record.getNum()==null?0:record.getNum();//数量
		if((num+1)>capacity){
			res.setCode(500);
			res.setMessage("该档案柜已经满，不能再存放");
			res.setResult(t);
			return res;
		}
		archivesRecord.setArchivesCode(record.getArchivesCode());
		archivesRecord.setInTime(DateUtils.getNowTime());
		archivesRecord.setCjsj(DateUtils.getNowTime());
		archivesRecord.setCjr(sysUser.getZh()+"-"+sysUser.getXm());
		archivesRecord.setStype("0");
		archivesRecord.setRemark(entity.getRemark());
//		插入档案记录表
		save(archivesRecord);
//		档案柜使用的数量自增
		record.setNum((int) (num+1));
		recordManagementService.update(record);

//		将档案柜的编码回写到学员表中
		t.setRecordLib(record.getArchivesCode());
		traineeService.update(t);

		res.setCode(200);
		res.setMessage("存档成功");
		res.setResult(t);
		return res;
	}

	/**
	 * 获取档案明细
	 * @param entity serialNum 学员流水号
	 * @param entity archivesCode 档案柜编码
	 * @param entity stype 档案状态 0在库 1 出库 null是查询全部
	 * @return 档案状态  档案柜状态
	 */
	@Override
	public ApiResponse<Map<String,Object>> getRecordDetail(ArchivesRecord entity){
		Map<String,Object> map=new HashMap<>();
//		RuntimeCheck.ifBlank(entity.getSerialNum(), "学员流水号不能为空");
		RuntimeCheck.ifTrue(StringUtils.isBlank(entity.getSerialNum())&&StringUtils.isBlank(entity.getArchivesCode()),"学员流水号或档案柜编码至少有一项不为空");
		//查询用户档案明细
		SimpleCondition condition = new SimpleCondition(ArchivesRecord.class);
		//检查档案是否在线
		if(StringUtils.isNotEmpty(entity.getStype())){
			condition.eq(ArchivesRecord.InnerColumn.stype,entity.getStype());
		}

		if(StringUtils.isNotEmpty(entity.getSerialNum())){
			condition.eq(ArchivesRecord.InnerColumn.serialNum,entity.getSerialNum());
		}

		if(StringUtils.isNotEmpty(entity.getArchivesCode())){
			condition.eq(ArchivesRecord.InnerColumn.archivesCode,entity.getArchivesCode());
		}

		condition.setOrderByClause(ArchivesRecord.InnerColumn.id.desc());

		List<ArchivesRecord> archivesRecordList=this.findByCondition(condition);
		//遍历档案列表，对在线档案柜信息增加 身份证号 手机号 机构名称(报名点) 报名时间
		if(archivesRecordList!=null&&archivesRecordList.size()>0){
			List<String> userList = archivesRecordList.stream().map(ArchivesRecord::getTraineeId).collect(Collectors.toList());
			SimpleCondition traineeCondition = new SimpleCondition(TraineeInformation.class);
			traineeCondition.in(TraineeInformation.InnerColumn.id,userList);
			List<TraineeInformation> files = traineeMapper.selectByExample(traineeCondition);
			if(files!=null&&files.size()>0){
				Map<String,TraineeInformation> traineeMap = files.stream().collect(Collectors.toMap(TraineeInformation::getId,p->p));
				for(ArchivesRecord archives:archivesRecordList){
					if(StringUtils.isNotEmpty(archives.getTraineeId())){
						TraineeInformation traineeInforMap = traineeMap.get(archives.getTraineeId());
						if (traineeInforMap == null)continue;
						archives.setIdCardNo(traineeInforMap.getIdCardNo());
						archives.setPhone(traineeInforMap.getPhone());
						archives.setJgmc(traineeInforMap.getJgmc());
						archives.setRegistrationTime(traineeInforMap.getRegistrationTime());

					}
				}
			}

		}

//		ArchivesRecord archivesRecord=null;
//		if(archivesRecordList!=null&&archivesRecordList.size()>0){
//			archivesRecord=archivesRecordList.get(0);
//		}
//		RuntimeCheck.ifNull(archivesRecord,"未找到该流水号对应的档案");

//		//查询档案柜明细
//		RecordManagement record=null;//
//		SimpleCondition recordCondition = new SimpleCondition(RecordManagement.class);
//		recordCondition.eq(RecordManagement.InnerColumn.archivesCode,entity.getArchivesCode());
//		List<RecordManagement> recordList=recordManagementService.findByCondition(recordCondition);
//		if(recordList!=null&&recordList.size()>0){
//			record=recordList.get(0);
//		}
//		map.put("fileCabinet",record);//
		map.put("archivesRecordList",archivesRecordList);//档案明细

		return ApiResponse.success(map);
	}
	/**
	 * 档案库 出库
	 * @param entity serialNum 学员流水号
	 * @param entity remark  备注
	 * @return 档案状态  档案柜状态
	 */
	@Override
	public ApiResponse<String> updateShiftout(ArchivesRecord entity){
        SysYh sysUser=getCurrentUser();

		RuntimeCheck.ifBlank(entity.getSerialNum(), "学员流水号不能为空");
		//查询用户档案明细
		SimpleCondition condition = new SimpleCondition(ArchivesRecord.class);
		condition.eq(ArchivesRecord.InnerColumn.serialNum,entity.getSerialNum());
		condition.eq(ArchivesRecord.InnerColumn.stype,"0");
		condition.setOrderByClause(ArchivesRecord.InnerColumn.id.desc());
		List<ArchivesRecord> archivesRecordList=this.findByCondition(condition);
		ArchivesRecord archivesRecord=null;
		if(archivesRecordList!=null&&archivesRecordList.size()>0){
			archivesRecord=archivesRecordList.get(0);
		}
		RuntimeCheck.ifNull(archivesRecord,"未找到该流水号需要出库档案");

		RuntimeCheck.ifTrue(StringUtils.equals(archivesRecord.getStype(),"1"),"该档案已出库不需要再次出库");
		//查询档案柜明细
		RecordManagement record=null;//
		SimpleCondition recordCondition = new SimpleCondition(RecordManagement.class);
		recordCondition.eq(RecordManagement.InnerColumn.archivesCode,archivesRecord.getArchivesCode());
		List<RecordManagement> recordList=recordManagementService.findByCondition(recordCondition);
		if(recordList!=null&&recordList.size()>0){
			record=recordList.get(0);
		}

		//档案记录表，做出库处理
        archivesRecord.setStype("1");
		archivesRecord.setRemark(entity.getRemark());
		archivesRecord.setOutTime(DateUtils.getNowTime());
		this.update(archivesRecord);

        record.setNum((int) (record.getNum()==null?0:record.getNum()-1));
        recordManagementService.update(record);
        /**
         * 将档案表里的 档案柜号，移除
         */
        traineeMapper.updateRecordLib(archivesRecord.getTraineeId());

        return ApiResponse.success();
	}

	/**
	 * 获取学员档案柜信息
	 * @param entity serialNum 学员流水号
	 * @return
	 */
	@Override
	public ApiResponse<RecordManagement> getTraineeRecordDetail(ArchivesRecord entity){
		RuntimeCheck.ifBlank(entity.getSerialNum(), "学员流水号不能为空");
		SimpleCondition condition = new SimpleCondition(ArchivesRecord.class);
		condition.eq(ArchivesRecord.InnerColumn.serialNum,entity.getSerialNum());
		condition.eq(ArchivesRecord.InnerColumn.stype,"0");
		condition.setOrderByClause(ArchivesRecord.InnerColumn.id.desc());
		List<ArchivesRecord> archivesRecordList=this.findByCondition(condition);
		ArchivesRecord archivesRecord=null;
		if(archivesRecordList!=null&&archivesRecordList.size()>0){
			archivesRecord=archivesRecordList.get(0);
		}
		RecordManagement recordManagement=null;
		ApiResponse<RecordManagement> res = new ApiResponse<>();
		res.setCode(500);
		res.setMessage("未找到该流水号需要出库档案");
		res.setResult(new RecordManagement());
		if(archivesRecord==null){
			return res;
		}
		recordManagement=recordManagementService.findById(archivesRecord.getArchivesCode());
		if(recordManagement!=null){
			res.setCode(200);
			res.setMessage("操作成功");
			res.setResult(recordManagement);
		}else{
			res.setMessage("未找到应对的档案柜");
		}
		return res;
	}
}