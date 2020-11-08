<template>
  <div>
    <Modal
      v-model="UpModal"
      :mask-closable="false"
      :closable="false"
      width="800"
      title="图片新增">
      <div>
        <Form ref="formValidate" :model="form" :rules="ruleValidate">
          <Row type="flex" justify="space-between">
            <Col align="center" span="4">
              <div>
                添加图片
              </div>
              <up-img :headImg='form.picUrl' @txImg="txImg"></up-img>
            </Col>
            <Col span="9">
              <FormItem label="图片标题" prop="title">
                <Input v-model="form.title" placeholder="图片标题"></Input>
              </FormItem>
            </Col>
            <Col span="9">
              <FormItem label="执行终端" prop="type">
                <Select v-model="form.type">
                  <Option v-for="(item,index) in typeList" :value="item.key" :key="index">{{item.val}}</Option>
                </Select>
              </FormItem>
            </Col>
          </Row>
          <Row type="flex" justify="space-between" style="margin-top: 16px">
            <Col span="4">
              <FormItem label="图片排序" prop="picSort">
                <input class="input" type="number" v-model="form.picSort" placeholder="图片排序">
              </FormItem>
            </Col>
            <Col span="9">
              <FormItem label="跳转地址" prop="jumpUrl">
                <Input v-model="form.jumpUrl" placeholder="跳转地址"></Input>
              </FormItem>
            </Col>
            <Col span="9">
              <FormItem label="图片状态" prop="effective">
                <Select v-model="form.effective">
                  <Option v-for="(item,index) in effectiveList" :value="item.key" :key="index">{{item.val}}</Option>
                </Select>
              </FormItem>
            </Col>
          </Row>
        </Form>
      </div>
      <div slot='footer'>
        <Button type="default" @click="colse" style="color: #949494">取消</Button>
        <Button type="primary" @click="save">确定</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import upImg from '@/components/Upload/UploadImg'

  export default {
    name: "add",
    components: {
      upImg
    },
    props:{
      mess:{}
    },
    data() {
      return {
        UpModal: true,
        form: {
          title:'',//标题
          type:'1',//终端执行
          picUrl: '',//图片地址
          jumpUrl:'',//跳转地址
          picSort:1,//排序
          effective:'1',//图片状态
        },
        ruleValidate:{
          title: [
            { required: true, message: '请输入图片标题', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '请选择展示终端', trigger: 'change' }
          ],
          effective: [
            { required: true, message: '请选择图片状态', trigger: 'change' }
          ]
        },
        typeList:[],
        effectiveList:[]
      }
    },
    created(){
      if(this.mess.id){
        this.form = this.mess
      }
      this.getDic()
    },
    methods: {
      getDic(){
        this.typeList = this.dictUtil.getByCode(this,'ZDCLK1021');
        this.effectiveList = this.dictUtil.getByCode(this,'ZDCLK1025');
      },
      save() {
        if(this.form.picUrl == ''){
          this.swal({
            type:'warning',
            title:'请先添加图片'
          })
          return
        }
        let url = ''
        if(this.mess.id){
          url='/api/picrotation/update'
        }else {
          url='/api/picrotation/save'
        }
        this.$refs['formValidate'].validate((valid) => {
          if (valid) {
            this.$http.post(url,this.form).then(res=>{
              if(res.code == 200){
                this.$parent.getPagerList()
                this.colse()
              }else {
                this.$Message.error(res.message);
              }
            }).catch(err=>{

            })
          }
        })
      },
      txImg(url) {
        this.form.picUrl = url
      },
      colse() {
        this.$parent.compName = ''
      }
    }
  }
</script>

<style lang="less">
  .input{
    display: inline-block;
    width: 100%;
    height: 36px;
    line-height: 1.5;
    padding: 4px 7px;
    font-size: 12px;
    border: 1px solid #dddee1;
    border-radius: 4px;
    color: #495060;
    background-color: #fff;
    background-image: none;
    position: relative;
    cursor: text;
    transition: border .2s ease-in-out,background .2s ease-in-out,box-shadow .2s ease-in-out;
  }
  .input::-webkit-input-placeholder{
    color: #dddee1;
  }
  .input::-moz-placeholder{
    color: #dddee1;
  }
</style>
