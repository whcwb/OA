<template>
  <div class="box_col">
    <pager-tit title="团购报名" iconname="md-contacts"></pager-tit>
    <div class="box_row colCenter pageFindSty">
      <div class="box_row_100">
        <Button type="success" style="width: 80px" @click="">
          成团
        </Button>
      </div>

      <div>
        <Icon type="ios-home" size="32"/>
        <Select v-model="user.tc" placeholder="请选择团购类型"  style="width: 220px" >
          <Option v-for="item in tgList" :value="item.value" :key="item.value">{{ item.floor }}</Option>
        </Select>
      </div>

      <div>
        <Icon type="ios-home" size="32"/>
        <Input value="" placeholder="请输入学员姓名" style="width: 220px" />
      </div>

      <Button type="primary" @click="">
        <Icon type="md-search"></Icon>
        <!--查询-->
      </Button>
    </div>
    <Card class="box_col_100" style="">

      <Table
        size="large" stripe
        :height="AF.getPageHeight()-360"
        :columns="studentTabTit" :data="studentTabData"
        @on-selection-change="tabSelect"
      ></Table>

      <div style="text-align: right;padding-top: 16px">
        <Page :total="100" />
      </div>
    </Card>

  </div>
</template>

<script>
  export default {
    name: "index",
    data(){
      return{
        showModal: true,
        user:{
          xm: '',
          zjh: '',
          sjhm: '',
          sffq: '',
          cx: '',
          tc: '',
          team1: '',
          team2: '',
          xb: ''
        },
        cityList:[{value: "C1",floor: "C1"},{value: "C2",floor: "C2"},{value: "C3",floor: "C3"}]
        ,tgList:[{value: 3,floor: "三人成团优惠100"},{value: 2,floor: "二人成团优惠50"}],
        studentTabTit: [
          {
            type: 'selection',
            width: 60,
            align: 'center'
          },
          {
            title: '姓名',
            key: 'name'
          },
          {
            title:'性别',
            key:'sex'
          },
          {
            title:'证件号码',
            key:'idNum'
          },
          {
            title: '报名点',
            key: 'adres',
            sortable: true
          },
          {
            title: '报名时间',
            key: 'date',
            sortable: true
          },
          {
            title: '车型',
            key: 'cartyp',
            sortable: true
          }

        ],
        studentTabData: [
          {
            name: '张小名',
            adres: '一队',
            sex:'男',
            idNum:'222222222222222222',
            cartyp: 'A2',
            date: '2016-10-03'
          },
          {
            name: '张小名',
            sex:'男',
            idNum:'222222222222222222',
            adres: '二队',
            cartyp: 'A1',
            date: '2016-10-04'
          },
          {
            name: '张小名',
            adres: '三队',
            sex:'男',
            idNum:'222222222222222222',
            cartyp: 'C1',
            date: '2016-10-02'
          },
          {
            name: '张小名',
            adres: '三队',
            cartyp: 'C2',
            sex:'男',
            idNum:'222222222222222222',
            date: '2016-10-01'
          }
        ]
      }
    },
    methods: {
      submit(){
        this.$parent.data1.push(this.user);
        this.$parent.compName = ''
      }
    }
  }
</script>

<style scoped>

</style>
