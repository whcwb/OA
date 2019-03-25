<template>
  <div>
    <Card style="width:100%;overflow: auto">
      <div class="box_row_list">
        <div class="radioCarItem" v-for="(it,index) in carList" :key ="index"
             :style="{borderColor:itemIndex==index?'#19be6b':'#f5a623'}"
             @click="itemClick(it,index)">
          <div class="clBh" :style="{background:itemIndex==index?'#19be6b':'#f5a623'}">{{it.clBh}}</div>
          <div class="clCx" :style="{background:itemIndex==index?'#19be6b':'#f5a623'}">{{it.clCx}}</div>
        </div>
      </div>
    </Card>
  </div>
</template>

<script>
  export default {
    name: "RadioCar",
    props:{
      clKm:{
        type:String,
        default:'3'
      }
    },
    data(){
      return{
        itemIndex:null,
        carList:[]
      }
    },
    created(){
      this.getCarlist()
    },
    methods:{
      itemClick(item,index){
        this.itemIndex = index
        this.$emit('carItem',item)
        this.$emit("getCarItemMess",item)
      },
      getCarlist(){
        this.$http.post('/api/lccl/getCar', {
          notShowLoading: 'true',
          pagerNum: 1,
          pageSize: 99999,
          clKm: this.clKm,
          orderBy: 'clZt asc,clBh asc,clCx asc',
          clZt: '00'
        }).then(res=>{
          if(res.code == 200){
            res.page.list.forEach((it,key)=>{
              it.radio = false
              if(key == res.page.list.length-1){
                this.carList = res.page.list
              }
            })
          }
          console.log(res);
        })
      }
    }
  }
</script>

<style lang="less">
.radioCarItem{
  width: 80px;
  height:40px;
  border-radius: 40px;
  /*background-color: #f5a623;*/
  border: solid 2px;
  margin: 12px;
  position: relative;
  cursor: pointer;
  .clBh{
    position: absolute;
    left: -1px;
    top: -2px;
    width: 40px;
    height: 40px;
    text-align: center;
    line-height: 40px;
    border-radius: 40px;
    color: #fff;
    font-size: 20px;
    font-weight: 600;
  }
  .clCx{
    position: absolute;
    right: 0px;
    bottom: 0px;
    width: 30px;
    height: 30px;
    text-align: center;
    line-height: 30px;
    border-radius: 30px;
    color: #fff;
    font-size: 16px;
    font-weight: 600;
  }
}
</style>
