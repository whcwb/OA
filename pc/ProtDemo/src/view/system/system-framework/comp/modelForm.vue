<style lang="less">
</style>
<template>
  <div>
    <Modal v-model="showModal" width='900' :closable='mesF'
           :mask-closable="mesF"
           :title="operate+'机构'">
      <div style="height:680px;overflow: scroll;">
        <Form :rules="ruleInline" ref="formItem" :model="formItem">
          <Row>
            <Col span="10">
              <FormItem label='机构名称' prop="jgmc">
                <Input type="text" v-model="formItem.jgmc" placeholder="请填写机构名称"></Input>
              </FormItem>
              <FormItem label='机构负责人'>
                <Input type="text" v-model="formItem.glyxm" placeholder="请填写机构负责人"></Input>
              </FormItem>
              <FormItem label='机构类型' prop="lx" v-if="formItem.fjgdm.length == 6">
                <Select clearable v-model="formItem.lx">
                  <Option :value="item.key" v-for="item in jdTypList" :key="item.key">{{item.val}}</Option>
                </Select>
              </FormItem>
              <FormItem label=所在区域>
                <Select clearable v-model="formItem.szqy">
                  <Option :value="item.val" v-for="item in areaList" :key="item.key">{{item.val}}</Option>
                </Select>
              </FormItem>
              <FormItem label='联系电话1'>
                <Input type="text" v-model="formItem.lxdh1" placeholder="联系电话1"></Input>
              </FormItem>
              <FormItem label='联系电话2'>
                <Input type="text" v-model="formItem.lxdh2" placeholder="联系电话2"></Input>
              </FormItem>
              <FormItem label='机构说明'>
                <Input type="text" v-model="formItem.jgsm" placeholder="请填写机构说明"></Input>
              </FormItem>
            </Col>
            <Col span="10" offset="4" style="height: 580px;overflow: scroll">
              <FormItem label='权限选择:'>
                <br>
                <menu-choose v-if="showTree" :data="orgTree" :choosedData="hasPermissionCodes"
                             @treeChange="treeChange"></menu-choose>
                <!--<Tree :data="orgTree" show-checkbox multiple></Tree>-->
              </FormItem>
            </Col>
          </Row>
        </Form>
      </div>
      <div slot='footer'>
        <Button type="default" @click="colse" style="color: #949494">取消</Button>
        <Button type="primary" @click="save('formItem')">确定</Button>
      </div>
    </Modal>
  </div>
</template>

<script>

  import menuChoose from '../../../components/menuChoose'

  export default {
    name: '',
    components: {menuChoose},
    data() {
      return {
        areaList: '',
        areaList: this.dictUtil.getByCode(this, 'ZDCLK1009'),
        showModal: true,
        choosedData: [],
        operate: '新建',
        mesF: false,
        edit: false,
        showTree: false,
        formItem: {
          glyxm:'',//管理员姓名
          fjgdm: '',
          jgmc: '',//机构名称
          szqy:'',//所在区域
          lxdh1:'',
          lxdh2:'',
          lx:'',
        },
        ruleInline: {
          jgmc: [
            {required: true, message: '请输入机构名称', trigger: 'blur'}
          ],
          lx: [
            {required: true, message: '请输入机构名称', trigger: 'change'}
          ],
        },
        userList: [],
        orgTree: [],
        choosedIds: [],
        mode: 'add',
        jdgm: '',
        parentCode: '',
        sonCode: '',
        newOrgCode: '',
        hasPermissionCodes: [],
        jdTypList:[]
      }
    },
    created() {
      this.getJdTypList()
    },
    mounted() {
      this.mode = this.$parent.mode;
      if (this.mode == 'md-create') {
        this.operate = '编辑'
        this.formItem = this.$parent.currentNode;
        this.parentCode = this.$parent.currentNode.fjgdm;
        this.sonCode = this.$parent.currentNode.jgdm;
        this.getHasPermissionCodes();
        this.getOrgPermissionTree();
      } else {
        this.operate = '新增'
        this.formItem.fjgdm = this.$parent.parentNode.jgdm;
        this.parentCode = this.$parent.parentNode.jgdm;
        this.getOrgPermissionTree();
      }
      this.getUserList();
    },
    methods: {
      getJdTypList(){
        this.jdTypList = this.dictUtil.getByCode(this,'ZDCLK1020');
      },
      treeChange(e) {
        this.choosedIds = e;
      },
      // getChoosedIds(list){
      //     for(let r of list){
      //         if (r.checked)this.choosedIds.push(r.gndm);
      //         if (r.children){
      //             this.getChoosedIds(r.children);
      //         }
      //     }
      // },
      getOrgPermissionTree() {
        this.orgTree = [];
        this.$http.get(this.apis.FUNCTION.getPermissionTreeWithChecked + "?hideSystem=true&parentCode=" + this.parentCode + '&sonCode=' + this.sonCode, {timers: new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            this.orgTree = res.result;
            this.showTree = true;
          }
        })
      },
      getHasPermissionCodes() {
        this.hasPermissionCodes = [];
        this.$http.get(this.apis.FUNCTION.GET_ORG_FUNCTIONS + "?jgdm=" + this.formItem.jgdm, {timers: new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            if (res.result) {
              for (let r of res.result) {
                this.hasPermissionCodes.push(r.gndm);
              }
              this.choosedIds = this.hasPermissionCodes;
            }
            this.showTree = this.orgTree.length > 0;
          }
        })
      },
      getUserPermissionTree() {
        this.orgTree = [];
        this.$http.get(this.apis.FUNCTION.GET_USER_PERMISSION_TREE, {timers: new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            this.orgTree = res.result;
          }
        })
      },
      setOrgPermission() {
        // this.choosedIds = [];
        // this.getChoosedIds(this.orgTree);
        let ids = '';
        for (let r of this.choosedIds) {
          ids += r + ',';
        }
        let orgCode = this.formItem.jgdm;
        if (this.mode == 'add') {
          orgCode = this.newOrgCode;
        }
        this.$http.post(this.apis.FUNCTION.SET_ORG_FUNCTIONS, {'jgdm': orgCode, 'gndms': ids}).then((res) => {
          if (res.code === 200) {
            this.$Message.success(res.message);
          } else {
            this.$Message.error(res.message);
          }
        })
      },
      getUserList() {
        let userInfoJson = sessionStorage.getItem("userInfo");
        let userInfo = JSON.parse(userInfoJson);
        let jgdm = userInfo.jgdm;
        // console.log('jgdm', jgdm);
        this.$http.get(this.apis.USER.QUERY, {
          params: {
            jgdmStartWith: jgdm,
            timers: new Date().getTime()
          }
        }).then((res) => {
          if (res.code === 200 && res.page.list) {
            this.userList = res.page.list;
          }
        })
      },
      save(name) {
        var v = this
        v.SpinShow = true
        this.$refs[name].validate((valid) => {
          if (valid) {
            let url = this.apis.FRAMEWORK.ADD;
            if (this.mode == 'md-create') {
              url = this.apis.FRAMEWORK.CHANGE;
            }
            delete this.formItem.children;
            delete this.formItem.cjsj;
            this.$http.post(url, this.formItem).then((res) => {
              if (res.code === 200) {
                this.newOrgCode = res.result;
                this.setOrgPermission();
              } else {
                v.$Message.error(res.message);
              }
              v.$parent.componentName = ''
              v.$parent.getTree()
            })
          } else {
            v.SpinShow = false
            v.$Message.warning('请认真填写相关信息!');
          }
        })
      },
      colse() {
        var v = this
        v.$parent.componentName = ''
      }
    }
  }
</script>

<style>

</style>
