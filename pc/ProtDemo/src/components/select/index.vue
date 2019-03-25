<template>
  <div>
    <div style="width: 300px;height: 32px;position: relative;margin-left: 8px">
      <input v-model="inputVal" class="input" type="text" style="position: absolute;top:0;z-index: 100"
             @focus="InFocus()"
             @blur="InBlur()"
             @keyup.up="EventUp"
             @keyup.down="EventDown"
      />
      <div id="option" class="option" v-if="optionShow">
        <!--:style="{transform: 'translateY('+optionRtop*(28)+'px)'}"-->
        <ul>
          <li class="ivu-select-item" :style="{background:index==optionIndex?'#d4d4d4':''}"
              v-for="(it,index) in option" :key="index"
              @click="optionClick(it,index)">
              <span v-for="(item,index) in opVal">{{it[item]}}</span>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "index",
    props:{
      option:{
        type:Array,
        defaule:[]
      },
      opVal:{
        type:Array,
        default:[]
      }
    },
    watch:{
      inputVal:function (n,o) {
        this.inChange()
      }
    },
    data() {
      return {
        inputVal:'',
        optionShow: false,
        // option: [
        //   {key: 1, val: 1},
        //   {key: 2, val: 2},
        //   {key: 3, val: 3},
        //   {key: 4, val: 4},
        //   {key: 5, val: 5},
        //   {key: 6, val: 6},
        //   {key: 7, val: 7},
        //   {key: 8, val: 8},
        //   {key: 9, val: 9},
        //   {key: 10, val: 10},
        // ],
        optionMess:{},
        optionIndex:0,
        optionRtop:0
      }
    },
    methods: {
      InFocus() {
        this.optionShow = true
        if(this.inputVal == ''){
          this.getOption()
        }
      },
      InBlur() {
        // this.optionShow = false
        // this.optionIndex=0
        // this.optionRtop=0
      },
      EventUp() {//向上
        if(this.optionIndex !=0){
          this.optionIndex --
          this.getOption()
          if(this.optionIndex<this.option.length-6 &&this.optionIndex<4){
            this.optionRtop --
            document.getElementById('option').scrollTo(0,this.optionRtop*(document.getElementById('option').scrollHeight/7))
          }
        }
      },
      EventDown() {//向下 document.getElementById('option').scrollHeight  document.getElementById('option').scrollTo(x,y)
        if(this.optionIndex !=this.option.length -1){
          this.optionIndex ++
          this.getOption()
          if(this.optionIndex>6){
            this.optionRtop ++
            // 竖向滚动条置顶(window.scrollTo(0,0);
            document.getElementById('option').scrollTo(0,this.optionRtop*(document.getElementById('option').scrollHeight/7))
          }
        }
      },
      getOption(){
        var mess = this.option[this.optionIndex]
        this.inputVal=''
        this.opVal.forEach((item,index)=>{
          this.inputVal += mess[item]
        })
      },
      optionClick(mess,index){
        this.inputVal = ''
        this.opVal.forEach((item,index)=>{
          this.inputVal += mess[item]
        })
        this.optionIndex = index
        this.optionShow = false
      },
      inChange(){
        this.$emit('inputVal',this.optionMess)
      }
    }
  }
</script>

<style lang="less">
  ul, li {
    list-style: none;
  }

  .option {
    width: 100%;
    border-radius: 4px;
    position: absolute;
    top: 37px;
    background-color: #fff;
    color: #000;
    height: 200px;
    overflow: auto;
    li:hover{
      background-color: #d4d4d4;
    }
  }
</style>
