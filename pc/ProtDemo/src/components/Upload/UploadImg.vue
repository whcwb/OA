<template>
  <div style="width: 100%;min-width: 60px;z-index: 100">
    <div class="demo-upload-list" v-for="item in uploadList">
      <template v-if="item.message">
        <img :src="apiUrl + item.message">
        <div class="demo-upload-list-cover">
          <Icon type="ios-eye-outline" @click.native="handleView(item.message)"></Icon>
          <Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
        </div>
      </template>
      <template v-else>
        <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
      </template>
    </div>
    <Upload
      v-show="uploadList.length==0"
      ref="upload"
      :show-upload-list="false"
      :default-file-list="defaultList"
      :on-success="handleSuccess"
      :format="['jpg','jpeg','png']"
      :max-size="5120"
      :on-format-error="handleFormatError"
      :on-exceeded-size="handleMaxSize"
      :before-upload="handleBeforeUpload"
      multiple
      type="drag"
      :action="uploadImg"
      style="display: inline-block;width:58px;">
      <div style="width: 58px;height:58px;line-height: 58px;">
        <Icon type="ios-camera" size="20"></Icon>
      </div>
    </Upload>
    <Modal title="预览" v-model="visible" style="text-align: center">
      <img :src="apiUrl + imgName" v-if="visible" style="width: 100%">
    </Modal>
  </div>
</template>
<script>
  export default {
    data () {
      return {
        uploadImg:this.apis.upFile,
        defaultList: [],
        imgName: '',
        visible: false,
        uploadList: [
          // {message:"temp/47c142c294fd438d872af85a66c1488c.png"}
        ],
        apiUrl:this.apis.getImgUrl
      }
    },
    props:{
      headImg:String
    },
    methods: {
      handleView (name) {
        this.imgName = name;
        this.visible = true;
      },
      handleRemove (file) {
        this.uploadList = []
        // const fileList = this.$refs.upload.fileList;
        // this.$refs.upload.fileList.splice(fileList.indexOf(file), 1);
      },
      handleSuccess (res, file) {//文件上传成功
        console.log(res);
        this.uploadList = [res]
        this.$emit('txImg',res.message)
        // file.url = 'https://o5wwk8baw.qnssl.com/7eb99afb9d5f317c912f08b5212fd69a/avatar';
        // file.name = '7eb99afb9d5f317c912f08b5212fd69a';
      },
      handleFormatError (file) {//文件格式验证
        this.$Notice.warning({
          title: 'The file format is incorrect',
          desc: 'File format of ' + file.name + ' is incorrect, please select jpg or png.'
        });
      },
      handleMaxSize (file) {//文件大小验证
        this.$Notice.warning({
          title: 'Exceeding file size limit',
          desc: 'File  ' + file.name + ' is too large, no more than 5M.'
        });
      },
      handleBeforeUpload () {//文件上传前
        const check = this.uploadList.length < 1;
        if (!check) {
          this.$Notice.warning({
            title: 'Up to five pictures can be uploaded.'
          });
        }
        return check;
      }
    },
    mounted () {
      // console.log('txurl',this.headImg);
      if(this.headImg){
        this.uploadList = [{message:this.headImg}]
      }
      // this.uploadList = this.$refs.upload.fileList;
    }
  }
</script>
<style>
  .demo-upload-list{
    display: inline-block;
    width: 60px;
    height: 60px;
    text-align: center;
    line-height: 60px;
    border: 1px solid transparent;
    border-radius: 4px;
    overflow: hidden;
    background: #fff;
    position: relative;
    box-shadow: 0 1px 1px rgba(0,0,0,.2);
    margin-right: 4px;
  }
  .demo-upload-list img{
    width: 100%;
    height: 100%;
  }
  .demo-upload-list-cover{
    display: none;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background: rgba(0,0,0,.6);
  }
  .demo-upload-list:hover .demo-upload-list-cover{
    display: block;
  }
  .demo-upload-list-cover i{
    color: #fff;
    font-size: 20px;
    cursor: pointer;
    margin: 0 2px;
  }
</style>
