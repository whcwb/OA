export default {
  filters: {
    idCardNo:(val)=>{
      if(!val){
        return '******'
      }
      let arr = val.split('')
      arr.splice(6,8,'******')
      return arr.join('')
    },
    phone:(val)=>{
      if(!val){
        return '****'
      }
      let arr = val.split('')
      arr.splice(3,4,'****')
      return arr.join('')
    }
  },
  created() {
  },
  mounted() {
  }

}
