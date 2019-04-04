<template>
  <div>
    <audio v-if="aSrc" id="playTTS" autoplay="autoplay" :src="aSrc"></audio>
    <Modal
      height="700px"
      width="1200px"
      v-model="modal1"
      title="预约登记"
      :closable='false'
      :mask-closable="false"
      >
      <div>
        <Table ref="ref" height="500" @on-expand="expand" stripe :columns="columns1" :data="yydata" ></Table>
      </div>
      <div slot='footer'>
        <Button type="default" @click="cancel" style="color: #949494">关闭</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import selectyy from './orderListExpand'
  import giveCar from '../comp/readCard'
  export default {
    name: "keyypd",
    components:{
      selectyy
    },
    data() {
      return {
        readingCard:false,
        giveCar:giveCar,
        modal1: true,
        tok:'',
        aSrc:'',
        formData: {
          jllx: '',
          jlid: '',
          xurs: '',
          xyxm: '',
          xydh: '',
          yjf: '',
          bz: ''
        },
        columns1: [
          {
            title: '序号',
            type: 'index',
            align: 'center',
          },
          {
            title: '教练姓名',
            align: 'center',
            key: 'jlXm'
          },
          {
            title: '车型',
            align: 'center',
            key: 'jlCx'
          },
          {
            title: '所属驾校',
            align: 'center',
            key: 'jlJx'
          },
          {
            title: '学员人数',
            align: 'center',
            key: 'xySl'
          },
          {
            title: '登记时间',
            align: 'center',
            minWidth:120,
            key: 'cjsj'
          },
          {
            title: '发车',
            align: 'center',
            type: 'expand',
            width: 80,
            render: (h, p) => {
              return h(selectyy,{
                props: {
                  row: this.choosedItem,
                  km:'2'
                },
                on:{
                  FcSave:()=>{
                    this.getYYdj();
                    // this.$parent.getYYdj();
                    console.log(this.$parent);
                    this.util.getPageData(this.$parent);
                    //this.util.initTable(this);
                  }
                }
              })
            }
          },
          {
            title: '叫号',
            align: 'center',
            width: 80,
            render: (h, p) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'primary',
                    size: 'small',
                    icon:'md-volume-up'
                  },
                  on: {
                    click: () => {
                      this.JHvideo(p.row)
                    }
                  }
                })
              ])
            }
          },
          {
            title: '删除',
            align: 'center',
            key: 'address',
            render: (h, p) => {
              return h('div', [
                // h('Button', {
                //   props: {
                //     type: 'primary',
                //     size: 'small',
                //   },
                //   style: {
                //     marginRight: '10px'
                //   },
                //   on: {
                //     click: () => {
                //       this.modal1 = false;
                //       this.$parent.compHisName = '';
                //       this.fache(p.row)
                //     }
                //   }
                // }, '发'),
                h('Button', {
                  props: {
                    type: 'error',
                    size: 'small',
                  },
                  style: {
                    marginRight: '10px'
                  },
                  on: {
                    click:()=>{
                      this.delyyjl(p.row.id);
                      this.$parent.DrawerVal = true;
                    }
                  }
                }, '删')
              ])
            }
          }
        ],
        yydata: [],
        choosedItem:null,
      }
    },
    created() {
      this.getTok()
      this.getYYdj()
    },
    methods: {
      getTok(){
        this.$http.post('/pub/getvoicetkey').then(res=>{
          if(res.code == 200){
            this.tok = res.result
          }
        })
      },
      JHvideo(row){
        // console.log('row', this.tok);
        let text = encodeURIComponent(row.jlXm+",请来前台取卡!"+row.jlXm+",请来前台取卡!"+row.jlXm+",请来前台取卡!");
        // console.log(text)
        this.aSrc = "https://tsn.baidu.com/text2audio?tok="+this.tok+"&per=0&spd=4&ctp=1&lan=zh&cuid=123456890987654321&tex=" + text;
        setTimeout(()=>{
          this.aSrc = ''
        },10*1000)
      },
      getCarNoNum(row){
        let num = this.choosedItem.cardNo;
        this.$http.post('/api/lccl/getCar', {
          notShowLoading: 'true',
          pagerNum: 1,
          pageSize: 99999,
          clKm: "2",
          cardNo:num,
        }).then((res) => {
          if (res.code == 200) {
            console.log(res);
            if(res.page.list.length == 0){
              this.choosedItem.lcClId=""
            }else {
              let car =  res.page.list[0];
              console.log(car);
              if(car.clKm !== '2'){
                this.$refs.ref.objData[row.index]._isExpanded = false
                this.swal({
                  title: '此卡已绑定科目三车辆,请更换卡片',
                  type: 'warning',
                  confirmButtonText: '关闭'
                })
                return
              }
              this.choosedItem.lcClId = car.id;
            }
          } else {
            this.$Message.info(res.message);
          }
        })
      },
      expand(row,status){
        row.cardNo = ''
        this.readingCard = status
        this.choosedItem = status?row:null
        status && this.readkar(row);
      },
      readkar(row){
        if (!!window.ActiveXObject || "ActiveXObject" in window){
        }else{
          this.swal({
            title: '请使用IE10以上的浏览器',
            type: 'warning',
            confirmButtonText: '关闭'
          })
          return
        }
        var v = this
        this.giveCar.readCard((key,mess)=>{
          if(!key){
            this.$refs.ref.objData[row.index]._isExpanded = false
            if(mess === "打开设备失败"){
              return
            }
            if (this.readingCard){
              let v = this
              setTimeout(()=>{
                if (v.readingCard) {
                  this.readkar(row)
                }
              },200)
            }
            if (v.showFQfzkp){
              return;
            }
            v.showFQfzkp = true;
            v.swal({
              title:mess,
              type:'error',
              confirmButtonText: '读卡',
              cancelButtonText: '关闭',
              showCancelButton: true
            }).then((res) => {
              if(res.value){
                v.showFQfzkp = false;
                v.readkar(row)
              }else {
                v.showFQfzkp = false;
                v.showQfshowFQfzkpzkp = false;
                v.readingCard = false
              }
            })
          }else {
            v.swal.close()
            this.$refs.ref.objData[row.index]._isExpanded = false
            this.AF.carCard('2',mess,key=>{
              if(key){
                this.$refs.ref.objData[row.index]._isExpanded = true
                v.showFQfzkp = false;
                this.choosedItem.cardNo = mess;
                this.getCarNoNum(row)
              }else {
                this.choosedItem.cardNo = '';
              }
            })
          }
        })
      },
      delyyjl(p){
        this.swal({
          title:'是否删除？',
          type:'question',
          showCancelButton: true,
          confirmButtonText: '删除！',
          cancelButtonText: '取消！'
        }).then(val=>{
          if(val.value){
            this.$http.post('/api/lcjl/remove/'+p).then(res=>{
              if(res.code == 200){
                this.swal({
                  type:'success',
                  title:res.message
                })
                this.getYYdj();
                this.$parent.getYYdj();
                this.cancel()
              }else{
                this.swal({
                  type:'error',
                  title:res.message
                })
              }
            }).catch(err=>{})
          }
        })
      },
      fache(e) {
        this.$emit('confirm', e);
      },
      cancel(){
        this.$emit('colse');
      },
      getYYdj() {
        this.$http.get('/api/lcjl/query', {params: {kssjIsNull: '1', orderBy: 'cjsj asc',lcKm:'2'}}).then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.yydata = res.result
            for(let i=0;i<this.yydata.length;i++){
              this.yydata[i].index = i;
            }
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
