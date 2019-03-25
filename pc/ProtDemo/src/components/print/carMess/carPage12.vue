<template>
      <div>
          <table border="1" cellspacing="0" style="width: 100%" class="jt1">
              <tr style="height: 100px">
                <td></td>
                <td colspan="8"></td>
              </tr>
              <tr style="height: 100px">
                <td></td>
                <td colspan="8"></td>
              </tr>
              <tr style="height: 100px">
                <td>从业资格证复印件</td>
                <td colspan="8"></td>
              </tr>
              <tr style="height: 100px">
                <td>单位意见</td>
                <td colspan="8"></td>
              </tr>
          </table>
      </div>
</template>

<script>
    export default {
        name: "carPage1",
        props:{
            carInfo:{
                type:Object,
                default:()=>{
                    return {}
                }
            }
        }
    }
</script>

<style scoped>

</style>
