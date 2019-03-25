<template>
  <div class="box_col">
    <pager-tit title="票据打印" iconname="ios-football"></pager-tit>
    <div class="box_row colCenter rowRight pageFindSty">
      <div>
        <Icon type="ios-home" size="32"/>
        <Select v-model="F_select" placeholder="请选择收费项" style="width: 120px">
          <Option v-for="(item,index) in cityList" :value="index" :key="item.value">{{ item.floor }}</Option>
        </Select>
      </div>

      <div>
        <Icon type="ios-home" size="32"/>
        <Input v-model="coachName" placeholder="请输入学员姓名" style="width: 120px" />
      </div>

       <div>
         <Icon type="ios-home" size="32"/>
         <Input v-model="coachphone" placeholder="请输入学员手机号" style="width: 120px" />
       </div>

       <div>
         <Icon type="ios-home" size="32"/>
         <Input v-model="coachId" placeholder="请输入学员证件号码" style="width: 120px" />
       </div>
      <div>
        <DatePicker type="daterange" split-panels placement="bottom-end" placeholder="考试时间" style="width: 200px"></DatePicker>
      </div>
      <Button type="primary" @click="">
        <Icon type="md-search"></Icon>
        <!--查询-->
      </Button>
      <Button type="primary" @click="">
        <Icon type="md-add"></Icon>
      </Button>
    </div>
    <div class="box_col_100">
      <div class="box_row" style="height: 100%">
        <div class="box_row_1auto">
          <Card title="今日收费记录" style="width: 100%;height: 90%">
            <Table border width="100%" :columns="tabTit" :data="tabData" stripe></Table>
          </Card>
        </div>
        <div  class="box_row_1auto">
          <Card title="今日已打印" style="width: 100%;height: 90%">
            <Table border width="100%" :columns="tabTit1" :data="tabData1" stripe></Table>
          </Card>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
    export default {
        name: "index",
        data() {
          return{
            F_select:'',
            coachName:'',
            coachphone:'',
            cityList:[
              {
                value: 'k1',
                floor: '报名费'
              },
              {

              }
            ],
            tabTit:[
              {
                title: '序号',
                align: 'center',
                width: 60,
                type: 'index'
              },
              {
                title: '姓名',
                key: 'xm'
              },
              {
                title: '证件号码',
                key: 'zjhm'
              },
              {
                title: '收费类型',
                key: 'lx'
              },
              {
                title: '金额',
                key: 'je'
              },
              {
                title: '操作',
                render: (h) =>{
                  return h('div',[
                    h('Button',{
                      props: {
                        type: 'info',
                        size: 'small'
                      }
                    },'打印')
                  ])
                }
              }
            ],
            tabData:[
              {
                xm: 'zhang',
                zjhm: '83646234',
                lx: '报名费',
                je:  2800
              }
            ],
            tabTit1:[
              {
                title: '序号',
                align: 'center',
                width: 60,
                type: 'index'
              },
              {
                title: '姓名',
                key: 'xm'
              },
              {
                title: '证件号码',
                key: 'zjhm'
              },
              {
                title: '收费类型',
                key: 'lx'
              },
              {
                title: '金额',
                key: 'je'
              }
            ],
            tabData1:[
              {
                xm: 'zhang',
                zjhm: '83646234',
                lx: '报名费',
                je:  2800
              }
            ]
          }
        }
    }
</script>

<style scoped>

</style>
