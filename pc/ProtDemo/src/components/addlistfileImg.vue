<template>
  <div>
    <div class="demo-upload-list" v-for="item in uploadList">
      <template v-if="item.status === 'finished'">
        <img style="width: 58px;height:58px" :src="item.url">
        <div class="demo-upload-list-cover">
          <!--文件移除-->
          <Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
        </div>
      </template>
      <template v-else>
        <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
      </template>
    </div>
    <Upload
      ref="upload"
      :show-upload-list="false"
      :default-file-list="defaultList"
      :on-success="handleSuccess"
      :format="['jpg','jpeg','png']"
      :max-size="1000"
      :on-format-error="handleFormatError"
      :on-exceeded-size="handleMaxSize"
      :before-upload="handleBeforeUpload"
      multiple
      type="drag"
      :action="uploadUrl"
      style="display: inline-block;width:58px;">
      <div style="width: 58px;height:58px;line-height: 58px;">
        <Icon type="md-camera" size="20"></Icon>
      </div>
    </Upload>
  </div>
</template>
<script>

  export default {
    name: '',
    data() {
      return {
        defaultList: [],
        imgName: '',
        visible: false,
        uploadList: [],
      }
    },
    props: {
      urlList: {
        type: Array,
        default: []
      },
      uploadUrl: {
        type: String,
        // default:this.apis.upFile+'?targetPath=temp'
        default: ''
      }
    },
    created() {
      this.dataList()
    },
    methods: {
      dataList() {
        let ArrList = this.urlList
        for (var i = 0; i < ArrList.length; i++) {
          this.defaultList.push({'url': this.apis.getImgUrl + ArrList[i]})
          // this.uploadList.push({'url': this.apis.getImgUrl + ArrList[i]})
        }
      },
      handleView(name) {
        this.imgName = name;
        this.visible = true;
      },
      handleRemove(file) {
        const fileList = this.$refs.upload.fileList;
        this.$refs.upload.fileList.splice(fileList.indexOf(file), 1);
//              this.formItem.filePaths.replace(url+',','')
        this.$emit('removeFile', file.url.replace(this.apis.getImgUrl,''))
      },
      handleSuccess(res, file, fileList) {
        this.$emit('addImg', res.message);
        file.url = this.apis.getImgUrl + res.message;
      },
      handleFormatError(file) {
        this.$Notice.warning({
          title: '文件格式错误',
          desc: '图片仅支持 jpg、jpeg、png'
        });
      },
      handleMaxSize(file) {
        this.$Notice.warning({
          title: '文件太大了',
          desc: '文件不能超过18M'
        });
      },
      handleBeforeUpload() {
        const check = this.uploadList.length < 5;
        if (!check) {
          this.$Notice.warning({
            title: '只能上传5个文件哦！！！'
          });
        }
        return check;
      }
    },
    mounted() {
      this.uploadList = this.$refs.upload.fileList;
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
