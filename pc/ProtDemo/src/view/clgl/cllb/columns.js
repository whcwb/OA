export default [

  {title: '车牌号码', key: 'cph', required: true,span: 8},
  {title: '档案编号', key: 'dah',span: 8},
  {title: '号牌种类', key: 'hpzl', dict: 'ZDCLK1036',span: 8},
  {title: '车型', key: 'pxcx', dict: 'ZDCLK0040',span: 8},
  {title: '使用性质', key: 'syxz', dict: 'ZDCLK1037',span: 8},
  {title:'机动车所有人',key:'carSyr',span: 8},
  {title: '身份证明名称', key: 'srydzlx', dict: 'IDType', span: 8},
  {title: '证件号码', key: 'srycode', span: 8},

  {title: '登记机关', key: 'djjg', span: 8},
  {title: '登记日期', key: 'ccdjrq', type: 'date', span: 8},
  {title: '车牌号', key: 'cph', span: 8},
  {title: '区域所属', key: 'carQy',dict:'ZDCLK1009', span: 8},


  {title: '车辆类型', key: 'cllx', span: 8},
  {title: '车辆品牌', key: 'clpp', span: 8},
  {title: '车辆型号', key: 'clxh', span: 8},

  {title: '车身颜色', key: 'csys', span: 8},
  {title: '车辆识别码', key: 'clsbm', span: 8},
  {title: '国产/进口', key: 'clcd',dict: 'CD', span: 8},

  {title: '发动机号', key: 'fdjh', span: 8},
  {title: '发动机型号', key: 'fdjxh', span: 8},
  {title: '燃料种类', key: 'rlzl', dict: 'ZDCLK1038', span: 8},

  {title: '排量', key: 'pl', append: 'ml', span: 8},
  {title: '功率', key: 'gl', append: 'kw', span: 8},
  {title: '制造厂名称', key: 'zzcmc', span: 8},

  {title: '转向形式', key: 'zxfs', span: 8},
  {title: '前轮距', key: 'qlj', append: 'mm', span: 8},
  {title: '后轮距', key: 'hlj', append: 'mm', span: 8},

  {title: '轮胎数量', key: 'ltsl', span: 8},
  {title: '轮胎规格', key: 'ltgg', span: 8},
  {title: '板簧片数', key: 'bhps', span: 8},

  {title: '轴距', key: 'zj', span: 8},
  {title: '轴数', key: '', span: 8},
  {title: '总质量', key: 'zzl', append: 'kg', span: 8},

  {title: '外廓尺寸_长', key: 'wkc', append: 'mm', span: 4},
  {title: '外廓尺寸_宽', key: 'wkk', append: 'mm', span: 4},
  {title: '外廓尺寸_高', key: 'wkg', append: 'mm', span: 4},

  {title: '货箱内部尺寸_长', key: 'nkc', append: 'mm', span: 4},
  {title: '货箱内部尺寸_宽', key: 'nkk', append: 'mm', span: 4},
  {title: '货箱内部尺寸_高', key: 'nkg', append: 'mm', span: 4},

  {title:'核定载质量',key:'hdzl',append: 'kg', span: 8},
  {title:'核定载客',key:'hdzk',append: '人', span: 8},
  {title:'准牵引总质量',key:'zqyzzl',append: 'kg', span: 8},

  {title:'驾驶室载客',key:'jsszks',append: '人', span: 8},
  {title: '车辆获得方式', key: 'clhdfs', span: 8},

  {title: '车辆出厂日期', key: 'ccrq', type: 'date', span: 8},
  {title: '发证机关', key: 'fzjg',  span: 8},
  {title: '发证日期', key: 'fzrq', type: 'date',span: 8},


  // {title: '车牌号码', key: 'cph', required: true},
  // {title: '号牌种类', key: 'hpzl', dict: 'ZDCLK1036'},
  // {title: '车型', key: 'pxcx', dict: 'ZDCLK0040'},
  // {title: '使用性质', key: 'syxz', dict: 'ZDCLK1037'},
  // {title: '初次登记日期', key: 'ccdjrq', type: 'date'},
  // {title: '强制报废期', key: 'qzbfq', type: 'date'},
  //
  // {title: '车辆识别码', key: 'clsbm'},
  // {title: '使用人姓名', key: 'syrName'},
  // {title: '使用人联系方式', key: 'syrDn'},
  // {title: '使用人所在地', key: 'syrSzd',dict:'ZDCLK1009',span:'12'},
  // {title: '发动机型号', key: 'fdjxh'},
  //
  //
  // {title: '车辆品牌', key: 'clpp'},
  // {title: '车辆类型', key: 'cllx'},
  // {title: '品牌型号', key: 'ppxh'},
  // {title: '车辆型号', key: 'clxh'},
  //
  //
  // {title: '档案编号', key: 'dah'},
  // {title: '车辆获得方式', key: 'clhdfs'},
  // {title: '核定载客', key: 'hdzk'},
  // {title: '出厂日期', key: 'ccrq', type: 'date'},
  //
  // {title: '发动机号', key: 'fdjh'},
  // {title: '燃料种类', key: 'rlzl', dict: 'ZDCLK1038'},
  // {title: '排量', key: 'pl', append: 'L'},
  // {title: '功率', key: 'gl', append: 'W'},
  // {title: '制造厂名称', key: 'zzcmc'},
  //
  // {title: '车身颜色', key: 'csys'},
  // {title: '前轮距', key: 'qlj', append: 'mm'},
  // {title: '后轮距', key: 'hlj', append: 'mm'},
  // {title: '轮胎数量', key: 'ltsl'},
  // {title: '轮胎规格', key: 'ltgg'},
  // {title: '板簧片数', key: 'bhps'},
  //
  // {title: '轴距', key: 'zj'},
  // {title: '外廓长', key: 'wkc', append: 'mm'},
  // {title: '外廓宽', key: 'wkk', append: 'mm'},
  // {title: '外廓高', key: 'wkg', append: 'mm'},
  // {title: '总质量', key: 'zzl', append: 'kg'},
]
