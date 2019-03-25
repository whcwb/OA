<style lang="less">
  .framework {
    background: #fff;
    border: solid 1px #ededed;
    .frame-tree {
      width: 170px;
      border-right: solid 2px #d8d8d8;
    }
    .frame-mess {
      padding-top: 8px;
      position: relative;
      .addTree {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        border: solid 3px #989898;
        border-radius: 25px;
        width: 200px;
        height: 200px;
        text-align: center;
        i {
          font-size: 190px;
        }
      }
    }
  }
  .bodyC{
    width: 23%;
    margin:5px 1%;
    .videoFile{
      height: 100%;
      width: 100%;
    }
  }
</style>
<template>
  <div class="boxbackborder">
    <pager-tit title="组织机构管理"></pager-tit>
    <div class="box_row framework"  :style="{height:AF.getPageHeight()-200+'px'}">
      <div class='frame-tree'>
        <div class="box">
          <div class="tit" style="margin: 6px;">
            <Button type="primary" style="width: 100%;font-size: 14px;"
                    @click="rootClick(RootTree)">
              <b>
                组织机构管理
              </b>
            </Button>
          </div>
          <div class="body" style="margin: 6px;"
               :style="RootTree.children.length==0 ? TreeListStyleC : TreeListStyleF">
            <div class="box_col_auto" style="height:500px;overflow: auto">
              <Tree v-for="(item,index) in RootTree.children" :data="item"
                    @on-select-change="treeClick"
                    @on-toggle-expand="treeToggleClick"></Tree>
            </div>
          </div>
        </div>
      </div>
      <div class="box_row_1auto frame-mess">

        <div style="padding: 6px;height: 100%">
          <div class="box_col">
            <div class="tit"
                 style="font-size: 16px;border-bottom:solid 2px #989898;height: 35px;">
              <b v-if="treeMess.title">
                {{treeMess.title}}
              </b>
              <!--<Button style="float: right;margin-right: 8px;" type="primary" shape="circle" icon="md-menu" @click="edit(treeMess)"></Button>-->
              <Button type="error" shape="circle" icon="md-close"  v-if="treeMess.title"
                      style="float: right;margin-right: 8px;" @click="del(treeMess)"></Button>
              <Button style="float: right;margin-right: 8px;" type="primary"
                      shape="circle" icon="md-add" @click="add()"></Button>

            </div>
            <div class="box_col_auto">
              <div class="box_row_list" v-if="treeMess.children">
                <Card class="" v-for="(item,index) in treeMess.children" style="margin: 1%">
                  <p slot="title">
                    <Icon type="ios-film-outline"></Icon>
                    {{item.title}}
                  </p>
                  <div slot="extra">
                    <Button type="primary" shape="circle" icon="md-menu"
                            size="small" @click="edit(item)"></Button>
                    <Button type="error" shape="circle" icon="md-close"
                            size="small" @click="del(item)"></Button>
                  </div>
                  <div>
                    <div>
                      <!--<Row>-->
                      <!--<Col span="12">-->
                      负责人 ：{{item.glyxm}}
                      <!--</Col>-->
                    </div>
                    <div v-show="item.jgdm.length==9">
                      <!--<Col span="12">-->
                      所属 ：{{lx(item.lx)}}
                      <!--</Col>-->
                      <!--</Row>-->
                    </div>
                    <div>
                      联系电话1 ： {{item.lxdh1}}
                    </div>
                    <div>
                      联系电话2 ： {{item.lxdh2}}
                    </div>
                    <div>
                      所在区域 ： {{item.szqy}}
                    </div>
                    <!--<div>-->
                    <!--机构编码 ：{{item.jgbm}}-->
                    <!--</div>-->
                    <div>
                      机构说明 ：{{item.jgsm}}
                    </div>
                    <div>
                      创建时间 ：{{item.cjsj}}
                    </div>
                  </div>
                </Card>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <component :is="componentName"></component>
  </div>
</template>
<script>
  import mixins from '@/mixins'

  import treeList from './comp/treelist.vue'
  import modelForm from './comp/modelForm.vue'

  export default {
    name: '',
    components: {
      treeList, modelForm
    },
    mixins: [mixins],
    data() {
      return {
        jgmc: '',
        tabHeight: {
          height: ''
        },
        TreeListStyleC: "text-align: center",
        TreeListStyleF: "text-align: left",
        componentName: '',
        choosedItem: null,
        mode: 'add',
        parentNode: '',
        currentNode: '',
        RootTree: {
          children: [
          ]
        },
        treeMess: {}
      }
    },
    created() {
    },
    mounted() {
      this.getTree();
    },
    methods: {
      lx(val){
        return this.dictUtil.getValByCode(this, 'ZDCLK1020', val)
      },
      getTree() {
        var v = this
        this.$http.get(this.apis.FRAMEWORK.getCurrentUserOrgTree,{timers:new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            v.RootTree.children = [res.result];
            v.treeClick(v.RootTree.children[0])
          }
        })
      },
      add() {
        this.parentNode = this.choosedItem;
        if (this.parentNode == null) {
          this.$Message.error('请选择福机构');
          return;
        }
        this.mode = 'add';
        this.componentName = 'modelForm';
      },
      edit(item) {
        this.mode = 'md-create';
        this.currentNode = item;
        this.componentName = 'modelForm';
      },
      del(item) {
        console.log(item);
        let ids = [];
        ids.push(item.jgdm);
        this.util.del(this, this.apis.FRAMEWORK.DELE, ids, () => {
          this.getTree();
        });
      },
      treeClick(event) {
        if (event.length > 0) {
          this.choosedItem = event[0];
          this.treeMess = event[0]
        } else {
          this.choosedItem = null;
        }
      },
      treeToggleClick(event) {
        if (event.expand) {
          this.treeClick([event])
        }
      },
      rootClick() {
        var v = this
        v.treeMess = {
          title: v.RootTree.title,
          children: []
        }
        for (var i = 0; i < v.RootTree.children.length; i++) {
          v.treeMess.children.push(v.RootTree.children[i][0])
        }
      }
    }
  }
</script>
