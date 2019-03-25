<template>
  <div class="box_col">
    <pager-tit title="流水对账" iconname="ios-football"></pager-tit>
    <div class="box_row colCenter rowRight pageFindSty">
      <!--<div>-->
        <!--<Icon type="ios-home" size="32"/>-->
        <!--<Select v-model="F_select" placeholder="请选择驾校" style="width: 120px">-->
          <!--<Option v-for="(item,index) in cityList" :value="index" :key="item.value">{{ item.floor }}</Option>-->
        <!--</Select>-->
      <!--</div>-->

      <div>
        <Icon type="ios-home" size="32"/>
        <Input value="" placeholder="请输入学员姓名" style="width: 120px" />
      </div>

      <div>
        <Icon type="ios-home" size="32"/>
        <Input value="" placeholder="请输入学员手机号" style="width: 120px" />
      </div>

      <div>
        <Icon type="ios-home" size="32"/>
        <Input value="" placeholder="请输入学员证件号码" style="width: 120px" />
      </div>
      <div>
        <DatePicker type="daterange" split-panels placement="bottom-end" placeholder="对账日期" style="width: 200px"></DatePicker>
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
          <Card title="今日需要对账记录" style="width: 100%;height: 100%">
            <Table border  :columns="tabTit" :data="tableData" stripe></Table>
            <div class="box_row" style="padding-top: 16px">
              <div class="box_row_100">
                合计： {{count}}
              </div>
              <Page show-sizer show-elevator :total="100"></Page>
            </div>
          </Card>
        </div>
        <div class="box_row_1auto">
          <Card title="对账记录" style="width: 100%;height: 100%">
            <Table border :columns="tabTit1" :data="tableData1" stripe></Table>
            <div class="box_row" style="padding-top: 16px">
              <div class="box_row_100">
                合计： {{count1}} &nbsp;&nbsp;&nbsp;&nbsp;
                <Button type="primary">打印</Button>
              </div>

              <Page show-sizer show-elevator :total="100"></Page>
            </div>
          </Card>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
    export default {
        name: "index",
        data(){
          return {
            count: 9000,
            count1: 200000,
            tabTit:[
              {
                title: '序号',
                type: 'index',
                width: 60
              },
              {
                title: '学员姓名',
                key: 'xm'
              },
              {
                title: '证件号码',
                key: 'zjhm'
              },
              {
                title: '类型',
                key: 'lx'
              },
              {
                title: '驾校名称',
                key: 'jx'
              },
              {
                title: '对账金额',
                key: 'je'
              },
              {
                title: '银行流水号',
                key: 'lsh'
              }
            ],
            tableData:[
              {
                xm: 'song',
                zjhm: '6765677878',
                lx: '报名费',
                jx: 'xx驾校',
                je: 9000,
                lsh: '2387423fasdf87'
              }
            ],
            tabTit1:[
              {
                title: '序号',
                type: 'index',
                width: 60
              },
              {
                title: '学员姓名',
                key: 'xm'
              },
              {
                title: '证件号码',
                key: 'zjhm'
              },
              {
                title: '类型',
                key: 'lx'
              },
              {
                title: '驾校名称',
                key: 'jx'
              },
              {
                title: '对账金额',
                key: 'je'
              },
              {
                title: '银行流水号',
                key: 'lsh'
              },
              {
                title: '状态',
                key: 'zt'
              },
              {
                title: '对账日期',
                key: 'dzrq'
              }

            ],
          tableData1:[
            {
              xm: 'song',
              zjhm: '6765677878',
              lx: '报名费',
              jx: 'xx驾校',
              je: 9000,
              lsh: '2387423fasdf87',
              zt: '成功',
              dzrq: '2018-09-09'
            }
          ]
          }
        }
    }
</script>

<style scoped>

</style>
