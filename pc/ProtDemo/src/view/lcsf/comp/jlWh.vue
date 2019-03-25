<template>
  <div style="border: 1px solid #dcdee2;width: 100%;height: 300px;overflow-y: scroll">
    <table style="width: 100%" cellpadding="0" cellspacing="0" align="center" id="dataTable">
      <thead>
        <tr>
          <td>姓名</td>
          <td>驾校</td>
          <td>电话</td>
          <td>删除</td>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(r,index) in tableData" :key="index" @click="rowClick(r)" style="border-top: 1px solid #dcdee2">
          <td>{{r.jlXm}}</td>
          <td>{{r.jlJx}}</td>
          <td>{{r.jlLxdh}}</td>
          <td><Button type="error" size="small" @click="remove(r.id)">删除</Button></td>
        </tr>
      </tbody>
    </table>
    <!--<Table border stripe size="small"-->
           <!--:height="Tabheight"-->
           <!--@on-row-click="rowClick"-->
           <!--:columns="tabTit" :data="tableData"></Table>-->
  </div>
</template>

<script>
  export default {
    name: "jlWh",
    props:{
      DrawerValJL:{
        type:Boolean,
        default:false
      },
      Tabheight:{
        type:Number,
        default:200
      },
      parms:{
        type:Object,
        default:{
          jlJx:'',
          jlXm:'',
        }
      }
    },
    watch:{
      DrawerValJL:function (n,o) {
        this.getPagerList()
      },
      'parms.jlJx':function (n,o) {
        if (n){
          this.getPagerList()
        }
      },
    },
    data(){
      return{
        tabTit:[
          {title:'姓名',key:'jlXm',align:'center'},
          {title:'驾校',key:'jlJx',align:'center',minWidth:110},
          {title:'电话',key:'jlLxdh',align:'center',minWidth:120},
          {title:'删除',width:70,align:'center',render:(h,p)=>{
              return h('Button',{
                props:{type:'error',size:'small'},
                on:{
                  click:()=>{
                    this.remove(p.row.id)
                  }
                }
              },'删')
            }
          }
        ],
        tableData:[]
      }
    },
    created(){
      // alert(this.AF.getPageHeight())
      // this.getPagerList()
    },
    methods:{
      rowClick(row,index){
        this.$emit('JLRowClick',row)
      },
      getPagerList(){
          let p = JSON.parse(JSON.stringify(this.parms));
        console.log(p);
        this.$http.get('/api/lcwxjl/query',{params:p}).then(res=>{
          if(res.code == 200){
            this.tableData = res.result
          }
        }).catch(err=>{})
      },
      remove(id){
        this.swal({
          title:'是否删除！？？',
          type:'warning',
          confirmButtonText: '删除',
          cancelButtonText: '取消',
          showCancelButton: true,
        }).then((res) => {
          if (res.value) {
            this.$http.post('/api/lcwxjl/remove/'+id).then(res=>{
              if(res.code == 200){
                  this.$emit('remove');
                this.getPagerList()
              }
            }).catch(err=>{})
          }
        })
      }
    }
  }
</script>

<style scoped>
  #dataTable tr{
    height: 41px;
    vertical-align: center;
  }
  #dataTable thead{ background-color:#f5f7f9;} /* odd 标识奇数行，even标识偶数行 */
  #dataTable tr:nth-of-type(even){ background-color:#f5f7f9;} /* odd 标识奇数行，even标识偶数行 */
  #dataTable tbody tr:hover{ background-color: #ebf7ff;}
</style>
