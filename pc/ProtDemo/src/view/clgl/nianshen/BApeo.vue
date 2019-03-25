<template>
  <div>
    <Modal
      v-model="showModal"
      height="600"
      width="900"
      :closable='false'
      :mask-closable="false"
      :title="'年审信息'">
      <div slot="header" style="text-align: center;font-size: 22px;font-weight: 600">
        备案人
      </div>
      <div style="overflow: auto;height: 600px;width:800px">
        <Form label-position="top" >
          <Row>
            <Col span="6">
              <FormItem style="padding: 0 8px">
                <div slot="label" style="font-size: 18px;font-weight: 600">
                  备案人证件号码：
                </div>
                <Input v-model="cqrMess.jsysfzh" type="text" placeholder="备案人证件号码"/>
              </FormItem>
            </Col>
            <Col span="6">
              <FormItem style="padding: 0 8px">
                <div slot="label" style="font-size: 18px;font-weight: 600">
                  备案人姓名：
                </div>
                <Input v-model="cqrMess.jsyxm" type="text" placeholder="备案人姓名"/>
              </FormItem>
            </Col>
            <Col span="6">
              <FormItem style="padding: 0 8px">
                <div slot="label" style="font-size: 18px;font-weight: 600">
                  备案人电话号码：
                </div>
                <Input v-model="cqrMess.jsylxdh" type="text" placeholder="备案人电话号码"/>
              </FormItem>
            </Col>
            <Col span="6">
              <FormItem style="padding: 0 8px">
                <div slot="label" style="font-size: 18px;font-weight: 600">
                  备案时间：
                </div>
                <DatePicker v-model="cqrMess.jsycjsj" type="date" placeholder="备案时间" clearable></DatePicker>

              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="24">
              <FormItem>
                <div slot="label" style="font-size: 18px;font-weight: 600">
                  备注：
                </div>
                <Input v-model="cqrMess.jsybz" type="textarea" :autosize="{minRows: 3,maxRows: 3}" placeholder="备注信息"/>
              </FormItem>
            </Col>
          </Row>
          <FormItem>
            <div slot="label" style="font-size: 18px;font-weight: 600">
              附件：
            </div>
            <div style="width: 100%">
              <add-img-list :urlList="urlList" :uploadUrl="uploadUrl"
                            @removeFile="(mes)=>{removeFile(mes)}"
                            @addImg="(mes)=>{addImg(mes)}"></add-img-list>
            </div>
          </FormItem>
        </Form>
      </div>
      <div slot='footer'>
        <Button type="default" @click="close" style="color: #949494">取消</Button>
        <Button type="primary" @click="ok">确定</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import addImgList from '../../../components/addlistfileImg'

  export default {
    name: '',
    components: {addImgList},
    data() {
      return {
        v: this,
        showModal: true,
        operate: "审核",
        urlList: [],
        uploadUrl: this.apis.upFile,
        cqrMess:{
          jsydzda:''
        },
        Updata: {
          id:'',
          bz: '',
          jsydzda: ''
        }
      }
    },
    computed:{
      id:function(){
        return this.$parent.choosedItem.id
      }
    },
    watch:{
      id:function (n,o) {
        if(n == ''){
          this.swal({
            title:'数据丢失,请重新选取车辆！',
            type:'warning'
          }).then(p=>{
            this.$parent.componentName = ''
          })
        }
      }
    },
    created() {
      this.cqrMess = this.$parent.choosedItem
      this.Updata.id = this.$parent.choosedItem.id
    },
    methods: {
      getDict(val,code,unit){
        let a = val
        if(code!=undefined){
          a = this.dictUtil.getValByCode(this, code, val)
        }
        if(unit){
          a = a+unit
        }
        return a
      },
      removeFile(mes) {
        console.log(mes);
        this.urlList.forEach((it,index)=>{
          if(it == mes){
            this.urlList.splice(index,1)
            this.cqrMess.jsydzda = this.urlList.join(',')
            return
          }
        })
      },
      addImg(mes) {
        console.log(mes);
        this.urlList.push(mes)
        this.cqrMess.jsydzda = this.urlList.join(',')
      },
      close(){
        this.$parent.componentName = ''
      },
      ok(){
        var v = this
        this.swal({
          title:'年审确认',
          type:'question',
          showCancelButton: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(p=>{
          if(p.value){
            v.$http.post('/api/carns/clnsbar',this.cqrMess).then(res=>{
              if(res.code == 200){
                v.swal({
                  title:res.message,
                  type:'success'
                })
                v.$parent.componentName = ''
                v.$parent.getPager()
              }
            }).catch(err=>{})
          }
        })
      }
    }
  }
</script>

<style>
</style>
