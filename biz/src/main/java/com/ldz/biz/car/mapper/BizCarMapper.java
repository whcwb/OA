package com.ldz.biz.car.mapper;

import com.ldz.biz.car.model.BizCar;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface BizCarMapper extends Mapper<BizCar> {

    @Insert({ "<script> " +
            " <![CDATA[ " +
            " INSERT INTO biz_car_history (id, cph, jgdm, jgmc, cjr, cjsj, dah, car_qy, car_syr, car_sry_code, car_sry_zj_type, hdzl, jsszks, zqyzzl, nkg, nkk, nkc, zxfs, clcd, djjg, pxcx, hpzl, syxz, car_property_type, clpp, cllx, ppxh, clxh, ccdjrq, fzrq, qzbfq, bx_cdrq, yy_cdrq, gx_cdrq, clsbm, fdjh, fdjxh, rlzl, pl, gl, zzcmc, csys, qlj, hlj, ltsl, ltgg, bhps, zj, wkc, wkk, wkg, zzl, hdzk, byxz, clhdfs, ccrq, fzjg, syr_id, syr_name, syr_szd, syr_dn, syxx_id, nsrq, ns_id, bf_type, bf_fee, bf_date, sm_date, sm_fee, cl_cqr_id, cl_cqr, cl_cqr_dn, cl_cqr_code, cl_cqr_jbr_dn, cl_cqr_jbr, cl_cqr_bz, cl_cqr_jl, dzd_djz_type, dzd_djz_code, dzd_djz_fileurl, dzd_wszm_type, dzd_wszm_code, dzd_wszm_ph, dzd_wszm_fileurl, dzd_fp_type, dzd_fp_code, dzd_fp_fileurl, dzd_drys_type, dzd_drys_fileurl, dzd_xszfy_type, dzd_xszfy_fileurl, warn_ygns_date, warn_bx_date, warn_nssj_date, warn_gx_date, yg_last_data, yg_yszh, yg_gps_type, yg_gps_code, yg_yy_type, yg_ysn_sx, yg_gx, yg_zj, yg_kj_type, yg_kj_azsj, yg_kj_pc, yg_new_code, yg_new_kj, gx_last_data, gx_gas_ggzyxq, gx_lx_dn, gx_lxr, gx_gas_bz, gx_gas_dd, gx_type, bx_last_data, bx_bxrq, bx_bxz, bx_bxdh, bx_bdzbbh, bx_bdfbbh, bx_bd_wz, bx_bz, bx_da_file, bx_tbgs, bx_bxlxr, bx_bd_count, ns_last_data, ns_nsz, ns_nssj, ns_essj, ns_pc, ns_jsyId, ns_jsyxm, ns_jsysfzh, ns_jsylxdh, ns_zt, car_type, bx_jbr, bx_jbr_dn, dzd_cqbg_url, dzd_cpbg_url, dzd_clbf_url, dzd_clbm_url, dzd_clb_url, jsyid, jsyxm, jsysfzh, jsylxdh, cj_type, jsycjsj, jsydzda, jsybz, car_history_id,car_syr_bgsj,car_syr_bgczr)  " +
            " SELECT #{carHistoryId} AS ID ,C.cph,C.jgdm,C.jgmc,C.cjr,C.cjsj,C.dah,C.car_qy,C.car_syr,C.car_sry_code,C.car_sry_zj_type,C.hdzl,C.jsszks,C.zqyzzl,C.nkg,C.nkk,C.nkc,C.zxfs,C.clcd,C.djjg,C.pxcx,C.hpzl,C.syxz,C.car_property_type,C.clpp,C.cllx,C.ppxh,C.clxh,C.ccdjrq,C.fzrq,C.qzbfq,C.bx_cdrq,C.yy_cdrq,C.gx_cdrq,C.clsbm,C.fdjh,C.fdjxh,C.rlzl,C.pl,C.gl,C.zzcmc,C.csys,C.qlj,C.hlj,C.ltsl,C.ltgg,C.bhps,C.zj,C.wkc,C.wkk,C.wkg,C.zzl,C.hdzk,C.byxz,C.clhdfs,C.ccrq,C.fzjg,C.syr_id,C.syr_name,C.syr_szd,C.syr_dn,C.syxx_id,C.nsrq,C.ns_id,C.bf_type,C.bf_fee,C.bf_date,C.sm_date,C.sm_fee,C.cl_cqr_id,C.cl_cqr,C.cl_cqr_dn,C.cl_cqr_code,C.cl_cqr_jbr_dn,C.cl_cqr_jbr,C.cl_cqr_bz,C.cl_cqr_jl,C.dzd_djz_type,C.dzd_djz_code,C.dzd_djz_fileurl,C.dzd_wszm_type,C.dzd_wszm_code,C.dzd_wszm_ph,C.dzd_wszm_fileurl,C.dzd_fp_type,C.dzd_fp_code,C.dzd_fp_fileurl,C.dzd_drys_type,C.dzd_drys_fileurl,C.dzd_xszfy_type,C.dzd_xszfy_fileurl,C.warn_ygns_date,C.warn_bx_date,C.warn_nssj_date,C.warn_gx_date,C.yg_last_data,C.yg_yszh,C.yg_gps_type,C.yg_gps_code,C.yg_yy_type,C.yg_ysn_sx,C.yg_gx,C.yg_zj,C.yg_kj_type,C.yg_kj_azsj,C.yg_kj_pc,C.yg_new_code,C.yg_new_kj,C.gx_last_data,C.gx_gas_ggzyxq,C.gx_lx_dn,C.gx_lxr,C.gx_gas_bz,C.gx_gas_dd,C.gx_type,C.bx_last_data,C.bx_bxrq,C.bx_bxz,C.bx_bxdh,C.bx_bdzbbh,C.bx_bdfbbh,C.bx_bd_wz,C.bx_bz,C.bx_da_file,C.bx_tbgs,C.bx_bxlxr,C.bx_bd_count,C.ns_last_data,C.ns_nsz,C.ns_nssj,C.ns_essj,C.ns_pc,C.ns_jsyId,C.ns_jsyxm,C.ns_jsysfzh,C.ns_jsylxdh,C.ns_zt,C.car_type,C.bx_jbr,C.bx_jbr_dn,C.dzd_cqbg_url,C.dzd_cpbg_url,C.dzd_clbf_url,C.dzd_clbm_url,C.dzd_clb_url,C.jsyid,C.jsyxm,C.jsysfzh,C.jsylxdh,C.cj_type,C.jsycjsj,C.jsydzda,C.jsybz,C.car_history_id , " +
            "#{cjsj} ,#{cjr} " +
            "FROM BIZ_CAR C where c.id=#{clId}  " +
            "" +
            "  ]]> "+
            " </script>"})
    void saveHistoryId(@Param("clId") String clId, @Param("carHistoryId") String carHistoryId, @Param("cjr") String cjr, @Param("cjsj") String cjsj);
}