<template>
  <div>
    <Modal
      v-model="showModal"
      height="600"
      width="1200"
      :closable='false'
      :mask-closable="false"
      :title="operate+'用户'">
      <Form
        ref="addmess"
        :model="addmess"
        :rules="ruleInline"
        :label-width="100"
        :styles="{top: '20px'}">
        <Row>
          <Col span="16">
            <div style="overflow: auto;height: 400px;width:600px">
              <Row>
                <Col span="12">
                  <FormItem prop="jgdm" label='组织机构：'>
                    <Cascader @on-change="change" v-model="orgPath" change-on-select :data="orgTree" placeholder="请选择组织机构"
                              filterable clearable></Cascader>
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem prop="zh" label='用户名：'>
                    <Input :readonly="!usermesType" type="text" v-model="addmess.zh" placeholder="请设置用户帐号">
                    </Input>
                  </FormItem>
                </Col>
                <Col span="12" v-if="showPsd">
                  <FormItem prop="mm" label='密码：'>
                    <Input type="password" v-model="addmess.mm" placeholder="请设置用户密码">
                    </Input>
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem prop="xm" label='姓名：'>
                    <Input type="text" v-model="addmess.xm" placeholder="请输入姓名"></Input>
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem label="性别： ">
                    <Select filterable clearable v-model="addmess.xb">
                      <Option value="10">男</Option>
                      <Option value="00">女</Option>
                    </Select>
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem label='类型：'>
                    <Select filterable clearable v-model="addmess.lx">
                      <Option v-for="item in yhlxDict" :value="item.key">{{item.val}}</Option>
                    </Select>
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem label='职务：'>
                    <Input type="text" v-model="addmess.zw" placeholder="请输入职务">
                    </Input>
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem prop="sjh" label='手机号码：'>
                    <Input type="text" v-model="addmess.sjh" placeholder="请输入手机号码"></Input>
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem prop="zjhm" label='证件号码：'>
                    <Input type="text" v-model="addmess.zjhm" placeholder="请输入证件号码"></Input>
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem label="附属机构： ">
                    <Select filterable clearable multiple v-model="jgdms"><Option v-for="(item,index) in fsjgList" :value="item.value" :key="index">{{item.label}}</Option>
                    </Select>
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem label="权限控制： ">
                    <Select  v-model="qxkz">
                      <Option value="p" key="p">自定义权限</Option>
                      <Option value="r" key="r">角色选择</Option>
                    </Select>
                  </FormItem>
                </Col>
              </Row>
            </div>
          </Col>
          <Col v-if="qxkz =='p'"  span="8"  style="height: 580px;overflow: scroll">
            <FormItem label='权限选择:'>
              <menu-choose v-if="showTree" :data="permissionTree" :choosedData="roleFunctionCodes"
                           @treeChange="treeChange"></menu-choose>
              <!--<Tree :data="permissionTree" show-checkbox multiple></Tree>-->
            </FormItem>
            <!--<Button type="primary" @click="editPermission">修改权限</Button>-->
          </Col>
          <Col v-if="qxkz =='r'" span="8"  style="height: 580px;overflow: scroll">
            <select-role v-if="showRoleTable"  :hasIds="hasRoleIds" @arrIds="arrIds"></select-role>
          </Col>
        </Row>

      </Form>
      <div slot='footer'>
        <Button type="default" @click="colse" style="color: #949494">取消</Button>
        <Button type="primary" @click="AddDataListOk('addmess')">确定</Button>
      </div>
    </Modal>
  </div>
</template>

