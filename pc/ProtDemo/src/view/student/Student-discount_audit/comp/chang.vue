<template>
  <div>
    <Modal v-model="showModal"
           title="优惠项修改"
           :closable='false' :mask-closable="false">
      <Row style="margin-bottom: 12px">
        <Col span="24">
          <Select v-model="reduceCode" placeholder="优惠项选择" not-found-text="请先选择报名点" label-in-value
                  @on-change="selectReduce" clearable>
            <Option v-for="item in messList" :value="item.id" :key="item.id">{{
              item.reduceName }}
            </Option>
          </Select>
        </Col>
      </Row>
      <Row>
        <Col span="24">
          <Input :number="true" v-model="price" placeholder="请输入驳回原因" ></Input>
        </Col>
      </Row>
      <div slot="footer">
        <Button type="default" style="color: #949494" @click="close">关闭</Button>
        <Button type="warning" @click="submit">修改</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "audit",
    props: {
      userAudit:{
        type: Object,
        default: {}
      }
    },
    data(){
      return{
        showModal: true,
        price:0,
        reduceCode:'',
        messList:[]
      }
    },
    watch:{
      price:function (n,o) {
        if(n>this.userAudit.registrationFee){
          this.swal({
            type:'error',
            title:'优惠金额不能大于报名费'
          }).then(mes=>{
            if(mes.value){
              this.price = o
            }
          })
        }
      }
    },
    created(){
      this.price = this.userAudit.reducePrice
      this.reduceCode = this.userAudit.reduceCode
      this.loadReduce(this.userAudit.jgdm)
    },
    methods: {
      selectReduce(sel){

      },
      loadReduce(jgdm) {
        this.$http.post(this.apis.TRAINEE.GETREDUCE, {jgdm: jgdm}).then((res) => {
          if (res.code === 200) {
            this.messList = res.result;
          } else {
            this.swal({
              text: '无法加载报名点信息!',
              type: 'error'
            });
          }
        }).catch((error) => {
          swal({
            text: this.apis.NETWORK_ERR_STR,
            type: 'error'
          });
        })
      },
      close(){
        this.$parent.compName = ''
      },
      submit(){
        if (this.price == ""){
          this.swal({
            type:'error',
            text:'请先输入优惠金额！'
          });
          return;
        }

        this.$http.post('/api/traineereduceauditing/editReducePrice', {id:this.userAudit.id,
          reduceCode:this.reduceCode,
          price:this.price}).then((res) => {
          let msg = res.message;
          if (res.code === 200) {
            this.swal({
              text: msg,
              type: 'success'
            });
            this.$parent.getPagerList()
            this.$parent.compName = '';
          }else{
            this.swal({
              text: msg,
              type: 'error'
            });
          }
        }).catch((error) => {
          this.swal({
            text: this.apis.NETWORK_ERR_STR,
            type: 'error'
          });
        });
      }
    }
  }
</script>

<style scoped>

</style>
