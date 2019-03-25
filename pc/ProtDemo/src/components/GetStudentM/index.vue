<template>
  <div class="box_col">
    <div class="box_col_auto" style="overflow:auto;">

      <div class="box_row_list" v-if="CardList.length>0">
        <div style="padding: 6px 6px 6px 0;cursor: pointer" v-for="(item,index) in CardList">
          <Card :class="index==tagIndex?'carBoeder':''" style="width: 110px" @click.native="itemClick(item,index)">
            <div>
              {{item.name}}
            </div>
            <div>
              {{item.registrationTime.substring(0,10)}}
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
    props:{
      params:{
        type:String,
        default:''
      }
    },
    watch:{
      params:function (n,o) {
        if(n){
          console.log(JSON.parse(n));
          this.getStudentList(JSON.parse(n))
        }
      }
    },
    data(){
      return{
        CardList:[],
        tagIndex:null
      }
    },
    methods:{
      itemClick(item,index){
        this.tagIndex = index
        console.log('lll');
        this.$parent.stuMes = item
        if (item.status == '60') {
          this.$parent.message = "该学员已经退费";
        }
        if (item.status == '50') {
          this.$parent.message = "该学员已经结业";
        }
      },
      getStudentList(val){
        if (val.idCardNo != '' ||val.name != '' ||val.phone != '' ) {
        }else {
          this.$parent.message = "请输入学员信息";
          return;
        }
        this.$http.post(this.apis.TRAINEE.PAGER, val).then((res) => {
          if (res.code == 200) {
            this.$parent.message = '';
            this.$parent.iconName = "md-checkmark";
            this.$parent.iconColor = "#19be6b";
            if (res.page.list.length > 0) {
              this.CardList = res.page.list
              this.tagIndex = 0
              this.$parent.stuMes = res.page.list[0]
            }
            if (res.page.list && res.page.list.length == 0) {
              this.$parent.message = "没有该学员信息";
              this.$parent.stuMes = {}
            }
            if (res.page.list && res.page.list[0].status == '60') {
              this.$parent.message = "该学员已经退费";
            }
            if (res.page.list && res.page.list[0].status == '50') {
              this.$parent.message = "该学员已经结业";
            }
          } else {
            this.$parent.iconName = "md-alert";
            this.$parent.iconColor = "#f90";
            this.$parent.message = res.message;
            this.$parent.headImg = null;
          }
        })
      }
    }
  }
</script>

<style lang="less">
.carBoeder{
  /*border:solid 1px #f00*/
  background-color: #ed4014;
  color: #fff;
  font-weight: 600;
}
</style>
