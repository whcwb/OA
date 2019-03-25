<style lang="less">
  .treeModal {
    .ivu-modal-content {
      border: #ededed solid 3px;
    }
  }
</style>
<template>
  <div class="box_col" style="width: 100%">
    <div class="box_col_auto">
      <Tabs v-if="treeMess[0] && treeMess[0].children"
            type="card">
        <TabPane :label="item.jgmc" v-for="(item,index) in treeMess[0].children">
          <div style="padding: 16px;padding-top: 0;overflow: auto">
            <div class="box_row_list">
              <Tag type="dot" :color="itch.tagTyp"
                   v-for="(itch,inch) in item.children"
                   @click.native="TagClick(item.jgmc,itch)">
                {{itch.jgmc}}
              </Tag>

            </div>
          </div>
        </TabPane>
      </Tabs>
    </div>
    <div class="box_col_auto">
      <Tag type="dot" color="success"
           v-for="(itch,inch) in checkList">{{itch.jgmc}}
      </Tag>
    </div>
  </div>
</template>

<script>
  export default {
    name: "index",
    data() {
      return {
        modalShow: true,
        treeMess: [],
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
        console.log(n);
      }
    },
    created() {
      // console.log('++++',this.checkList);
    },
    mounted() {
      this.getTree()
    },
    methods: {
      getTree() {
        var v = this
        this.$http.get(this.apis.FRAMEWORK.getCurrentUserOrgTree,{timers:new Date().getTime()}).then((res) => {
          if (res.code === 200) {
            if (this.checkList.length == 0) {
              res.result[0].children.forEach((item1, index1) => {
                item1.tagTyp = 'primary'
                if (item1.children && item1.children.length > 0) {
                  item1.children.forEach((item2, index2) => {
                    item2.tagTyp = 'primary'
                    if (item2.children && item2.children.length > 0) {
                      item2.children.forEach((item3, index3) => {
                        item3.tagTyp = 'primary'
                        if (item3.children && item3.children.length > 0) {
                          item3.children.forEach((item4, index4) => {
                            item4.tagTyp = 'primary'
                          })
                        }
                      })
                    }
                  })
                }
              })
            } else {
              res.result[0].children.forEach((item1, index1) => {
                this.checkList.forEach((val, key) => {
                  if (val.jgdm == item1.jgdm) {
                    item1.tagTyp = 'success'
                  } else if (item1.tagTyp != 'success') {
                    item1.tagTyp = 'primary'
                  }
                })
                if (item1.children && item1.children.length > 0) {
                  item1.children.forEach((item2, index2) => {
                    this.checkList.forEach((val, key) => {
                      if (val.jgdm == item2.jgdm) {
                        item2.tagTyp = 'success'
                      } else if (item2.tagTyp != 'success') {
                        item2.tagTyp = 'primary'
                      }
                    })
                    if (item2.children && item2.children.length > 0) {
                      item2.children.forEach((item3, index3) => {
                        this.checkList.forEach((val, key) => {
                          if (item3.jgdm == val.jgdm) {
                            item3.tagTyp = 'success'
                          } else if (item3.tagTyp != 'success') {
                            item3.tagTyp = 'primary'
                          }
                          // console.log(res.result);
                        })
                        if (item3.children && item3.children.length > 0) {
                          item3.children.forEach((item4, index4) => {
                            this.checkList.forEach((val, key) => {
                              if (val.jgdm == item4.jgdm) {
                                item4.tagTyp = 'success'
                              } else if (tem4.tagTyp != 'success') {
                                item4.tagTyp = 'primary'
                              }

                            })
                          })
                        }
                      })
                    }
                  })
                }
              })
            }
            v.treeMess = res.result;
          }
        })
      },
      TagClick(fName, obj) {
        if (obj.tagTyp == 'primary') {
          obj.tagTyp = 'success'
          // obj.jgmc = fName + '/' + obj.jgmc
          // let a = obj
          //     a.jgmc = fName + '/' + a.jgmc
          //
          obj = JSON.stringify(obj)
          console.log(obj);
          this.checkPush(fName, obj)
        } else if (obj.tagTyp == 'success') {
          obj.tagTyp = 'primary'
          this.checkRemove(obj)
        }
      },
      checkPush(fName, obj) {
        obj = JSON.parse(obj)
        obj.jgmc = fName + '/' + obj.jgmc
        console.log(obj);

        this.checkList.push(obj)
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
        this.$emit('checkList', this.checkList)
      }
    }
  }
</script>

<style scoped>

</style>
