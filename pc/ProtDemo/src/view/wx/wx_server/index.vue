<template>
  <div class="box">
    <Row>
      <Col span="12">
        <pager-tit title="活动管理" iconname="ios-football"></pager-tit>
      </Col>
      <Col span="12">
        <Row type="flex" justify="end">
          <Col>
            <Button type="primary" @click="compName = 'add'">
              <Icon type="md-add"></Icon>
            </Button>
          </Col>
        </Row>
      </Col>
    </Row>
    <div class="box_row_1auto" style="padding: 8px 0">
      <div class="box_row_list">
        <Card style="width:350px;margin: 12px" v-for="(item,index) in listData">
          <p slot="title">
            <Icon type="ios-film-outline"></Icon>
            {{item.title}}
          </p>
          <div slot="extra">
            <Tag color="volcano">
              {{type(item.type)}}
            </Tag>
            <Tag color="green" v-if="item.effective == '1'">
              {{effective(item.effective)}}
            </Tag>
            <Tag color="red" v-else>
              {{effective(item.effective)}}
            </Tag>

            <Button type="primary" size="small" shape="circle" @click="compName = 'add';formMess=item">
              <Icon type="md-create"></Icon>
            </Button>
          </div>
          <div style="margin-bottom: 6px">
            <Input v-model="item.jumpUrl" readonly placeholder="跳转地址"></Input>
          </div>
          <div style="height: 200px">
            <img :key="index" style="width: 100%;height: 100%"
                 :src="apiUrl+item.picUrl" alt="">
          </div>
        </Card>
      </div>

    </div>
    <component :is="compName" :mess="formMess"></component>
  </div>
</template>

<script>
  import add from './comp/add'
  export default {
    name: "index",
    components:{
      add
    },
    data(){
      return{
        compName:'',
        formMess:{},
        apiUrl:this.apis.getImgUrl,//文件服务地址前缀
        listData:[],
        param:{
          pageSize:10,
          pageNum:1,
        }
      }
    },
    created(){
      this.getPagerList()
    },
    methods:{
      type(val){
        return this.dictUtil.getValByCode(this, 'ZDCLK1021', val)
      },
      effective(val){
        return this.dictUtil.getValByCode(this, 'ZDCLK1025', val)
      },
      getPagerList(){
        this.$http.post('/api/picrotation/pager',this.param).then(res=>{
          console.log('pq',res);
          if(res.code==200){
           this.listData=res.page.list
          }
        }).catch(err=>{

        })
      }
    }
  }
</script>

<style scoped>

</style>
