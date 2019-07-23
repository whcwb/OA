<template>
  <div>
    <Modal
            v-model="showModal"
            height="600"
            width="900"
            :closable='false'
            :mask-closable="false"
            :title="'安全员工作日志打印'">
      <div class="body" id="page1">
        <div class="header" style="text-align: center">
          <span style="font-size: 20px;font-weight: 600">安全员工作日志</span>
        </div>
        <div>
          <div><span>安全员：{{item.xm}}</span></div>
          <div style="float: right"><span>{{time}}</span></div>
        </div>
        <div>
          <table border="1" cellspacing="0" cellpadding="0" style="width: 100%">
            <thead>
            <tr>
              <td>编号</td>
              <td>学员姓名</td>
              <td>身份证号</td>
              <td>练车次数</td>
              <td>考车号</td>
              <td>驾校</td>
              <td>学员签名</td>
              <td width="160px">副刹原因</td>
              <td width="60px">学员有无异议</td>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item,index) in list" :key="index">
              <td>{{index}}</td>
              <td>{{item.xyXm}}</td>
              <td></td>
              <td>{{item.xyLcCs == 0 ? '' : item.xyLcCs}}</td>
              <td>{{item.xyClBh}}</td>
              <td>{{item.xyJx}}</td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div slot='footer'>
        <Button type="default" @click="v.util.closeDialog(v)" style="color: #949494">取消</Button>
        <Button type="primary" @click="doPrint">打印</Button>
      </div>
    </Modal>
  </div>
</template>

<script>


    import {Printd} from 'printd'

    export default {
        name: '',
        data() {
            return {
                v: this,
                showModal: true,
                operate: "新增",
                editMode: false,
                minTime: '',
                maxTime: '',
                list: [],
                param: {
                    zgId:'',
                    aqyQdsjInRange:'',
                },
                item:{},
                time:'',
                parentParam:{},
            }
        },
        created() {
            this.item = JSON.parse(JSON.stringify(this.$parent.choosedItem));
            this.parentParam = JSON.parse(JSON.stringify(this.$parent.param));
            let now = new Date();
            this.time = now.format("yyyy年MM月dd日")
            if (this.parentParam.aqyQdsjInRange && this.parentParam.aqyQdsjInRange !== '') {
                let a = this.parentParam.aqyQdsjInRange.split(",");
                this.minTime = a[0] + " 00:00:00";
                this.maxTime = a[1] + " 00:00:00";
            }else{
                let now = new Date();
                let today = now.format("yyyy-MM-dd");
                this.minTime = today + " 00:00:00";
                this.maxTime = today + " 23:59:59";
            }
            this.param.zgId = this.item.id;
            this.getData();
        },
        methods: {
          doPrint(how) {
            var myDoc = {
              documents: document, // 打印页面(div)们在本文档中
              copyrights: '杰创软件拥有版权  www.jatools.com', // 版权声明必须
            };
            var jcp = getJCP();
            // 调用打印方法
            // if (how == '打印预览')
            jcp.printPreview(myDoc, false);
            // else if (how == "打印预览(显示进度条)") {
            //   jcp.printPreview(myDoc, true);
            // } else if (how == '弹出打印机选择对话框打印') {
            //   jcp.print(myDoc, true);
            // } else
            // jcp.print(myDoc, false); // 不弹出对话框打印
          },
          getData() {
                this.list = [];
                this.$http.post(this.apis.lcjl.getOneLog, this.param).then((res) => {
                    if (res.code == 200 && res.result) {
                        this.list = res.result.xyList;
                    } else {
                        this.$Message.error(res.message);
                    }
                })
            },
            printClick() {
                var v = this
                this.bzShow = false
                const cssText = `

  table td{
    text-align: center;
  }
        `
                const d = new Printd();
                setTimeout(() => {
                    d.print(document.getElementById('printDivSigUp'), [cssText])
                }, 50)
                setTimeout(() => {
                    v.close()
                }, 300)
            },
            close() {
                this.$parent.componentName = ''
            }
        }
    }
</script>

<style>
  table td {
    text-align: center;
  }
</style>
