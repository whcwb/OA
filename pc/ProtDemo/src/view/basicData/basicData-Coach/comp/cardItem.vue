<template>
  <Card class="coachMess" style="width: 390px">
    <div class="box_row colCenter userboxSty" style="padding-top: 0">
      <img :src="apiUrl + mess.headImg"
           v-if="mess.headImg"
           style="width: 50px;">
      <Icon v-else type="ios-contact" size="50"/>

      <div class="pelMess box_row_100">
        <div class="box_row">
          <div class="name box_row_100">
            {{mess.coachName}}
          </div>
          <div class="box_row_100">
            <Icon type="md-shuffle"/>
            {{mess.gender | gender}}
          </div>
          <div class="phone box_row_100">
            {{mess.phone}}
          </div>
        </div>
        <div class="box_row" style="padding-top: 6px">
          <div class="box_row_100">
            <Icon type="ios-pricetag"/>
            {{mess.jgmc}}
          </div>
          <div class="box_row_100" style="text-align: center">
            <Icon type="ios-car"/>
            {{mess.drivingType}}
          </div>
        </div>
      </div>
    </div>

    <div class="box_row colCenter userboxSty">
      <div class="pelMess box_row_100">
        <Icon type="md-bookmark"/>
        {{mess.coachSub |coachSub}}
      </div>
      <div class="pelMess box_row_100">
        <Icon type="logo-buffer"/>
        <Icon type="md-happy" color="#ffd600" v-for="item in parseInt(mess.coachRate)"/>
      </div>
    </div>
    <div class="box_row colCenter userboxSty">
      <div class="pelMess box_row_100">
        <Icon type="ios-speedometer"/>
        {{mess.drivingYears}}年
      </div>
      <div class="pelMess box_row_100">
        <Icon type="md-contacts"/>
        {{mess.traineeNum}}人
      </div>
      <div class="pelMess box_row_100">
        <Icon type="ios-leaf"/>
        {{mess.area | area}}
      </div>
    </div>
    <div class="box_row colCenter userboxSty">
      <div class="pelMess box_row_100">
        <Tooltip content="驾驶证 " placement="right">
          <Icon type="ios-card"/>
          {{mess.licenseStartTime}} ~ {{mess.licenseEndTime}}
        </Tooltip>
      </div>
    </div>
    <div class="box_row colCenter userboxSty" style="border: none">
      <div class="pelMess box_row_100">
        <Tooltip content="身份证" placement="right">
          <Icon type="ios-card-outline"/>
          {{mess.coachStartTime}} ~ {{mess.coachEndTime}}
        </Tooltip>
      </div>
    </div>
    <div class="box_row bottomBut">
      <div class="box_row_100 br" @click="change(mess)">
        <Icon type="md-create" size="18"/>
      </div>
      <div class="box_row_100" @click="dele(mess.id)">
        <Icon type="ios-trash" size="18"/>
      </div>
    </div>
  </Card>
</template>

<script>
  import mixin from '@/mixins'

  export default {
    name: "cardItem",
    mixins: [mixin],
    data() {
      return {
        apiUrl: this.apis.getImgUrl
      }
    },
    props: {
      mess: {
        type: Object,
        default: {
          coachName: '张筱筱'
        }
      }
    },
    created() {
    },
    methods: {
      dele(id) {
        this.$emit('dele', id)
      },
      change(mess) {
        this.$emit('change', mess)
      }
    }
  }
</script>

<style lang="less">
  .coachMess {
    position: relative;
    padding-bottom: 25px;
    .userboxSty {
      border-bottom: #f2f2f2 2px solid;
      padding: 16px 0;
      .pelMess {
        margin-left: 18px;
        font-size: 18px;
        i {
          font-size: 22px;
        }
        .name {
          font-weight: 700;
          font-size: 16px;
        }
      }
    }

    .bottomBut {
      position: absolute;
      bottom: 0;
      left: 0;
      right: 0;
      background-color: #f3f3f3;
      text-align: center;
      cursor: pointer;
      padding: 10px 0;
      .br {
        border-right: #dadada 1px solid;
      }
    }
  }
</style>
