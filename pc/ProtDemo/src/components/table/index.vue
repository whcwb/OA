<template>
  <div class="table-c">
    <table border="1" cellpadding="0" cellspacing="0">
      <tr>
        <td>100</td>
        <td>100</td>
        <td>100</td>
        <td>100</td>
        <td>100</td>
      </tr>
      <tr>
        <td>100</td>
        <td>100</td>
        <td>100</td>
        <td>100</td>
        <td>100</td>
      </tr>
    </table>
  </div>
</template>

<script>
  export default {
    name: "index",
    data(){
      return{
        tabData:[
          [
            {title:'title',key:'key'}
          ]
        ]
      }
    }

  }
</script>

<style lang="less">
  /*.table-c table{border-right:1px solid #F00;border-bottom:1px solid #F00}*/
  /*.table-c table td{border-left:1px solid #F00;border-top:1px solid #F00}*/
</style>
