<!--
仓库组件
-->
<template>
  <Card class="info-card-wrapper" :padding="0"
        style="height: 220px;min-width: 220px;width: 100%">
    <div class="box_col">
      <div class="boxTop">
        {{mess.archivesCode}} / {{mess.capacity}}
        <Tooltip content="档案详情" placement="right">
          <Button size="small" type="text" @click="arcEvent(mess)">
            <Icon type="md-arrow-round-forward" size='22'/>
          </Button>
        </Tooltip>
      </div>
      <div class="box_col_100">
        <div class="box_row" style="height: 100%">
          <Tooltip class="boxLeft"  v-if="mess.num!=mess.capacity"
                   :style="{width:(mess.capacity-mess.num)/mess.capacity*100+'%'}"
                   content="可用" placement="bottom">
            <div>
              {{(mess.capacity-mess.num)}}
            </div>
          </Tooltip>
          <Tooltip class="box_row_100 boxRight" content="已用" placement="bottom" v-if="mess.num!=0">
            <div class="">
              {{mess.num}}
            </div>
          </Tooltip>
        </div>
      </div>
      <div class="botBut box_row">
        <div class="box_row_100">
          <Button long type="error" @click="remove(mess.id)">
            <Tooltip content="删除" placement="top" style="width: 100%">
              <Icon type="ios-trash" size="18"/>
            </Tooltip>
          </Button>
        </div>
      </div>
    </div>
  </Card>
</template>

<script>
  export default {
    name: 'arcItem',
    components: {},
    props: {
      mess:Object
    },
    data() {
      return {
      }
    },
    computed: {},
    methods: {
      arcEvent(mess) {
        this.$emit('messTab', mess.archivesCode)
      },
      remove(id) {
        this.swal({
          title: "确定删除？",
          text: "",
          type: "warning",
          confirmButtonText: '删除',
          cancelButtonText: '取消',
          showCancelButton: true,
        }).then((res) => {
          if (res.value) {
            this.$http.post(this.apis.DAK.DELE+id).then((res)=>{
              if (res.code == 200){
                this.$emit('remove', id)
              }
              this.$Message.info(res.message);
            }).catch((err)=>{

            })

          } else {
            this.$Message.info('操作取消');
          }
        });
      }
    }
  }
</script>

<style lang="less">
  .tl {
    text-align: center;
    line-height: 140px;
    color: #fff;
    font-weight: 700;
  }

  .info-card-wrapper {
    .ivu-card-body {
      width: 100%;
      height: 100%;
    }
    .boxTop {
      color: #828282;
      border: #f2f2f2 1px solid;
      height: 45px;
      line-height: 45px;
      text-align: center;
      font-weight: 700;
      /*background: linear-gradient(to bottom right, #19be6b 50%, #ed3f14 50%);*/
      background-color: #f2f2f2;
      position: relative;
      .ivu-tooltip {
        position: absolute;
        right: 0px;
        top: 50%;
        transform: translateY(-50%);
        z-index: 100;
      }
      .button {
        outline: none;
      }
    }
    .boxLeft {
      .tl;
      background: #49cc8a;
    }
    .boxRight {
      .tl;
      background: #ff9900;
    }
    .botBut {
      border-top: #f2f2f2 1px solid;
      button {
        border-radius: 0;
      }
    }

  }

  .poab {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }

</style>
