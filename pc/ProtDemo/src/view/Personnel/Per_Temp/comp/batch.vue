<template>
  <div>
    <Modal v-model="showModal"
           title="批量入库"
           width="80%"
           :closable='false' :mask-closable="false">
      <div style="text-align: center;width:100%;max-height: 700px;overflow: auto">
        <Row style="font-size:16px;font-weight: 700;padding: 8px 0">
          <Col span="4">
            资产类别
          </Col>
          <Col span="4">
            物品类别
          </Col>
          <Col span="4">
            入库数量
          </Col>
          <Col span="4">
            物品单价
          </Col>
          <Col span="4">
            备注信息
          </Col>
          <Col span="4">

          </Col>
        </Row>
        <Row v-for="(item,index) in AMess" :key="index">
          <Col span="4" :class-name="'colsty'">
            <!--<Input type="text" size="default" v-model="item.kcMc" placeholder="物品名称"/>-->
            <Select v-model="item.kcMc" clearable @on-change="kcMcChange">
              <Option v-for="(item,index) in kcMcList" :value="item.mc" :key="index">{{ item.mc }}</Option>
            </Select>
          </Col>
          <Col span="4" :class-name="'colsty'">
            <Select v-model="item.kcLx" clearable>
              <Option v-for="(item,index) in kcLxlist" :value="item.mc" :key="index">{{ item.mc }}</Option>
            </Select>
          </Col>
          <Col span="4" :class-name="'colsty'">
            <InputNumber size="default" style="width: 100%" :min="0" v-model="item.kcSl"></InputNumber>
          </Col>
          <Col span="4" :class-name="'colsty'">
            <InputNumber size="default" style="width: 100%" :min="0" v-model="item.rkDj"></InputNumber>
          </Col>
          <Col span="4" :class-name="'colsty'">
            <Input type="textarea" :autosize="{minRows: 1,maxRows: 1}"
                   size="default" v-model="item.bz" placeholder="备注信息"/>
          </Col>
          <Col span="4" v-if="AMess.length>1">
            <Button size="default" type="error" @click="remove(index)">删除</Button>
          </Col>
        </Row>
        <Row style="padding-top:16px">
          <Col span="24" align="center">
            <Button type="info" long icon="md-add"
            @click="pushmess"
            >继续添加物品</Button>
          </Col>
        </Row>

      </div>
      <div slot="footer">
        <Button type="default" style="color: #949494" @click="close">关闭</Button>
        <Button type="warning" @click="submit">添加</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import FormItem from "../../../components/formItems";

  export default {
    name: "batch",
    components: {FormItem},
    data() {
      return {
        showModal: true,
        AMess: [
          {
            kcMc: '',//资产类别
            kcLx: '',//物品类别
            kcSl: 0,//数量
            bz: '',//备注信息
            rkDj: 0.00,//单价
          }
        ],
        Pmess: {
          kcMc: '',//资产类别
          kcLx: '',//物品类别
          kcSl: 0,//数量
          bz: '',//备注信息
          rkDj: 0.00,//单价
        },
        kcMcList:[],
        kcLxlist:[]
      }
    },
    created(){
      this.getStockTyp()
    },
    methods: {
      kcMcChange(val){
        // console.log(val);
        if(val === undefined){
          this.kcLxlist = []
          return
        }
        this.kcMcList.forEach((item,index)=>{
          if(item.mc === val){
            this.kcLxlist = item.children
          }
        })
      },
      getStockTyp(){
        this.$http.post('/api/kclb/getAllLb').then(res => {
          // console.log(res);
          if (res.code == 200) {
            this.kcMcList = res.result
          }
        }).catch(err => {
        })
      },
      pushmess(){
        let a =JSON.parse(JSON.stringify(this.Pmess))
        this.AMess.push(a)
      },
      remove(i){
        this.AMess.splice(i,1)
      },
      close() {
        this.$parent.compName = ''
      },
      submit() {
        let a = JSON.stringify(this.AMess)
        this.$http.post('/api/bizkc/saveList',{kcs:a}).then(res=>{
          if(res.code == 200){
            this.swal({
              type:'success',
              title:res.message
            })
            this.$parent.compName = ''
            this.$parent.getPagerList()

          }else {
            this.swal({
              type:'error',
              title:res.message
            })
          }

        }).catch(err=>{

        })
      }
    }
  }
</script>

<style lang="less">
  .colsty{
    padding: 3px 6px;
  }
</style>
