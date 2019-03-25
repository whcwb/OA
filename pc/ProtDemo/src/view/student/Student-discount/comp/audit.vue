<template>
  <div>
    <Modal v-model="showModal" height="600" width="900" :closable='false' :mask-closable="false">
      <div style="width:600px">
        <Form>
          <FormItem props="xm" label="姓名:">
            <Input  disabled type="text" placeholder="请输入姓名" v-model="user.xm"></Input>
          </FormItem>
          <FormItem props="idcard" label="身份证号:">
            <Input disabled type="text" placeholder="请输入身份证号" v-model="user.idcard"></Input>
          </FormItem>
          <FormItem props="phone" label="电话:">
            <Input disabled type="text" placeholder="请输入电话" v-model="user.phone"></Input>
          </FormItem>
          <FormItem props="tgyh" label="是否报团:">
            <Input disabled type="text" placeholder="是否报团" v-model="user.tgyh"></Input>
          </FormItem>
          <FormItem props="tgsfct" label="是否成团:">
            <Input disabled type="text" placeholder="是否成团" v-model="user.tgsfct"></Input>
          </FormItem>
          <FormItem props="sffq" label="是否分期:">
            <Input disabled type="text" placeholder="是否分期" v-model="user.sffq"></Input>
          </FormItem>
          <FormItem props="carType" label="车型:">
            <Input disabled type="text" placeholder="车型" v-model="user.carType"></Input>
          </FormItem>

          <FormItem props="carType" label="优惠方案:">
            <Select  filterable>
              <Option v-for="item in cityList" :value="item.value" :key="item.value">{{ item.floor }}</Option>
            </Select>
          </FormItem>
          <FormItem props="carType" label="优惠金额:">
            <Input type="text" placeholder="请输入优惠金额" ></Input>
          </FormItem>

        </Form>
      </div>
      <div slot="footer">
        <Button type="default" style="color: #949494" @click="close">关闭</Button>
        <Button type="primary" @click="submit">审核</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "audit",
    props: {
      userAudit:{
        type: Object,
        default: {}
      }
    },
    data(){
      return{
        showModal: true,
        user:{
          xm: this.userAudit.name,
          idcard: this.userAudit.idcard,
          phone: this.userAudit.phone,
          tgyh: this.userAudit.tgyh,
          tgsfct: this.userAudit.tgsfct,
          sffq: this.userAudit.sffq,
          carType: this.userAudit.carType
        }
        ,cityList:[{value: "1",floor: "亲情价1"},{value: "2",floor: "亲情价2"},{value: "3",floor: "热情价"}]
      }
    },
    methods: {
      close(){
        this.$parent.compName = ''
      },
      submit(){
        // this.$parent.data1.push(this.user);
        this.$parent.compName = ''
      }
    }
  }
</script>

<style scoped>

</style>
