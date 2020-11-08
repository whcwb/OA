<style lang="less">
  .treeModal {
    .ivu-modal-content {
      border: #ededed solid 3px;
    }
  }
</style>
<template>
  <div style="height: 100%">
    <div class="box_row" style="height: 100%">
      <div class="box_row_1auto" style="border-right: solid #eee 4px">
        <Tree :data="treeMess" show-checkbox
              @on-check-change="TagClick"></Tree>
      </div>
      <div class="box_row_3auto">
        <Tag type="dot" color="success"
             v-for="(itch,inch) in selectList">{{itch.jgmc}}
        </Tag>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "index",
    data() {
      return {
        treeMess: [],
        selectList: []
      }
    },
    props: {
      checkList: {
        type: Array,
        default: []

      }
    },
    watch: {
      checkList: function (n, o) {
      }
    },
    created() {
      this.selectList = this.checkList
      this.getTree()
    },
    mounted() {
    },
    methods: {
      menSeltct(name, r, l) {
      },

      getTree() {
        var v = this
        this.$http.get(this.apis.FRAMEWORK.getCurrentUserOrgTree, {timers: new Date().getTime()}).then((res) => {
          function yz(arr) {
            if (arr[0].jgdm.length === 3) {
              return arr[0].children
            } else if (arr[0].children && arr[0].children[0].jgdm.length === 3) {
              return arr[0].children[0].children
            } else if(arr[0].jgdm.length === 6){
              return arr
            }
          }
          var tree = yz(res.result)

          if (res.code === 200) {
            if (this.checkList.length == 0) {
              v.treeMess = tree
            } else {
              tree.forEach((item, index) => {
                item.checked = true
                item.children.forEach((itC, inC) => {
                  this.checkList.forEach((val, key) => {
                    if (itC.jgdm == val.jgdm) {
                      itC.checked = true
                    } else {
                      item.checked = false
                    }
                  })
                })
              })
              v.treeMess = tree
            }
          }
        })
      },
      TagClick(list) {
        this.selectList = []
        list.forEach((item, index) => {
          if (item.children.length == 0) {
            item = JSON.stringify(item)
            this.checkPush(item)
          }
        })
      },
      checkPush(obj) {
        obj = JSON.parse(obj)
        obj.jgmc = obj.fjgmc + '/' + obj.jgmc

        this.selectList.push(obj)
        this.checkEmit()
      },
      checkRemove(obj) {
        this.checkList.forEach((item, index) => {
          if (item.jgdm == obj.jgdm) {
            this.checkList.splice(index, 1)
          }
        })
        this.checkEmit()
      },
      checkEmit() {
        this.$emit('checkList', this.selectList)
      }
    }
  }
</script>

<style scoped>

</style>