<script>

  import menuChoose from '../../../components/menuChoose'
  import selectRole from '../../system-root/selectTable'

  export default {
    components: {menuChoose,selectRole},
    name: '',
    watch:{
      'jgdms':function (n,o) {
        console.log('+++++',n);
      }
    },
    data() {
      return {
        qxkz:'p',
        showModal: true,
        showRoleTable: false,
        showTree: false,
        operate: "新增",
        //新增数据
        jgdms:[],
        permissionTree: [],
        roleFunctionCodes: [],
        addmess: {
          zh: '',
          xm: '',
          mm: '',
          lx: '',
          xb: '0',
          zw: '',
          sjh: '',
          zjhm: '',
          jgdm: '',
          jgdms:'',
          roleId:''
        },
        showPsd: false,
        ruleInline: {
          zh: [
            {required: true, message: '请输入用户名', trigger: 'blur'}
          ],
          xm: [
            {required: true, message: '请输入姓名', trigger: 'blur'}
          ],
          sjh: [
            {required: true, message: '请输入手机号码', trigger: 'blur'}
          ],
        },
        yhlxDict: [],
        yhlxDictCode: 'ZDCLK0003',
        orgList: [],
        choosedIds:[],
        hasRoleIds: [],
        userRoles:[],
        checkGroup: [],
        treeValue: [],
        orgTree: [],
        orgPath: [],
        fsjgList:[],//附属机构list
      }
    },
    props: {
      usermesType: {
        type: Boolean,
        default: true
      },
      usermes: {
        type: Object,
        default: {}
      }
    },
    created() {
      this.fullcal()
      if (this.usermesType) {
        this.showPsd = true;
      } else {
        this.addmess = this.usermes
        this.jgdms = this.addmess.jgdms.split(',')
        this.operate = '编辑'
        this.getUserRoles();
        this.getOrgPath(this.addmess.jgdm);
      }
      this.yhlxDict = this.$parent.yhlxDict
      this.getOrgTree();
      this.getOrgPermissionTree();
    },
    methods: {
      getUserRoles() {
        this.$http.get(this.apis.ROLE.GET_USER_ROLES + '?userId=' + this.addmess.yhid,{timers:new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            if (res.result) {
              this.userRoles = res.result;
              this.hasRoleIds = [];
              for (let r of this.userRoles) {
                if (r.jslx == '40')continue
                this.hasRoleIds.push(r.jsId);
                this.checkGroup.push(r.jsId);
              }
            } else {
              this.userRoles = [];
            }
            this.showRoleTable = true;
          }
        })
      },
      arrIds(jsIds) {
        let ids = [];
        for (let r of jsIds) {
          ids.push(r);
        }
        this.checkGroup = ids;
        console.log('checkGroup',this.checkGroup);
      },
      editUserRoles() {
        let v = this;
        this.$http.post(this.apis.ROLE.MODIFY_USER_ROLES, {
          userId: this.addmess.yhid,
          roleIds: this.checkGroup.join(',')
        }).then((res) => {
          if (res.code === 200) {
            // this.$Message.success('操作成功');
          }else{
            this.$Message.error(res.message);
          }
        })
      },
      treeChange(e) {
        this.choosedIds = e;
      },
      change(vaule, selectedData) {
        this.addmess.jgdm = selectedData[selectedData.length - 1].value;
        this.permissionTree = [];
        this.$http.get(this.apis.FUNCTION.getPermissionTreeWithChecked + "?hideSystem=true&parentCode=" + this.addmess.jgdm,{timers:new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            this.permissionTree = res.result;
            if (!this.usermesType) {
              this.getRoleFunctions();
            } else {
              this.showTree = true;
            }
          }
        })
        this.treeValue = vaule;
      },
      editPermission() {
        this.getChoosedIds(this.permissionTree);
        let ids = '';
        for (let r of this.choosedIds) {
          ids += r + ',';
        }
        this.$http.post(this.apis.FUNCTION.SET_ROLE_FUNCTIONS, {
          'jsdm': this.addmess.roleId,
          'gndms': ids
        }).then((res) => {
          if (res.code === 200) {
            // this.$Message.success(res.message);
          }else{
            this.$Message.error(res.message)
          }
        })
      },
      getChoosedIds(list) {
        for (let r of list) {
          if (r.checked) this.choosedIds.push(r.gndm);
          if (r.children) {
            this.getChoosedIds(r.children);
          }
        }
      },
      getOrgPermissionTree() {
        let orgCode = '';
        if (!this.usermesType) {
          orgCode = this.addmess.jgdm;
        } else {
          let userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
          orgCode = userInfo.jgdm;
        }
        this.permissionTree = [];
        this.$http.get(this.apis.FUNCTION.getPermissionTreeWithChecked + "?hideSystem=true&parentCode=" + orgCode,{timers:new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            this.permissionTree = res.result;
            if (!this.usermesType) {
              this.getRoleFunctions();
            } else {
              this.showTree = true;
            }
          }
        })
      },
      getRoleFunctions() {
        // console.log('wwwwwwwwwwww',this.addmess);
        let url = this.apis.FUNCTION.GET_ROLE_FUNCTIONS + "?jsdm=ur" + this.addmess.yhid;
        this.$http.get(url,{timers:new Date().getTime()}).then((res) => {
          if (res.code === 200 && res.result) {
            for (let r of res.result) {
              this.roleFunctionCodes.push(r.gndm);
            }
            this.choosedIds = this.roleFunctionCodes;
          }
          this.showTree = true;
        })
      },
      setPermissionChecked(list) {
        for (let r of list) {
          if (this.roleFunctionCodes.indexOf(r.gndm) >= 0) {
            r.checked = true;
          }
          if (r.children && r.children.length > 0) {
            this.setPermissionChecked(r.children);
          }
        }
      },
      setRolePermission() {
        this.getChoosedIds(this.permissionTree);
        let ids = '';
        for (let r of this.choosedIds) {
          ids += r + ',';
        }
        this.$http.post(this.apis.FUNCTION.SET_ROLE_FUNCTIONS, {
          'jsdm': this.addmess.jsId,
          'gndms': ids
        }).then((res) => {
          if (res.code === 200) {
            this.$Message.success(res.message);
          }
        })
      },
      getChoosedIds(list) {
        for (let r of list) {
          if (r.checked) this.choosedIds.push(r.gndm);
          if (r.children) {
            this.getChoosedIds(r.children);
          }
        }
      },
      getOrgPath(orgCode) {
        this.orgPath = [];
        this.$http.get(this.apis.FRAMEWORK.getOrgPath + '?orgCode=' + orgCode,{timers:new Date().getTime()}).then((res) => {
          if (res.code == 200) {
            for (let r of res.result) {
              this.orgPath.push(r.jgdm);
            }
          }
        })
      },
      getOrgTree() {
        this.$http.get(this.apis.FRAMEWORK.getCurrentOrgTree,{timers:new Date().getTime()}).then((res) => {
          // if()
          this.usermess = JSON.parse(sessionStorage.getItem('userInfo'))
          // console.log('123',res.result);
          if(this.usermess.xm == "超级管理员"){
            this.orgTree = res.result[0].children
          }else {
            this.orgTree = res.result
          }


          if(res.result[0].value.length == 3){
            this.fsjgList = res.result[0].children[0].children
          }else if(res.result[0].value.length == 6){
            this.fsjgList = res.result[0].children
          }else if(res.result[0].value.length == 9){
            this.fsjgList = res.result[0]
          }
        })
      },
      getOrgList() {
        let v = this;
        v.$http.get(this.apis.FRAMEWORK.QUERY, {params: {pageSize: 10000,timers:new Date().getTime()}}).then((res) => {
          if (res.code === 200) {
            this.orgList = res.page.list;
          } else {
            v.$Message.error(res.message);
          }
        }).catch((error) => {
          v.$Message.error('出错了！！！');
        })
      },
      fullcal() {
        console.log('信息', this.usermes)
      },
      colse() {
        var v = this
        v.$parent.compName = ''
      },
      //确认添加新用户进行前台表单数据验证
      AddDataListOk(name) {
        if (this.qxkz != 'p'){
          this.editUserRoles();
        }
        var v = this
        this.$refs[name].validate((valid) => {
          if (valid) {
            v.addmess.jgdms=v.jgdms.join(',')
//                    	新增
            if (v.usermesType) {
              v.$http.post(this.apis.USER.ADD, v.addmess).then((res) => {
                if (res.code === 200) {
                  v.$Message.success('用户注册成功');
                  if(this.qxkz == 'p'){
                    this.addmess.roleId ='ur' +  res.result;
                    this.editPermission();
                  }
                  v.$emit('listF', res)
                } else {
                  v.$Message.error(res.message);
                }
              }).catch((error) => {
                v.$Message.error('出错了！！！');
              })
            } else {
              delete v.addmess.mm;
              v.$http.post(this.apis.USER.CHANGE, v.addmess).then((res) => {
                if (res.code === 200) {
                  v.$Message.success('用户修改成功');
                  v.$emit('listF', res)
                  if(this.qxkz == 'p'){
                    this.addmess.roleId ='ur' +  v.addmess.yhid;
                    this.editPermission();
                  }
                } else {
                  v.$Message.error(res.message);
                }
              }).catch((error) => {
                v.$Message.error('出错了！！！');
              })
            }
          } else {
            v.$Message.error('请认真填写用户信息!');
          }
        })
      },
    }
  }
</script>

<style>
</style>
