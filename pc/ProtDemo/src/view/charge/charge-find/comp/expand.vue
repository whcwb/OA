<template>
  <div>
    <Row class="expand-row">
      <Col span="8" >
        <span class="expand-key">银行流水号: </span>
        <span class="expand-value">{{row.bankSerialNum}}</span>
      </Col>
      <Col span="8">
        <span class="expand-key">收费时间: </span>
        <span class="expand-value">{{row.chargeTime}}</span>
      </Col>
      <Col span="8">
        <span class="expand-key">审核人:  </span>
        <span class="expand-value">{{row.verifier}}</span>
      </Col>
    </Row>
    <Row class="expand-row">
      <Col span="8">
        <span class="expand-key">审核时间: </span>
        <span class="expand-value">{{row.verifyTime}}</span>
      </Col>
      <Col span="8">
        <span class="expand-key">性别: </span>
        <span class="expand-value">{{ row.information.gender == '10'? '男': '女'}}</span>
      </Col>
      <Col span="8">
        <span class="expand-key">手机号码: </span>
        <span class="expand-value">{{row.information.phone}}</span>
      </Col>
    </Row>
    <Row >
      <Col span="8">
        <span class="expand-key">备注: </span>
        <span class="expand-value">{{row.remark}}</span>
      </Col>
      <Col span="8">
        <span class="expand-key">票据编号：</span>
        <span class="expand-value" >{{row.pjbh !=''?row.pjbh.substring(0,13):''}}</span>
      </Col>
      <Col span="8" >
        <div v-if="(row.chargeCode == '0004' || row.chargeCode == '0005')">
          <span class="expand-key">补打票据：</span>
          <Button icon="ios-print" type="primary" @click="winPrint(row)" size="small"></Button>
        </div>
      </Col>

    </Row>

    <!--<component-->
      <!--:is="compName"-->
      <!--:selectRow="selectRow"-->
    <!--&gt;-->
    <!--</component>-->
  </div>
</template>

<script>
    export default {
      name: "expand",
      props: {
        row: {
          type: Object
        }
      },
      data(){
        return {
          compName:'',
          messId:''
        }
      },
      methods:{
        winPrint(r){
          // console.log(r);
          if((r.chargeCode == '0004' || r.chargeCode == '0005')){
            this.$emit('print',{traineeId:r.traineeId,id:r.id})
            return;
          }else{
            this.swal({
              type:'warning',
              text:'抱歉，暂不支持打印支出收据'
            })
          }
        }
      }
    }
</script>

<style scoped>
  .expand-row{
    margin-bottom: 16px;
  }
</style>
