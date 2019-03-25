<template>
    <div class="box_col">
      <pager :title="'功能管理'" :iconname="'ios-boat'"></pager>
      <Row>
        <Col span="18">&nbsp;</Col>
        <div style="text-align: right">
          <Col span="6"> <Input v-model="value" placeholder="Enter something..." style="width: 100px;margin-right: 10px" />
            <Button type="primary" shape="circle" icon="ios-search" style="margin-right: 10px"></Button>
            <Button type="primary" shape="circle" icon="md-add" @click="addUser"></Button>
          </Col>
        </div>
      </Row>
      <Table border stripe  :columns="columns1" :data="data1" style="margin-top: 10px"></Table>
      <div style="text-align: right; padding-top: 20px">
        <Page :total="'1000'" show-sizer/>
      </div>
    </div>
</template>

<script>
  import pager from '../../../components/header'

  export default {
        name: "index",
        components: {
          pager
        },
        data(){
          return{
              columns1: [
                {
                  title: '序号',
                  width: 80,
                  align: 'center',
                  type: 'index'
                },
                {
                  title: '功能名称',
                  key: 'gnmc',
                  align: 'center'
                },
                {
                  title: '功能代码',
                  key: 'gndm',
                  align: 'center'
                },
                {
                  title: 'URL',
                  key: 'url',
                  align: 'center'
                },
                {
                  title: '父节点',
                  key: 'fjd',
                  align: 'center'
                },
                {
                  title: 'API前缀',
                  key: 'apiqz',
                  align: 'center'
                },
                {
                  title: 'API后缀',
                  key: 'apihz',
                  align: 'center'
                },
                {
                  title: '操作',
                  key: 'cz',
                  align: 'center',
                  render: (h,params) =>{
                    return h('div',[
                      h('Button',{
                        props: {
                          type: 'warning',
                          size: 'small',
                          icon: 'md-create'
                        },
                        style:{
                          marginRight: '10px'
                        }
                      }),
                      h('Button',{
                        props: {
                          type: 'error',
                          size: 'small',
                          icon: 'md-close'
                        }
                      })
                    ])
                  }
                }
              ],
            data1: [
              {
                gnmc: '用户管理',
                gndm: 'system_user',
                url: 'system/system_user',
                fjd: 'system',
                apiqz: '/',
                apihz: '/'
              },
              {
                gnmc: '角色管理',
                gndm: 'system_root',
                url: 'system/system_root',
                fjd: 'system',
                apiqz: '/',
                apihz: '/'
              },
              {
                gnmc: '机构管理',
                gndm: 'system_framework',
                url: 'system/system_framework',
                fjd: 'system',
                apiqz: '/',
                apihz: '/'
              }
            ]
          }
        }
    }
</script>

<style scoped>

</style>
