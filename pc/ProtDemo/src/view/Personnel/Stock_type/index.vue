<template>
  <div class="box_col">
    <Row>
      <Col span="12">
        <pager-tit title="库存分类"></pager-tit>
      </Col>
      <Col span="12">
        <Row type="flex" justify="end">
          <Col>
            <Button type="primary" icon="md-add" @click="saveItem(null)"></Button>
          </Col>
        </Row>
      </Col>
    </Row>
    <div class="box_col_auto" style="margin-top:12px;background-color: #e8e8e8">
      <Row>
        <Col span="6" :lg="6" :md="8" :sm="12" :xs="24" v-for="(item,index) in mesData" :key="index">
          <Card style="margin: 8px">
            <p slot="title">
              <Icon type="ios-film-outline"></Icon>
              {{item.mc}}
            </p>
            <div slot="extra">
              <Button size="small" type="primary" @click="saveItem(item)" style="margin-right: 8px">
                新增
              </Button>
              <Button size="small" type="error" @click="remove(item)">
                删除
              </Button>
            </div>
            <div :style="{height:(AF.getPageHeight()-430)/2+'px',overflow:'auto'}">
              <!--<Row v-for="it in item.children">-->
              <!--<Col span="24">{{it.mc}}</Col>-->
              <!--</Row>-->
              <Table :height="(AF.getPageHeight()-430)/2" stripe size="small"
                     :columns="tabTit" :data="item.children"></Table>
            </div>
          </Card>
        </Col>
      </Row>
    </div>
    <component :is="compName"></component>
  </div>
</template>

<script>
  import formData from './comp/formData'

  export default {
    data() {
      return {
        compName: '',
        choosItem: {},
        mesData: [],
        tabTit: [
          {
            title: '#',
            align: 'center',
            type: 'index'
          },
          {
            title: '物品名称',
            align: 'center',
            key: 'mc'
          },
          {
            title: '删除',
            align: 'center',
            render: (h, p) => {
              return h('Button', {
                props: {size: 'small', type: 'error'},
                on:{
                  click:()=>{
                    this.remove(p.row)
                  }
                }
              }, '刪除')
            }
          }
        ]
      }
    },
    name: "index",
    components: {formData},
    created() {
      this.getData()
    },
    methods: {
      getData() {
        this.$http.post('/api/kclb/getAllLb').then(res => {
          // console.log(res);
          if (res.code == 200) {
            this.mesData = res.result
          }
        }).catch(err => {
        })
      },
      saveItem(item) {
        this.choosItem = item
        this.compName = 'formData'
      },
      remove(item) {
        this.swal({
          title: "是否确定删除?!!",
          type: "error",
          showCancelButton: true,
          confirmButtonText: '确定删除！',
          cancelButtonText: '取消删除！',
        }).then(val => {
          if (val.value) {
            this.$http.post('/api/kclb/remove/' + item.id).then(res => {
              if (res.code == 200) {
                this.getData()
              }
            }).catch(err => {
            })
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
