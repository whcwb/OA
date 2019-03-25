<template>
  <div id="app" style="transform: translateY(-18px)">
    <router-view/>
    <div id="page1" class="AppPrint">
      <div v-html="printInnerHTML">
      </div>
    </div>
  </div>
</template>

<script>
  import store from './store'

  export default {
    name: 'App',
    created() {

    },
    computed: {
      printInnerHTML: () => {
        return store.state.app.printInnerHTML
      }
    },
    mounted() {
    },
    methods: {
    }
  }
</script>

<style lang="less">
  .AppPrint{
    left: 0;
    top: 0;
    background-color: #fff;
    position: fixed;
    z-index: -9999;
    /*z-index: 9999;*/
  }
  .ivu-menu-light.ivu-menu-horizontal .ivu-menu-item-active,
  .ivu-menu-light.ivu-menu-horizontal .ivu-menu-submenu-active,
  .ivu-menu-light.ivu-menu-horizontal .ivu-menu-submenu:hover {
    border-bottom: none !important;
    border: 2px solid #2d8cf0;
    border-radius: 30px 30px 5px 5px;
  }

  .ivu-menu-light.ivu-menu-horizontal .ivu-menu-item:hover {
    border-bottom: none !important;
  }

  .size {
    width: 100%;
    height: 100%;
  }

  html, body {
    .size;
    overflow: hidden;
    margin: 0;
    padding: 0;
  }

  #app {
    .size;
  }
</style>
